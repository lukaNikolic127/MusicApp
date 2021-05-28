package rs.ac.bg.fon.ps.operation.performer;

import rs.ac.bg.fon.ps.domain.Band;
import rs.ac.bg.fon.ps.domain.Performer;
import rs.ac.bg.fon.ps.domain.Singer;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

public class RemovePerformer extends AbstractGenericOperation {

    private Performer performer;
    
    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Performer)) {
            throw new Exception();
        }

        Performer performer = (Performer) param; // za brisanje

        if (performer instanceof Singer) {
            Singer singer = (Singer) performer;
            this.performer = (Performer) repository.get(singer, null);

        } else if (performer instanceof Band) {
            Band band = (Band) performer;
            this.performer = (Performer) repository.get(band, null);
        }

        if (this.performer.getPerformerID() == 0) {
            throw new Exception("Performer does not exist.");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.delete(performer);
    }

}
