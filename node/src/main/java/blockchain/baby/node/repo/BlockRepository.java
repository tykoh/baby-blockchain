package blockchain.baby.node.repo;

import blockchain.baby.node.jpa.BcBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tzeyong.
 */
@Repository
public interface BlockRepository extends JpaRepository<BcBlock, Long> {
    // get block with largest index
    public BcBlock findTopByOrderByIndexDesc();
}
