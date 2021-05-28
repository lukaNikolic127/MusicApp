package rs.ac.bg.fon.ps.repository.db;

import rs.ac.bg.fon.ps.communication.Operation;

public class Connection {
    private Operation operation;
    private DbConnectionFactory connection;

    public Connection() {
    }

    public Connection(Operation operation, DbConnectionFactory connection) {
        this.operation = operation;
        this.connection = connection;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public DbConnectionFactory getConnection() {
        return connection;
    }

    public void setConnection(DbConnectionFactory connection) {
        this.connection = connection;
    }
    
}
