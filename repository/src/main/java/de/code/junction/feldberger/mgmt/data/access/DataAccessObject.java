package de.code.junction.feldberger.mgmt.data.access;

import java.util.List;
import java.util.Optional;

/**
 * The definition of an object responsible for accessing data defined as {@link DataTransferObject}s.
 *
 * @param <T>  identifier
 * @param <DTO> representation as a {@link DataTransferObject}
 * @author J. Murray
 */
public interface DataAccessObject<T, DTO extends DataTransferObject<T>> {

    /**
     * Find a data object by its identifier.
     *
     * @param id identifier
     * @return optional data
     */
    Optional<DTO> findById(T id);

    /**
     * Find all data objects.
     *
     * @return list of data
     */
    List<DTO> getAll();
}
