package rs.ac.bg.fon.ps.repository.db;

public class ConnectionBusyException extends Exception {

    public ConnectionBusyException() {
    }

    @Override
    public String getMessage() {
        return "Connection is busy, please try later!";
    }
    
}
