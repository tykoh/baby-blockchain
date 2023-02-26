package blockchain.baby.model;

import lombok.Data;

/**
 * A transaction to be included in a block.
 * supported type: issue, redeem, transfer
 * Contains signature of the sender.
 * @author TY
 */
@Data
public class Transaction {
    private String type;
    private byte[] from;
    private byte[] to;
    private int amount;
    private String signature;

}
