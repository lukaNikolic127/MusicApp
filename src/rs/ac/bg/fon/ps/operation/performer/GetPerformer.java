package rs.ac.bg.fon.ps.operation.performer;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Band;
import rs.ac.bg.fon.ps.domain.Country;
import rs.ac.bg.fon.ps.domain.Performer;
import rs.ac.bg.fon.ps.domain.Singer;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

public class GetPerformer extends AbstractGenericOperation {

    private Performer performer;

    public Performer getPerformer() {
        return performer;
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Performer)) {
            throw new Exception();
        }
        Performer performer = (Performer) param;
        if (performer instanceof Singer) {
            Singer singer = (Singer) performer;
            this.performer = (Performer) repository.get(singer, null);

        } else if (performer instanceof Band) {
            Band band = (Band) performer;
            this.performer = (Performer) repository.get(band, null);
        }
        if (this.performer.getPerformerID() == 0) {
            throw new Exception();
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Country country = (Country) repository.get(this.performer.getCountry(), null);
        this.performer.setCountry(country);

        User user = (User) repository.get(this.performer.getUser(), null);
        this.performer.setUser(user);
    }

}
