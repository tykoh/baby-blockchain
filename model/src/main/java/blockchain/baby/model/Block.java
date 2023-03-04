package blockchain.baby.model;

import blockchain.baby.util.HexConverter;
import blockchain.baby.util.Sha256Generator;
import lombok.Data;

import java.security.NoSuchAlgorithmException;

/**
 * A block in the blockchain.
 * Records a list of transactions.
 * @author TY
 */
@Data
public class Block {
    private int index;
    /**
     * 32 bytes - hash from previous block
     */
    private byte[] previousHash;
    /**
     * 32 bytes - SHA256(previous hash + XOR of all transaction signatures + timestamp + nonce)
     */
    private byte[] hash;
    private long timestamp;
    private int nonce;
    private Transaction[] transactions;

    /**
     * Constructor for genesis block.
     */
    public Block() {
        this.index = 0;
        this.previousHash = new byte[32];
        this.timestamp = System.currentTimeMillis();
        this.nonce = (int) (Math.random() * Integer.MAX_VALUE);
        this.transactions = new Transaction[0];
        try {
            calculateHash();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructor for new block. With previous block and an array of Transactions as input.
     * Generate a random nonce.
     */
    public Block(Block previousBlock, Transaction[] transactions) {
        this.index = previousBlock.getIndex() + 1;
        this.previousHash = previousBlock.getHash();
        this.timestamp = System.currentTimeMillis();
        this.transactions = transactions;
        this.nonce = (int) (Math.random() * Integer.MAX_VALUE);
        try {
            calculateHash();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Return hash in hex string format.
     * where hash = SHA256(previous hash + XOR of all transaction signatures + timestamp + nonce)
     */
    public void calculateHash() throws NoSuchAlgorithmException {
        // hash = SHA256(XOR of all transaction signatures)
        byte[] workingHash = new byte[32];
        for (Transaction transaction : transactions) {
            byte[] signature = transaction.getSignature();
            for (int i = 0; i < 32; i++) {
                workingHash[i] ^= signature[i];
            }
        }
        // hash = sha256(previousHash + workingHash + timestamp + nonce)

        byte[] timestampBytes = Long.toString(timestamp).getBytes();
        byte[] nonceBytes = Integer.toString(nonce).getBytes();
        byte[] input = new byte[previousHash.length + workingHash.length + timestampBytes.length + nonceBytes.length];

        System.arraycopy(previousHash, 0, input, 0, previousHash.length);
        System.arraycopy(workingHash, 0, input, previousHash.length, workingHash.length);
        System.arraycopy(timestampBytes, 0, input, previousHash.length + workingHash.length, timestampBytes.length);
        System.arraycopy(nonceBytes, 0, input, previousHash.length + workingHash.length + timestampBytes.length, nonceBytes.length);

        this.hash = Sha256Generator.generate(input);
    }

    /**
     * Return previous hash in hex string format.
     */
    public String getPreviousHashHex() {
        return HexConverter.bytesToHex(previousHash);
    }

    /**
     * Return hash in hex string format.
     */
    public String getHashHex() {
        return HexConverter.bytesToHex(hash);
    }

    /**
     * return string representation of block
     * loop through all transactions and print them
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Block{" +
                "index=" + index +
                ", previousHash=" + getPreviousHashHex() +
                ", hash=" + getHashHex() +
                ", timestamp=" + timestamp +
                ", nonce=" + nonce +
                ", transactions=[");
        for (Transaction transaction : transactions) {
            // add new line
            sb.append("\n");
            sb.append(transaction.toString());
        }
        sb.append("]}");
        return sb.toString();
    }

}
