package rs.ac.bg.fon.ps.operation.performer;

import rs.ac.bg.fon.ps.communication.Operation;
import rs.ac.bg.fon.ps.domain.Band;
import rs.ac.bg.fon.ps.domain.Performer;
import rs.ac.bg.fon.ps.domain.Singer;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;
import rs.ac.bg.fon.ps.repository.db.DbRepository;

public class SavePerformer extends AbstractGenericOperation {

    @Override
    protected void startTransaction() throws Exception {
        ((DbRepository) repository).connect(Operation.ADD_PERFORMER);
    }

    @Override
    protected void disconnect() throws Exception {
        ((DbRepository) repository).disconnect(Operation.ADD_PERFORMER);
    }

    @Override
    protected void commitTransaction() throws Exception {
        ((DbRepository) repository).commit(Operation.ADD_PERFORMER);
    }

    @Override
    protected void rollbackTransaction() throws Exception {
        ((DbRepository) repository).rollback(Operation.ADD_PERFORMER);
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        try {
            if (param == null || !(param instanceof Performer)) {
                throw new Exception();
            }
            Performer performer = (Performer) param;
            if (performer instanceof Singer) {
                Singer singer = (Singer) performer;
                Singer result = (Singer) repository.get(singer, singer.getArtistNameCondition());
                if (result.getPerformerID() != 0) {
                    throw new Exception("Singer '" + singer.getArtistName() + "' already exists!");
                }
            } else if (performer instanceof Band) {
                Band band = (Band) performer;
                Band result = (Band) repository.get(band, band.getBandNameCondition());
                if (result.getPerformerID() != 0) {
                    throw new Exception("Band '" + band.getBandName() + "' already exists!");
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Performer performer = (Performer) param;
        long newID = repository.getMaxId(new Performer()) + 1;
        performer.setPerformerID(newID);
        repository.add(performer);
    }

}
