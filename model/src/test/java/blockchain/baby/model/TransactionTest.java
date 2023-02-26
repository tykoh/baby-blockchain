package blockchain.baby.model;

import blockchain.baby.security.KeyPairGenerator;
import blockchain.baby.util.HexConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by tzeyong.
 * 1. Test sign method
 * 2. Test verifySignature method
 * 3. Test parse bytes into transaction
 */
class TransactionTest {
    boolean init = false;
    java.security.KeyPair organisationKey;
    java.security.KeyPair beneficiaryKey;
    Account organisationAccount;
    Account beneAccount;
    RewardType rewardType;

    void initTest() throws Exception{
        // if not init, then init test
        if (!init) {
            // use KeyPairGenerator to generate a keypair
            organisationKey = KeyPairGenerator.generateKeyPair();
            // use KeyPairGenerator to generate a keypair for beneficiary
            beneficiaryKey = KeyPairGenerator.generateKeyPair();
            organisationAccount = new Account(organisationKey.getPublic().getEncoded());
            beneAccount = new Account(beneficiaryKey.getPublic().getEncoded());
            byte[] codeBytes = HexConverter.hexToBytes("ABCD");
            rewardType = new RewardType(codeBytes, "Reward Type ABCD", "Reward Type ABCD Description");
            rewardType.sign(organisationKey.getPrivate());
            init = true;// init test
        }
    }

    @Test
    void signAndVerify() throws Exception {
        initTest();
        rewardType.sign(organisationKey.getPrivate());
        Transaction transaction = new Transaction(TransactionType.ISSUE, organisationAccount, beneAccount, 100, rewardType);
        assertNotNull(transaction);
        transaction.sign(organisationKey.getPrivate());
        assertTrue(transaction.verifySignature(organisationKey.getPublic()));
    }

    @Test
    void parseBytes() throws Exception {
        initTest();
        rewardType.sign(organisationKey.getPrivate());
        Transaction transaction = new Transaction(TransactionType.ISSUE, organisationAccount, beneAccount, 100, rewardType);
        assertNotNull(transaction);
        transaction.sign(organisationKey.getPrivate());
        assertTrue(transaction.verifySignature(organisationKey.getPublic()));
        byte[] bytes = transaction.getBytes();
        String originalBytes = HexConverter.bytesToHex(bytes);
        // log out original bytes
        System.out.println("original bytes:" + originalBytes);
        assertNotNull(bytes);
        Transaction parsedTransaction = new Transaction(bytes);
        assertNotNull(parsedTransaction);
        String compareBytes = HexConverter.bytesToHex(parsedTransaction.getBytes());
        // log out compare bytes
        System.out.println("compare bytes: "+compareBytes);
        assertEquals(originalBytes, compareBytes);
    }
}