package rs.ac.bg.fon.ps.operation.country;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Country;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

public class GetAllCountries extends AbstractGenericOperation {

    private List<Country> countries;

    public List<Country> getCountries() {
        return countries;
    }
    
    @Override
    protected void preconditions(Object param) throws Exception {
        
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        countries = (List<Country>) (List<?>) repository.getAll(new Country(), null);
    }
    
}
