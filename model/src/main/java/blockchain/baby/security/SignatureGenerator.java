/**
 * Provides method to generate signature bytes for given data.
 */
package blockchain.baby.security;

import blockchain.baby.util.HexConverter;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class SignatureGenerator {
    /**
     * Generate signature bytes for given data.
     * @param data data to be signed
     * @return signature bytes
     */
    public static byte[] generate(PrivateKey key, byte[] data) throws Exception{
        // generate signature from private key using bouncy castle
        Signature instance = Signature.getInstance("SHA256withRSA", "BC");
        instance.initSign(key);
        instance.update(data);
        // return signature bytes
        return instance.sign();
    }

    /**
     * Return generated signature in hex string format.
     */
    public static String generateHex(PrivateKey key, byte[] data) throws Exception {
        return HexConverter.bytesToHex(generate(key, data));
    }

    /**
     * Verify signature bytes for given data.
     */
    public static boolean verifySignature(PublicKey key, byte[] signature, byte[] data){
        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(key);
            sig.update(data);
            return sig.verify(signature);
        } catch (Exception e) {
            return false;
        }
    }
}
