package blockchain.baby.node.repo;

import blockchain.baby.node.jpa.BcBlock;
import blockchain.baby.node.jpa.BcTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tzeyong.
 */
@Repository
public interface TransactionRepository extends JpaRepository<BcTransaction, Long> {
    // list transaction with status PENDING
    List<BcTransaction> findByTxnStatus(BcTransaction.Status status);
}
