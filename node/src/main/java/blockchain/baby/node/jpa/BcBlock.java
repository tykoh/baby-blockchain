package blockchain.baby.node.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by tzeyong.
 */
@Entity
@Table(name = "bc_block")
@Getter
@Setter
public class BcBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idx")
    private int index;

    @Column(name = "previous_hash")
    private String previousHash;

    @Column(name = "hash")
    private String hash;

    @Column(name = "timestamp")
    private Date timestamp;

    private int nonce;

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    private List<BcTransaction> transactions;

    // implement toString()
    @Override
    public String toString() {
        return "BcBlock{" +
                "id=" + id +
                ", index=" + index +
                ", previousHash='" + previousHash + '\'' +
                ", hash='" + hash + '\'' +
                ", timestamp=" + timestamp +
                ", nonce=" + nonce +
                '}';
    }

}
