package blockchain.baby.node.repo;

import blockchain.baby.node.jpa.RewardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tzeyong.
 */
@Repository
public interface RewardTypeRepository extends JpaRepository<RewardType, Long> {
    // Search reward type by fingerprint
    RewardType findByFingerprint(String fingerprint);
}
