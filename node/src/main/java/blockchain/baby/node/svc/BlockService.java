package blockchain.baby.node.svc;

import blockchain.baby.node.jpa.BcBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import blockchain.baby.node.repo.BlockRepository;

import java.util.List;

/**
 * Created by tzeyong.
 */
@Service
public class BlockService {
    @Autowired
    private BlockRepository blockRepository;

    public List<BcBlock> getBlocks() {
        return blockRepository.findAll();
    }

    public void saveBlock(BcBlock block) {
        blockRepository.save(block);
    }
}
