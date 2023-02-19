package blockchain.baby.model;

import lombok.Data;

/**
 * An account in the blockchain.
 * Contains one or more public keys and a balance for each loyalty reward type.
 * First public key uses in the constructor is the account id
 */
@Data
public class Account {
    private String accountId;
    private String[] publicKeys;
    private Balance[] balances;
}
