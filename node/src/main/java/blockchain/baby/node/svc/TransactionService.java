package blockchain.baby.node.svc;

import blockchain.baby.model.Block;
import blockchain.baby.model.Transaction;
import blockchain.baby.node.jpa.BcBlock;
import blockchain.baby.node.jpa.BcTransaction;
import blockchain.baby.node.repo.BlockRepository;
import blockchain.baby.node.repo.TransactionRepository;
import blockchain.baby.util.HexConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by tzeyong.
 */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BlockRepository blockRepository;

    public List<BcTransaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public void saveTransaction(BcTransaction transaction) {
        transactionRepository.save(transaction);
    }

    /**
     * Get all pending transactions
     */
    public List<BcTransaction> getPendingTransactions() {
        return transactionRepository.findByTxnStatus(BcTransaction.Status.PENDING);
    }

    public void addTransactionsToBlock(List<BcTransaction> bcTxns, List<Transaction> transactions) {
        // get block with largest index
        BcBlock bcBlock = blockRepository.findTopByOrderByIndexDesc();
        // if not found, create first block
        Block previousBlock = null;
        if (bcBlock == null) {
            System.out.println("Going to create first block!");
            Block block = new Block();
            bcBlock = new BcBlock();
            bcBlock.setIndex(block.getIndex());
            bcBlock.setPreviousHash(block.getPreviousHashHex());
            bcBlock.setTimestamp(new Date(block.getTimestamp()));
            bcBlock.setNonce(block.getNonce());
            bcBlock.setHash(block.getHashHex());
            blockRepository.save(bcBlock);
            previousBlock = block;
        } else {
            System.out.println("Found previous block: " + bcBlock);
            previousBlock = new Block();
            previousBlock.setIndex(bcBlock.getIndex());
            previousBlock.setPreviousHash(HexConverter.hexToBytes(bcBlock.getPreviousHash()));
            previousBlock.setTimestamp(bcBlock.getTimestamp().getTime());
            previousBlock.setNonce(bcBlock.getNonce());
            previousBlock.setHash(HexConverter.hexToBytes(bcBlock.getHash()));
        }

        // create next block
        // convert List of Transaction to array
        Transaction[] txnArray = new Transaction[transactions.size()];
        transactions.toArray(txnArray);

        Block nextBlock = new Block(previousBlock, txnArray);
        // map nextBlock to BcBlock
        BcBlock nextBcBlock = new BcBlock();
        nextBcBlock.setIndex(nextBlock.getIndex());
        nextBcBlock.setPreviousHash(nextBlock.getPreviousHashHex());
        nextBcBlock.setTimestamp(new Date(nextBlock.getTimestamp()));
        nextBcBlock.setNonce(nextBlock.getNonce());
        nextBcBlock.setHash(nextBlock.getHashHex());
        blockRepository.save(nextBcBlock);
        // iterate thru bcTxns and set bcblock
        for (BcTransaction bcTxn : bcTxns) {
            bcTxn.setBlock(nextBcBlock);
            transactionRepository.save(bcTxn);
        }
    }
}
