package blockchain.baby.model;

import lombok.Data;
import blockchain.baby.security.SignatureGenerator;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * A transaction to be included in a block.
 * supported type: issue, redeem, transfer
 * Contains signature of the sender.
 * @author TY
 */
@Data
public class Transaction {
    private TransactionType txnType;
    private byte[] from;
    private byte[] to;
    private byte[] rewardTypeCode;
    private byte[] rewardTypeFingerprint;
    private byte[] amount;
    private byte[] signature;

    /**
     * parse byte array to transaction
     */
    public Transaction(byte[] bytes) {
        txnType = TransactionType.valueOf(bytes[0]);
        from = new byte[8];
        System.arraycopy(bytes, 1, from, 0, 8);
        to = new byte[8];
        System.arraycopy(bytes, 9, to, 0, 8);
        rewardTypeCode = new byte[2];
        System.arraycopy(bytes, 17, rewardTypeCode, 0, 2);
        rewardTypeFingerprint = new byte[8];
        System.arraycopy(bytes, 19, rewardTypeFingerprint, 0, 8);
        amount = new byte[bytes[27]];
        System.arraycopy(bytes, 28, amount, 0, bytes[27]);
        signature = new byte[8];
        System.arraycopy(bytes, bytes.length - 8, signature, 0, 8);
    }

    public Transaction(TransactionType transactionType, Account fromAcct, Account toAcct, long amount, RewardType rewardType) {
        this.txnType = transactionType;
        this.from = fromAcct.getAccountId();
        this.to = toAcct.getAccountId();
        this.rewardTypeCode = rewardType.getCode();
        this.rewardTypeFingerprint = rewardType.getFingerprint();
        this.amount = String.valueOf(amount).getBytes();
    }

    /** get sign data in bytes
     * 1st byte: transaction type
     * 8 bytes from: sender
     * 8 bytes to: receiver
     * 2 bytes reward type code
     * 8 bytes reward type fingerprint
     * 1 byte amount size
     * number of bytes of amount indicated by amount size

     * @return byte array of transaction to sign
     */
    public byte[] getSignData() {
        byte[] bytes = new byte[1 + 8 + 8 + 2 + 8 + 1 + amount.length];
        bytes[0] = (byte) txnType.getValue();
        System.arraycopy(from, 0, bytes, 1, 8);
        System.arraycopy(to, 0, bytes, 9, 8);
        System.arraycopy(rewardTypeCode, 0, bytes, 17, 2);
        System.arraycopy(rewardTypeFingerprint, 0, bytes, 19, 8);
        bytes[27] = (byte) amount.length;
        System.arraycopy(amount, 0, bytes, 28, amount.length);
        return bytes;
    }

    /** get transaction in bytes
     * getSignData() + 8 bytes signature
     * @return byte array of transaction
     */
    public byte[] getBytes() {
        byte[] bytes = new byte[getSignData().length + 8];
        System.arraycopy(getSignData(), 0, bytes, 0, getSignData().length);
        System.arraycopy(signature, 0, bytes, getSignData().length, 8);
        return bytes;
    }

    /**
     * Sign data with sender private key
     */
    public void sign(PrivateKey privateKey) throws Exception{
        signature = SignatureGenerator.generate(privateKey, getSignData());
    }

    /**
     * Verify signature with sender public key
     */
    public boolean verifySignature(PublicKey publicKey) {
        return SignatureGenerator.verifySignature(publicKey, signature, getSignData());
    }


}
