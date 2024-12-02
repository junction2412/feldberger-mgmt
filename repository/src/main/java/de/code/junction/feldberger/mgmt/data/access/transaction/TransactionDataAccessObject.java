package de.code.junction.feldberger.mgmt.data.access.transaction;

import de.code.junction.feldberger.mgmt.data.access.DataAccessObject;

public interface TransactionDataAccessObject extends DataAccessObject<Integer, Transaction> {

    void persistTransaction(Transaction transaction);
}
