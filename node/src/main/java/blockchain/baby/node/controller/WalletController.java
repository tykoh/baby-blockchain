package blockchain.baby.node.controller;


import blockchain.baby.node.jpa.BcTransaction;
import blockchain.baby.node.svc.TransactionService;
import blockchain.baby.security.KeyPairGenerator;
import blockchain.baby.util.HexConverter;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tzeyong.
 */
@RestController
public class WalletController {

    @PostMapping("/wallet")
    public String postTxn() {
        // generate public private key pair
        KeyPair keyPair = KeyPairGenerator.generateKeyPair();
        Gson gson = new Gson();
        String publicKey = HexConverter.bytesToHex(keyPair.getPublic().getEncoded());
        String privateKey = HexConverter.bytesToHex(keyPair.getPrivate().getEncoded());

        Map map = new HashMap();
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        map.put("privateKeyFormat", keyPair.getPrivate().getFormat());
        map.put("publicKeyFormat", keyPair.getPublic().getFormat());
        return gson.toJson(map);
    }
}
