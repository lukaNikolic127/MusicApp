package rs.ac.bg.fon.ps.operation.performer;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Band;
import rs.ac.bg.fon.ps.domain.Country;
import rs.ac.bg.fon.ps.domain.Performer;
import rs.ac.bg.fon.ps.domain.Singer;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

public class SearchPerformers extends AbstractGenericOperation {

    private List<Performer> performers;

    public List<Performer> getPerformers() {
        return performers;
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Performer)) {
            throw new Exception();
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Performer performer = (Performer) param;

        if (performer instanceof Singer) {
            Singer singer = (Singer) performer;
            StringBuilder sb = new StringBuilder();
            sb.append(singer.getTypeCondition());
            if (singer.getFirstName() != null && !singer.getFirstName().isEmpty()) {
                sb.append(" AND firstName = '").append(singer.getFirstName()).append("'");
            }
            if (singer.getLastName() != null && !singer.getLastName().isEmpty()) {
                sb.append(" AND lastName = '").append(singer.getLastName()).append("'");
            }
            if (singer.getArtistName() != null && !singer.getArtistName().isEmpty()) {
                sb.append(" AND artistName = '").append(singer.getArtistName()).append("'");
            }
            if (singer.getPerformerID() != 0) {
                sb.append(" AND id = ").append(singer.getPerformerID());
            }
            if (singer.getCountry() != null && singer.getCountry().getCountryID() != 0) {
                sb.append(" AND countryId = ").append(singer.getCountry().getCountryID());
            }
            if (singer.getUser() != null && singer.getUser().getUserID() != 0) {
                sb.append(" AND userId = ").append(singer.getUser().getUserID());
            }

            List<Performer> singers = repository.getAll(singer, sb.toString());
            performers = singers;

            for (Performer p : performers) {
                Country country = (Country) repository.get(p.getCountry(), null);
                p.setCountry(country);

                User user = (User) repository.get(p.getUser(), null);
                p.setUser(user);
            }

        } else if (performer instanceof Band) {
            Band band = (Band) performer;
            StringBuilder sb = new StringBuilder();
            sb.append(band.getTypeCondition());
            if (band.getBandName() != null && !band.getBandName().isEmpty()) {
                sb.append(" AND bandName = '").append(band.getBandName()).append("'");
            }
            if (band.getPerformerID() != 0) {
                sb.append(" AND id = ").append(band.getPerformerID());
            }
            if (band.getCountry() != null && band.getCountry().getCountryID() != 0) {
                sb.append(" AND countryId = ").append(band.getCountry().getCountryID());
            }
            if (band.getUser() != null && band.getUser().getUserID() != 0) {
                sb.append(" AND userId = ").append(band.getUser().getUserID());
            }
            List<Performer> bands = repository.getAll(band, sb.toString());
            performers = bands;

            for (Performer p : performers) {
                Country country = (Country) repository.get(p.getCountry(), null);
                p.setCountry(country);

                User user = (User) repository.get(p.getUser(), null);
                p.setUser(user);
            }
        }
    }

}
