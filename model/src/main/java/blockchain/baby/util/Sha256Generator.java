package blockchain.baby.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Generator {
    /**
     * Generate SHA256 hash of input.
     * @param input
     * @return
     */
    public static byte[] generate(byte[] input) throws NoSuchAlgorithmException {
        // generate sha256 from input bytes
        // return hash bytes
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input);
    }
}
