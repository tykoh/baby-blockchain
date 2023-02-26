package blockchain.baby.model;

import lombok.Data;

/**
 * A block in the blockchain.
 * Records a list of transactions.
 * @author TY
 */
@Data
public class Block {
    private int index;
    private String previousHash;
    private String hash;
    private long timestamp;
    private int nonce;
    private Transaction[] transactions;

}
