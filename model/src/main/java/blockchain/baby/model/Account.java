package blockchain.baby.model;

import blockchain.baby.util.HexConverter;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;

/**
 * An account in the blockchain.
 * Contains one or more public keys and a balance for each loyalty reward type.
 * First public key uses in the constructor is the account id
 */
@Data
public class Account {
    @NonNull
    private byte[] accountId;
    private ArrayList<byte[]> publicKeys;
    private Balance[] balances;

    // add constructor to add a public key
    public Account(byte[] initialPublicKey) {
        accountId = initialPublicKey;
        addPublicKey(initialPublicKey);
    }

    // add a public key to the account
    public void addPublicKey(byte[] publicKey) {
        if (publicKeys == null) {
            publicKeys = new ArrayList<>();
        }
        publicKeys.add(publicKey);
    }
}
