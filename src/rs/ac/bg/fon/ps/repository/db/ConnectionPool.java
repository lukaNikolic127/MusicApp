package rs.ac.bg.fon.ps.repository.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rs.ac.bg.fon.ps.communication.Operation;

public class ConnectionPool {

    private static ConnectionPool instance;
    private final List<Connection> freeConnections = new ArrayList<>();
    private final List<Connection> busyConnections = new ArrayList<>();

    private ConnectionPool() {
        freeConnections.add(new Connection(Operation.ADD_PERFORMER, DbConnectionFactory.getInstance()));
        freeConnections.add(new Connection(Operation.ADD_ALBUM, DbConnectionFactory.getInstance()));
        freeConnections.add(new Connection(Operation.UPDATE_ALBUM, DbConnectionFactory.getInstance()));
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public void getConnection(Operation operation) throws Exception {
        Connection conn = getIfFree(operation);
        freeConnections.remove(conn);
        busyConnections.add(conn);
    }

    public void freeConnection(Operation operation) {
        Connection conn = getIfBusy(operation);
        if (conn != null) {
            busyConnections.remove(conn);
            freeConnections.add(conn);
        }
    }

    private Connection getIfFree(Operation operation) throws Exception {

        switch (operation) {
            case UPDATE_ALBUM:
                for (Connection busyConnection : busyConnections) {
                    if (busyConnection.getOperation().equals(Operation.UPDATE_ALBUM)
                            || busyConnection.getOperation().equals(Operation.ADD_ALBUM)) {
                        throw new ConnectionBusyException();
                    }
                }
                for (Connection freeConnection : freeConnections) {
                    if (freeConnection.getOperation().equals(Operation.UPDATE_ALBUM)) {
                        return freeConnection;
                    }
                }
            case ADD_ALBUM:
                for (Connection busyConnection : busyConnections) {
                    if (busyConnection.getOperation().equals(Operation.UPDATE_ALBUM)
                            || busyConnection.getOperation().equals(Operation.ADD_ALBUM)) {
                        throw new ConnectionBusyException();
                    }
                }
                for (Connection freeConnection : freeConnections) {
                    if (freeConnection.getOperation().equals(Operation.ADD_ALBUM)) {
                        return freeConnection;
                    }
                }
            case ADD_PERFORMER:
                for (Connection busyConnection : busyConnections) {
                    if (busyConnection.getOperation().equals(Operation.ADD_PERFORMER)) {
                        throw new ConnectionBusyException();
                    }
                }
                for (Connection freeConnection : freeConnections) {
                    if (freeConnection.getOperation().equals(Operation.ADD_PERFORMER)) {
                        return freeConnection;
                    }
                }
            default:
                return null;
        }
    }

    private Connection getIfBusy(Operation operation) {
        for (Connection conn : busyConnections) {
            if (conn.getOperation().equals(operation)) {
                return conn;
            }
        }
        return null;
    }

    void commit(Operation operation) throws Exception {
        for (Connection conn : busyConnections) {
            if (conn.getOperation().equals(operation)) {
                conn.getConnection().getConnection().commit();
            }
        }
    }

    void rollback(Operation operation) throws Exception {
        for (Connection conn : busyConnections) {
            if (conn.getOperation().equals(operation)) {
                conn.getConnection().getConnection().rollback();
            }
        }
    }

}
