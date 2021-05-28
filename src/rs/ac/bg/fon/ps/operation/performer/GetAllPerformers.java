package rs.ac.bg.fon.ps.operation.performer;

import java.sql.SQLException;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Band;
import rs.ac.bg.fon.ps.domain.Country;
import rs.ac.bg.fon.ps.domain.Performer;
import rs.ac.bg.fon.ps.domain.Singer;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

public class GetAllPerformers extends AbstractGenericOperation {

    private List<Performer> performers;

    @Override
    protected void preconditions(Object param) throws Exception {

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Performer p = (Performer) param;
        if (p instanceof Band) {
            Band band = new Band();
            performers = (List<Performer>) (List<?>) repository.getAll(band, band.getTypeCondition());
        } else if (p instanceof Singer) {
            Singer singer = new Singer();
            performers = (List<Performer>) (List<?>) repository.getAll(singer, singer.getTypeCondition());
        }

        for (Performer performer : performers) {

            Country country = (Country) repository.get(performer.getCountry(), null);
            performer.setCountry(country);

            User user = (User) repository.get(performer.getUser(), null);
            performer.setUser(user);

        }
    }

    public List<Performer> getPerformers() {
        return performers;
    }

}
