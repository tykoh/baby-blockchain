package blockchain.baby.node.jpa;

import blockchain.baby.model.Account;
import blockchain.baby.model.RewardType;
import blockchain.baby.model.Transaction;
import blockchain.baby.model.TransactionType;
import blockchain.baby.util.HexConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tzeyong.
 */
@Entity
@Table(name = "bc_transaction")
@Getter
@Setter
public class BcTransaction {

    public enum Status {
        PENDING,
        CONFIRMED,
        REJECTED
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "txn_type")
    @Enumerated(EnumType.STRING)
    private TransactionType txnType;

    @Column(name = "from_address", length = 2048)
    private String fromAddress;

    @Column(name = "to_address", length = 2048)
    private String toAddress;

    @Column(name = "reward_type_code")
    private String rewardTypeCode;
    @Column(name = "reward_type_fingerprint", length = 2048)
    private String rewardTypeFingerprint;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "signature", length = 2048)
    private String signature;

    @Enumerated(EnumType.STRING)
    private Status txnStatus;
    @ManyToOne
    @JoinColumn(name = "block_id")
    private BcBlock block;

    // map to Transaction
    public Transaction toTransaction(RewardType rewardType) {
        byte[] fromAddressBytes = HexConverter.hexToBytes(fromAddress);
        byte[] toAddressBytes = HexConverter.hexToBytes(toAddress);
        Transaction transaction = new Transaction(txnType, new Account(fromAddressBytes), new Account(toAddressBytes), amount, rewardType);

        transaction.setSignature(HexConverter.hexToBytes(signature));
        return transaction;
    }

    // implement toString() method
    @Override
    public String toString() {
        return "BcTransaction{" +
                "id=" + id +
                ", txnType=" + txnType +
                ", fromAddress='" + fromAddress + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", rewardTypeCode='" + rewardTypeCode + '\'' +
                ", amount=" + amount +
                ", signature='" + signature + '\'' +
                ", txnStatus=" + txnStatus +
                ", block=" + block +
                '}';
    }
}
