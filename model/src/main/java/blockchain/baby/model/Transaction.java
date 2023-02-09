package blockchain.baby.model;

/**
 * A transaction to be included in a block.
 * supported type: issue, redeem, transfer
 * Contains signature of the sender.
 * @author TY
 */
public class Transaction {
    private String type;
    private String from;
    private String to;
    private int amount;

    private String signature;

    public Transaction(String type, String from, String to, int amount, String signature) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.signature = signature;
    }

    public String getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getAmount() {
        return amount;
    }

    public String getSignature() {
        return signature;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                ", signature='" + signature + '\'' +
                '}';
    }
}
