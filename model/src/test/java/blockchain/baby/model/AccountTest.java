package blockchain.baby.model;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Account
 * Test cases:
 * 1. Create an account with a public key, check if the account id is the same as the public key
 * 2. Create an account with a public key, add another public key, check if the account id is the same as the first public key
 * 3. Create an account with a public key, add another two public key, check if the public key is added to the list of public keys and total number of public key is three
 */
class AccountTest {

        @Test
        void createAccountWithPublicKey() throws Exception{
            byte[] publicKey = "publicKey".getBytes("UTF-8");
            Account account = new Account(publicKey);
            assertNotNull(account);
            assertEquals(publicKey, account.getAccountId());
        }

        @Test
        void createAccountWithPublicKeyAndAddAnotherPublicKey() throws Exception {
            byte[] publicKey = "publicKey".getBytes(StandardCharsets.UTF_8);
            Account account = new Account(publicKey);
            assertNotNull(account);
            assertEquals(publicKey, account.getAccountId());

            byte[] anotherPublicKey = "anotherPublicKey".getBytes(StandardCharsets.UTF_8);
            account.addPublicKey(anotherPublicKey);
            assertEquals(publicKey, account.getAccountId());
        }

        @Test
        void createAccountWithPublicKeyAndAddTwoMorePublicKeys() {
            byte[] publicKey = "publicKey".getBytes(StandardCharsets.UTF_8);
            Account account = new Account(publicKey);
            assertNotNull(account);
            assertEquals(publicKey, account.getAccountId());

            byte[] anotherPublicKey = "anotherPublicKey".getBytes(StandardCharsets.UTF_8);
            account.addPublicKey(anotherPublicKey);
            assertEquals(publicKey, account.getAccountId());

            byte[] anotherPublicKey2 = "anotherPublicKey2".getBytes(StandardCharsets.UTF_8);
            account.addPublicKey(anotherPublicKey2);
            assertEquals(publicKey, account.getAccountId());

            assertEquals(3, account.getPublicKeys().size());
        }
}