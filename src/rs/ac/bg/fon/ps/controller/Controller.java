package rs.ac.bg.fon.ps.controller;

import java.sql.SQLException;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Album;
import rs.ac.bg.fon.ps.domain.Country;
import rs.ac.bg.fon.ps.domain.Genre;
import rs.ac.bg.fon.ps.domain.Performer;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;
import rs.ac.bg.fon.ps.operation.album.GetAlbum;
import rs.ac.bg.fon.ps.operation.album.GetAllAlbums;
import rs.ac.bg.fon.ps.operation.album.RemoveAlbum;
import rs.ac.bg.fon.ps.operation.album.SaveAlbum;
import rs.ac.bg.fon.ps.operation.album.SearchAlbums;
import rs.ac.bg.fon.ps.operation.album.UpdateAlbum;
import rs.ac.bg.fon.ps.operation.country.GetAllCountries;
import rs.ac.bg.fon.ps.operation.genre.GetAllGenres;
import rs.ac.bg.fon.ps.operation.performer.GetAllPerformers;
import rs.ac.bg.fon.ps.operation.performer.GetPerformer;
import rs.ac.bg.fon.ps.operation.performer.RemovePerformer;
import rs.ac.bg.fon.ps.operation.performer.SavePerformer;
import rs.ac.bg.fon.ps.operation.performer.SearchPerformers;
import rs.ac.bg.fon.ps.operation.user.GetAllUsers;
import rs.ac.bg.fon.ps.operation.user.GetUser;

public class Controller {

    private static Controller instance;

    private Controller() {

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public User getUser(User user) throws Exception {
        AbstractGenericOperation operation = new GetUser();
        try {
            ((GetUser) operation).execute(user);
            return ((GetUser) operation).getLoggedUser();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Country> getAllCountries() throws Exception {
        AbstractGenericOperation operation = new GetAllCountries();
        try {
            operation.execute(null);
            return ((GetAllCountries) operation).getCountries();
        } catch (Exception ex) {
            throw new Exception("Loading list of countries failed!");
        }
    }

    public void removePerformer(Performer performer) throws Exception {
        AbstractGenericOperation operation = new RemovePerformer();
        try {
            ((RemovePerformer) operation).execute(performer);
        } catch (SQLException ex) {
            throw new Exception("Deleting performer failed!");
        } catch (Exception ex) {
            throw new Exception("Deleting performer failed!\n" + ex.getMessage());
        }
    }

    public void savePerformer(Performer performer) throws Exception {
        try {
            AbstractGenericOperation operation = new SavePerformer();
            ((SavePerformer) operation).execute(performer);
        } catch (SQLException sqlEx) {
            throw new Exception("Saving performer failed!");
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Genre> getAllGenres() throws Exception {
        AbstractGenericOperation operation = new GetAllGenres();
        try {
            operation.execute(null);
            return ((GetAllGenres) operation).getGenres();
        } catch (Exception ex) {
            throw new Exception("Loading list of genres failed!");
        }
    }

    public void saveAlbum(Album album) throws Exception {
        try {
            AbstractGenericOperation operation = new SaveAlbum();
            ((SaveAlbum) operation).execute(album);
        } catch (SQLException sqlEx) {
            throw new Exception("Saving album failed!");
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Album> getAllAlbums() throws Exception {
        AbstractGenericOperation operation = new GetAllAlbums();
        try {
            operation.execute(null);
            return ((GetAllAlbums) operation).getAlbums();
        } catch (Exception ex) {
            throw new Exception("Loading list of albums failed!");
        }
    }

    public List<User> getAllUsers() throws Exception {
        AbstractGenericOperation operation = new GetAllUsers();
        try {
            operation.execute(null);
            return ((GetAllUsers) operation).getUsers();
        } catch (Exception ex) {
            throw new Exception("Loading list of users failed!");
        }
    }

    public List<Performer> searchPerformers(Performer performer) throws Exception {
        AbstractGenericOperation operation = new SearchPerformers();
        try {
            ((SearchPerformers) operation).execute(performer);
            return ((SearchPerformers) operation).getPerformers();
        } catch (Exception ex) {
            throw new Exception("Searching performers failed!");
        }
    }

    public List<Performer> getAllPerformers(Performer performer) throws Exception {
        AbstractGenericOperation operation = new GetAllPerformers();
        try {
            ((GetAllPerformers) operation).execute(performer);
            return ((GetAllPerformers) operation).getPerformers();
        } catch (Exception ex) {
            throw new Exception("Loading list of performers failed!");
        }
    }

    public List<Album> searchAlbums(Album album) throws Exception {
        AbstractGenericOperation operation = new SearchAlbums();
        try {
            ((SearchAlbums) operation).execute(album);
            return ((SearchAlbums) operation).getAlbums();
        } catch (Exception ex) {
            throw new Exception("Searching albums failed!");
        }
    }

    public void removeAlbum(Album album) throws Exception {
        AbstractGenericOperation operation = new RemoveAlbum();
        try {
            ((RemoveAlbum) operation).execute(album);
        } catch (SQLException ex) {
            throw new Exception("Deleting album failed!");
        } catch (Exception ex) {
            throw new Exception("Deleting album failed!\n" + ex.getMessage());
        }
    }

    public void updateAlbum(Album album) throws Exception {
        try {
            AbstractGenericOperation operation = new UpdateAlbum();
            ((UpdateAlbum) operation).execute(album);
        } catch (SQLException ex) {
            throw new Exception("Updating album failed!");
        } catch (Exception ex) {
            throw new Exception("Updating album failed!\n" + ex.getMessage());
        }
    }

    public Performer getPerformer(Performer performer) throws Exception {
        AbstractGenericOperation operation = new GetPerformer();
        try {
            ((GetPerformer) operation).execute(performer);
            return ((GetPerformer) operation).getPerformer();
        } catch (Exception ex) {
            throw new Exception("Loading performer failed!");
        }
    }

    public Album getAlbum(Album album) throws Exception {
        AbstractGenericOperation operation = new GetAlbum();
        try {
            ((GetAlbum) operation).execute(album);
            return ((GetAlbum) operation).getAlbum();
        } catch (Exception ex) {
            throw new Exception("Loading album failed!\n" + ex.getMessage());
        }
    }
}
