package blockchain.baby.node.schedule;

import blockchain.baby.model.RewardType;
import blockchain.baby.model.Transaction;
import blockchain.baby.model.TransactionType;
import blockchain.baby.node.jpa.BcTransaction;
import blockchain.baby.node.svc.RewardTypeService;
import blockchain.baby.node.svc.TransactionService;
import blockchain.baby.security.KeyPairGenerator;
import blockchain.baby.security.SignatureGenerator;
import blockchain.baby.util.HexConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tzeyong.
 */
@Component
public class VerifyTransactions {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RewardTypeService rewardTypeService;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws Exception{

        System.out.println("The time is now "+ dateFormat.format(new Date()));
        // list of pending transactions
        List<BcTransaction> pendingTransactions = transactionService.getPendingTransactions();
        // loop and print out each transaction
        List<BcTransaction> bcTxns = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();
        for (BcTransaction txn : pendingTransactions) {
            try {
                blockchain.baby.model.RewardType rewardType = new blockchain.baby.model.RewardType(
                        HexConverter.hexToBytes(txn.getRewardTypeCode()),
                        "",
                        "");
                rewardType.setFingerprint(HexConverter.hexToBytes(txn.getRewardTypeFingerprint()));
                Transaction transaction = txn.toTransaction(rewardType);
                System.out.println("Transaction: " + transaction.toString());
                // verify fingerprint
                byte[] fromAddressByte = HexConverter.hexToBytes(txn.getFromAddress());
                // convert fromAddressByte to public key
                PublicKey publicKey = KeyPairGenerator.getPublicKey(fromAddressByte);
                // if transaction type is ISSUE, verify fingerprint of reward type using fromAddress
                if (txn.getTxnType() == TransactionType.ISSUE) {

                    byte[] rewardTypeCodeBytes = HexConverter.hexToBytes(txn.getRewardTypeCode());
                    byte[] rewardTypeFingerprint = HexConverter.hexToBytes(txn.getRewardTypeFingerprint());
                    boolean issuerVerified = SignatureGenerator.verifySignature(publicKey, rewardTypeFingerprint, rewardTypeCodeBytes);
                    if (!issuerVerified) {
                        // update issuer verification failed
                        System.out.println("Issuer verification failed");
                        txn.setTxnStatus(BcTransaction.Status.REJECTED);
                        transactionService.saveTransaction(txn);
                    }
                }

                // check if transaction is valid
                boolean transactionIsValid = transaction.verifySignature(publicKey);
                if (!transactionIsValid) {
                    // update transaction status to rejected
                    System.out.println("Transaction is invalid");
                    txn.setTxnStatus(BcTransaction.Status.REJECTED);
                    transactionService.saveTransaction(txn);
                } else {
                    // update transaction status to accepted
                    System.out.println("Transaction is valid");
                    txn.setTxnStatus(BcTransaction.Status.CONFIRMED);
                    bcTxns.add(txn);
                    transactions.add(transaction);
                    transactionService.saveTransaction(txn);
                }
                System.out.println(txn.toString());
            } catch (Exception e) {
                // print stack trace
                e.printStackTrace();
                txn.setTxnStatus(BcTransaction.Status.REJECTED);

                transactionService.saveTransaction(txn);
            }
        }
        // add transactions to block if size of transaction is greater than zero
        if (transactions.size() > 0){
            System.out.println("Adding transactions to block");
            transactionService.addTransactionsToBlock(bcTxns, transactions);
        }
    }
}
