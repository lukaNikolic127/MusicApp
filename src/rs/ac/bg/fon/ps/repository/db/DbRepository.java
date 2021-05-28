package rs.ac.bg.fon.ps.repository.db;

import rs.ac.bg.fon.ps.communication.Operation;
import rs.ac.bg.fon.ps.repository.Repository;

public interface DbRepository<T> extends Repository<T> {

    default public void connect(Operation operation) throws Exception {
        if (operation != null) {
            ConnectionPool.getInstance().getConnection(operation);
        } else {
            DbConnectionFactory.getInstance().getConnection();
        }
    }

    default public void disconnect(Operation operation) throws Exception {
        if (operation != null) {
            ConnectionPool.getInstance().freeConnection(operation);
        } else {
            DbConnectionFactory.getInstance().getConnection().close();
        }
    }

    default public void commit(Operation operation) throws Exception {
        if (operation != null) {
            ConnectionPool.getInstance().commit(operation);
        } else {
            DbConnectionFactory.getInstance().getConnection().commit();
        }
    }

    default public void rollback(Operation operation) throws Exception {
        if (operation != null) {
            ConnectionPool.getInstance().rollback(operation);
        } else {
            DbConnectionFactory.getInstance().getConnection().rollback();
        }
    }

}
