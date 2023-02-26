package blockchain.baby.model;

/**
 * Created by tzeyong.
 */
public enum TransactionType {
    ISSUE(1),
    REDEEM(2),
    TRANSFER(3);

    private final int value;

    private TransactionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TransactionType valueOf(int value) {
        switch (value) {
            case 1:
                return ISSUE;
            case 2:
                return REDEEM;
            case 3:
                return TRANSFER;
            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }
    }
}

