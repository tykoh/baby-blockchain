package blockchain.baby.node.svc;

import blockchain.baby.node.jpa.BcBlock;
import blockchain.baby.node.jpa.RewardType;
import blockchain.baby.node.repo.RewardTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tzeyong.
 */
@Service
public class RewardTypeService {
    @Autowired
    private RewardTypeRepository repository;

    public List<RewardType> getRewardTypes() {
        return repository.findAll();
    }

    public void saveRewardType(RewardType rewardType) {
        repository.save(rewardType);
    }

    // get reward type by fingerprint
    public RewardType getRewardTypeByFingerprint(String fingerprint) {
        return repository.findByFingerprint(fingerprint);
    }
}
