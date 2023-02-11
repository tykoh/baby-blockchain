/**
 * Generate a key pair using bouncy castle with default 2048 bit key size
 */
package blockchain.baby.security;

import java.security.KeyPair;
import java.security.Security;

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
}
