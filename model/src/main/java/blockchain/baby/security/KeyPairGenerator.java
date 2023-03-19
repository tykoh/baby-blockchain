/**
 * Generate a key pair using bouncy castle with default 2048 bit key size
 */
package blockchain.baby.security;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyPairGenerator {

    public static KeyPair generateKeyPair() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        java.security.KeyPairGenerator generator = null;
        try {
            generator = java.security.KeyPairGenerator.getInstance("RSA", "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    /**
     * Get public key from byte array
     */
    public static PublicKey getPublicKey(byte[] key) throws Exception {
        // Create a new instance of the KeyFactory
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        // Create a new X509EncodedKeySpec with the publicKeyBytes
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(key);

        // Generate the public key from the key spec
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }

    /**
     * Get private key from byte array
     */
    public static PrivateKey getPrivateKey(byte[] key) throws Exception {
        // Create a new instance of the KeyFactory
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        // import pkcs8 encoded private key
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(key);

        // Generate the public key from the key spec
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        return privateKey;
    }
}
