package blockchain.baby.model;

import lombok.Data;

import java.math.BigInteger;

/**
 * Balance of RewardType for an account.
 */
@Data
public class Balance {
    private Account account;
    private RewardType rewardType;
    private BigInteger balance;

}
