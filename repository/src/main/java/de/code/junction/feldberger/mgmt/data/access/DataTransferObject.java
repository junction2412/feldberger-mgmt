package de.code.junction.feldberger.mgmt.data.access;

/**
 * An object to represent a specific set of related data.
 *
 * @param <ID> identifier type
 * @author J. Murray
 */
@SuppressWarnings("unused")
public interface DataTransferObject<ID> {

    /**
     * @return identifier value
     */
    ID getID();

    /**
     * @param id identifier value
     */
    void setID(ID id);
}
