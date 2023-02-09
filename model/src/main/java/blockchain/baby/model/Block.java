package blockchain.baby.model;

/**
 * A block in the blockchain.
 * Records a list of transactions.
 * @author TY
 */
public class Block {
    private int index;
    private String previousHash;
    private String hash;
    private long timestamp;
    private int nonce;
    private Transaction[] transactions;

    public Block(int index, String previousHash, String hash, long timestamp, int nonce, Transaction[] transactions) {
        this.index = index;
        this.previousHash = previousHash;
        this.hash = hash;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.transactions = transactions;
    }

    public int getIndex() {
        return index;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", previousHash='" + previousHash + '\'' +
                ", hash='" + hash + '\'' +
                ", timestamp=" + timestamp +
                ", nonce=" + nonce +
                ", transactions=" + transactions +
                '}';
    }
}
