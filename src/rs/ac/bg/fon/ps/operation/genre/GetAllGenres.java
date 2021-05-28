package rs.ac.bg.fon.ps.operation.genre;

import java.sql.SQLException;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Genre;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

public class GetAllGenres extends AbstractGenericOperation {

    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    @Override
    protected void preconditions(Object param) throws Exception {

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        genres = (List<Genre>) (List<?>) repository.getAll(new Genre(), null);
    }

}
