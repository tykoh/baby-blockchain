package blockchain.baby.node.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tzeyong.
 */
@Entity
@Table(name = "reward_type",
    indexes = {
                @Index(name = "fingerprint_index", columnList = "fingerprint", unique = true)
        }
)
@Getter
@Setter
public class RewardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String description;

    private String fingerprint;

    // implement toString()
    @Override
    public String toString() {
        return "RewardType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", fingerprint='" + fingerprint + '\'' +
                '}';
    }
}
