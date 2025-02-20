package de.code.junction.feldberger.mgmt.data.access;

/**
 * An object to represent a specific set of related data.
 *
 * @param <T> identifier type
 * @author J. Murray
 */
public interface DataTransferObject<T> {

    /**
     * @return identifier value
     */
    T getId();

    /**
     * @param id identifier value
     */
    void setId(T id);
}
