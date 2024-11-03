package de.code.junction.feldberger.mgmt.data.encryption;

/**
 * A service interface to leverage en-/decryption of specified datatypes.
 *
 * @param <E> encrypted type
 * @param <D> decrypted type
 * @author J. Murray
 */
public interface EncryptionService<E, D> {

    /**
     * Encrypts the provided clear text using encryption.
     *
     * @param clear The clear input to be encrypted.
     * @return The encrypted representation of the input.
     */
    E encrypt(D clear);

    /**
     * Decrypts the provided encrypted input using decryption to retrieve the original input.
     *
     * @param encrypted The encrypted input to be decrypted.
     * @return The original input obtained from decryption.
     */
    D decrypt(E encrypted);
}
