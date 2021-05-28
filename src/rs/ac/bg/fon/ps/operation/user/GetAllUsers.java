package rs.ac.bg.fon.ps.operation.user;

import java.util.List;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;
import rs.ac.bg.fon.ps.repository.Repository;

public class GetAllUsers extends AbstractGenericOperation{

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    @Override
    protected void preconditions(Object param) throws Exception{
        
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        users = (List<User>) (List<?>) repository.getAll(new User(), null);
    }
    
}
