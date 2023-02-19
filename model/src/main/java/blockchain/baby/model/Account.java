package blockchain.baby.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

/**
 * An account in the blockchain.
 * Contains one or more public keys and a balance for each loyalty reward type.
 * First public key uses in the constructor is the account id
 */
@Data
@RequiredArgsConstructor
public class Account {
    @NonNull
    private String accountId;
    private ArrayList<String> publicKeys;
    private Balance[] balances;

    // add constructor to add a public key
    public Account(String initialPublicKey) {
        this.accountId = initialPublicKey;
        addPublicKey(initialPublicKey);
    }

    // add a public key to the account
    public void addPublicKey(String publicKey) {
        if (publicKeys == null) {
            publicKeys = new ArrayList<>();
        }
        publicKeys.add(publicKey);
    }
}
