package rs.ac.bg.fon.ps.operation.user;

import java.util.List;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

public class GetUser extends AbstractGenericOperation {

    private User loggedUser;

    public User getLoggedUser() {
        return loggedUser;
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof User)) {
            throw new Exception();
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        User user = (User) param;
        User result;
        try {
            result = (User) repository.get(new User(), user.getUsernameCondition());
        } catch (Exception ex) {
            throw new Exception("Loading user failed!");
        }
        if (result.getUserID() == 0) {
            throw new Exception("Unknown user!");
        }
        loggedUser = result;
    }

}
