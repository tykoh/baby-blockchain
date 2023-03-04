package blockchain.baby.model;

import blockchain.baby.security.KeyPairGenerator;
import blockchain.baby.util.HexConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Block
 * Test cases:
 * 1. Generate genesis block
 * 2. Generate hash for genesis block
 * 3. Generate new block with previous block and transactions
 * 4. Generate hash for new block
 */
class BlockTest {

    boolean init = false;
    java.security.KeyPair organisationKey;
    java.security.KeyPair beneficiaryKey;
    java.security.KeyPair beneficiaryKey2;
    Account organisationAccount;
    Account beneAccount;
    Account beneAccount2;
    RewardType rewardType;

    void initTest() throws Exception{
        // if not init, then init test
        if (!init) {
            // use KeyPairGenerator to generate a keypair
            organisationKey = KeyPairGenerator.generateKeyPair();
            // use KeyPairGenerator to generate a keypair for beneficiary
            beneficiaryKey = KeyPairGenerator.generateKeyPair();
            beneficiaryKey2 = KeyPairGenerator.generateKeyPair();
            organisationAccount = new Account(organisationKey.getPublic().getEncoded());
            beneAccount = new Account(beneficiaryKey.getPublic().getEncoded());
            beneAccount2 = new Account(beneficiaryKey2.getPublic().getEncoded());
            byte[] codeBytes = HexConverter.hexToBytes("ABCD");
            rewardType = new RewardType(codeBytes, "Reward Type ABCD", "Reward Type ABCD Description");
            rewardType.sign(organisationKey.getPrivate());
            init = true;// init test
        }
    }

    @Test
    void generateGenesisBlock() {
        Block genesisBlock = new Block();
        assertNotNull(genesisBlock);
        assertEquals(0, genesisBlock.getIndex());
        assertEquals(32, genesisBlock.getPreviousHash().length);
        assertEquals(32, genesisBlock.getHash().length);
        assertEquals(0, genesisBlock.getTransactions().length);
    }

    @Test
    void generateNewBlock() throws Exception {
        initTest();
        Block genesisBlock = new Block();
        assertNotNull(genesisBlock);
        // print out genesis block
        System.out.println("Genesis Block:" + genesisBlock);
        Transaction[] transactions = new Transaction[3];
        transactions[0] = new Transaction(TransactionType.ISSUE, organisationAccount, beneAccount, 100, rewardType);
        // sign transaction
        transactions[0].sign(organisationKey.getPrivate());

        transactions[1] = new Transaction(TransactionType.REDEEM, beneAccount, organisationAccount, 50, rewardType);
        // sign transaction
        transactions[1].sign(beneficiaryKey.getPrivate());

        transactions[2] = new Transaction(TransactionType.TRANSFER, beneAccount, beneAccount2, 50, rewardType);
        // sign transaction
        transactions[2].sign(beneficiaryKey.getPrivate());
        Block newBlock = new Block(genesisBlock, transactions);
        assertNotNull(newBlock);
        // print out new block
        System.out.println("New Block:" + newBlock);
        assertEquals(1, newBlock.getIndex());
        assertEquals(32, newBlock.getPreviousHash().length);
        assertEquals(32, newBlock.getHash().length);
        assertEquals(3, newBlock.getTransactions().length);
    }

}