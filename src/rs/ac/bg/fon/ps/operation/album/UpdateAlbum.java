package rs.ac.bg.fon.ps.operation.album;

import rs.ac.bg.fon.ps.communication.Operation;
import rs.ac.bg.fon.ps.domain.Album;
import rs.ac.bg.fon.ps.domain.PerformerSong;
import rs.ac.bg.fon.ps.domain.Song;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;
import rs.ac.bg.fon.ps.repository.db.DbRepository;

public class UpdateAlbum extends AbstractGenericOperation {

    @Override
    protected void startTransaction() throws Exception {
        ((DbRepository) repository).connect(Operation.UPDATE_ALBUM);
    }

    @Override
    protected void disconnect() throws Exception {
        ((DbRepository) repository).disconnect(Operation.UPDATE_ALBUM);
    }

    @Override
    protected void commitTransaction() throws Exception {
        ((DbRepository) repository).commit(Operation.UPDATE_ALBUM);
    }

    @Override
    protected void rollbackTransaction() throws Exception {
        ((DbRepository) repository).rollback(Operation.UPDATE_ALBUM);
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Album)) {
            throw new Exception();
        }
        Album album = (Album) param;
        Album result = (Album) repository.get(album, null);
        if (result.getAlbumID() == 0) {
            throw new Exception("Album does not exist!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        Album album = (Album) param;
        repository.edit(album);

        for (Song song : album.getSongs()) {
            if (song.getSongID() != 0) {    // Update song
                repository.edit(song);
                for (PerformerSong performerSong : song.getPerformerSongs()) {
                    repository.delete(performerSong);
                }
                for (PerformerSong performerSong : song.getPerformerSongs()) {
                    repository.add(performerSong);
                }
            } else {    // New song
                song.setAlbum(album);
                long songID = repository.getMaxId(new Song()) + 1;
                song.setSongID(songID);
                repository.add(song);

                for (PerformerSong performerSong : song.getPerformerSongs()) {
                    repository.add(performerSong);
                }
            }
        }
    }

}
