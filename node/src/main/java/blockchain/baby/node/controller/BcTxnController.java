package blockchain.baby.node.controller;


import blockchain.baby.model.Account;
import blockchain.baby.model.RewardType;
import blockchain.baby.model.Transaction;
import blockchain.baby.model.TransactionType;
import blockchain.baby.node.jpa.BcTransaction;
import blockchain.baby.node.svc.TransactionService;
import blockchain.baby.security.KeyPairGenerator;
import blockchain.baby.security.SignatureGenerator;
import blockchain.baby.util.HexConverter;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tzeyong.
 */
@RestController
public class BcTxnController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/txn")
    public String postTxn(@RequestBody String jsonTxn) {
        // map jsonTxn to Transaction via Gson lib
        Gson gson = new Gson();
        BcTransaction txn = gson.fromJson(jsonTxn, BcTransaction.class);
        txn.setTxnStatus(BcTransaction.Status.PENDING);
        transactionService.saveTransaction(txn);
        return txn.toString();
    }

    @PostMapping("/txn/issue")
    public String postIssueTxn(@RequestBody String jsonTxn) throws Exception{
        // map jsonTxn to Transaction via Gson lib
        Gson gson = new Gson();
        Map map = gson.fromJson(jsonTxn, Map.class);
        // get public key from map
        String publicKeyStr = (String) map.get("publicKey");
        // get private key from map
        String privateKeyStr = (String) map.get("privateKey");
        // get rewardTypeCode from map
        String rewardTypeCode = (String) map.get("rewardTypeCode");
        // get amount from map
        Long amount = Long.parseLong((String) map.get("amount"));
        // get toAddress from map
        String toAddress = (String) map.get("toAddress");

        // derive private key
        PrivateKey privateKey = KeyPairGenerator.getPrivateKey(
                HexConverter.hexToBytes(privateKeyStr));
        Account fromAcct = new Account(
                HexConverter.hexToBytes(publicKeyStr)
        );
        Account toAcct = new Account(
                HexConverter.hexToBytes(toAddress)
        );

        // prepare reward type with code and fingerprint
        byte[] rewardTypeBytes = HexConverter.hexToBytes(rewardTypeCode);
        byte[] rewardTypeFingerprint = SignatureGenerator.generate(privateKey, rewardTypeBytes);
        RewardType rewardType = new RewardType(rewardTypeBytes, "", "");
        rewardType.setFingerprint(rewardTypeFingerprint);

        // prepare transaction
        Transaction txn = new Transaction( TransactionType.ISSUE,
                fromAcct,
                toAcct,
                amount,
                rewardType
        );
        txn.sign(privateKey);

        // prepare response
        Map responseMap = new HashMap();
        responseMap.put("txnType", "ISSUE");
        responseMap.put("fromAddress", publicKeyStr);
        responseMap.put("toAddress", toAddress);
        responseMap.put("amount", amount);
        responseMap.put("rewardTypeCode", rewardTypeCode);
        responseMap.put("rewardTypeFingerprint", HexConverter.bytesToHex(rewardTypeFingerprint));
        responseMap.put("signature", HexConverter.bytesToHex(txn.getSignature()));
        return gson.toJson(responseMap);
    }
}
