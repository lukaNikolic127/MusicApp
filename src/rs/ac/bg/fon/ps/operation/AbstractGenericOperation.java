package rs.ac.bg.fon.ps.operation;

import rs.ac.bg.fon.ps.repository.Repository;
import rs.ac.bg.fon.ps.repository.db.ConnectionBusyException;
import rs.ac.bg.fon.ps.repository.db.DbRepository;
import rs.ac.bg.fon.ps.repository.impl.RepositoryDBGeneric;

public abstract class AbstractGenericOperation {

    protected final Repository repository;

    public AbstractGenericOperation() {
        this.repository = new RepositoryDBGeneric();
    }

    public final void execute(Object param) throws Exception {
        try {
            preconditions(param);
            startTransaction();
            executeOperation(param);
            commitTransaction();
        } catch (ConnectionBusyException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            rollbackTransaction();
            disconnect();
            throw e;
        }
        disconnect();
    }

    protected abstract void preconditions(Object param) throws Exception;

    protected void startTransaction() throws ConnectionBusyException, Exception {
        ((DbRepository) repository).connect(null);
    }

    protected abstract void executeOperation(Object param) throws Exception;

    protected void commitTransaction() throws Exception {
        ((DbRepository) repository).commit(null);
    }

    protected void rollbackTransaction() throws Exception {
        ((DbRepository) repository).rollback(null);
    }

    protected void disconnect() throws Exception {
        ((DbRepository) repository).disconnect(null);
    }

}
