package blockchain.baby.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for KeyPairGenerator
 * Test cases:
 * 1. Generate a key pair
 * 2. Generate a key pair, use private key to sign a message, use public key to verify the signature
 * 3. Generate a key pair, use private key to sign a random string, use public key to verify the signature
 */
class KeyPairGeneratorTest {

    @Test
    void generateKeyPair() {
        java.security.KeyPair keyPair = KeyPairGenerator.generateKeyPair();
        assertNotNull(keyPair);
        assertNotNull(keyPair.getPrivate());
        assertNotNull(keyPair.getPublic());
    }

    @Test
    void generateKeyPairAndSign() throws Exception {
        java.security.KeyPair keyPair = KeyPairGenerator.generateKeyPair();
        assertNotNull(keyPair);
        assertNotNull(keyPair.getPrivate());
        assertNotNull(keyPair.getPublic());

        String message = "Hello World";
        byte[] signature = SignatureGenerator.generate(keyPair.getPrivate(), message.getBytes());
        assertNotNull(signature);
        assertTrue(SignatureGenerator.verifySignature(keyPair.getPublic(), signature, message.getBytes()));
    }

    @Test
    void generateKeyPairAndSignRandomString() throws Exception {
        java.security.KeyPair keyPair = KeyPairGenerator.generateKeyPair();
        assertNotNull(keyPair);
        assertNotNull(keyPair.getPrivate());
        assertNotNull(keyPair.getPublic());

        // generate a random string
        String message = java.util.UUID.randomUUID().toString();
        
        byte[] signature = SignatureGenerator.generate(keyPair.getPrivate(), message.getBytes());
        assertNotNull(signature);
        assertTrue(SignatureGenerator.verifySignature(keyPair.getPublic(), signature, message.getBytes()));
    }

}