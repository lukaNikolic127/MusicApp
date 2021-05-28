package rs.ac.bg.fon.ps.operation.album;

import java.util.concurrent.TimeUnit;
import rs.ac.bg.fon.ps.communication.Operation;
import rs.ac.bg.fon.ps.domain.Album;
import rs.ac.bg.fon.ps.domain.PerformerSong;
import rs.ac.bg.fon.ps.domain.Song;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;
import rs.ac.bg.fon.ps.repository.db.DbRepository;

public class SaveAlbum extends AbstractGenericOperation {

    @Override
    protected void startTransaction() throws Exception {
        ((DbRepository) repository).connect(Operation.ADD_ALBUM);
    }

    @Override
    protected void disconnect() throws Exception {
        ((DbRepository) repository).disconnect(Operation.ADD_ALBUM);
    }

    @Override
    protected void commitTransaction() throws Exception {
        ((DbRepository) repository).commit(Operation.ADD_ALBUM);
    }

    @Override
    protected void rollbackTransaction() throws Exception {
        ((DbRepository) repository).rollback(Operation.ADD_ALBUM);
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Album)) {
            throw new Exception();
        }
        Album album = (Album) param;
        Album result = (Album) repository.get(album, album.getNameCondition());

        if (result.getAlbumID() != 0) {
            throw new Exception("Album '" + album.getName() + "' already exists!");
        }

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Album album = (Album) param;

        long albumID = repository.getMaxId(new Album()) + 1;
        album.setAlbumID(albumID);
        repository.add(album);

        for (Song song : album.getSongs()) {
            song.setAlbum(album);
            long songID = repository.getMaxId(new Song()) + 1;
            song.setSongID(songID);
            repository.add(song);

            for (PerformerSong performerSong : song.getPerformerSongs()) {
                repository.add(performerSong);
            }
        }
        //TimeUnit.SECONDS.sleep(10);
    }

}
