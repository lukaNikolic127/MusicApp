package rs.ac.bg.fon.ps.operation.album;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Album;
import rs.ac.bg.fon.ps.domain.PerformerSong;
import rs.ac.bg.fon.ps.domain.Song;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

public class RemoveAlbum extends AbstractGenericOperation {

    private Album album;
    
    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Album)) {
            throw new Exception();
        }
        this.album = (Album) repository.get((Album) param, null);
        if (this.album.getAlbumID() == 0) {
            throw new Exception("Album does not exist.");
        } else {
            User user = (User) repository.get(this.album.getUser(), null);
            this.album.setUser(user);
            List<Song> songs = repository.getAll(new Song(), Song.generateAlbumCondition(this.album));
            for (Song song : songs) {
                List<PerformerSong> performerSongs = repository.getAll(new PerformerSong(), PerformerSong.generateSongCondition(song));
                song.setPerformerSongs(performerSongs);
            }
            this.album.setSongs(songs);
        }

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        for (Song song : album.getSongs()) {
            for (PerformerSong performerSong : song.getPerformerSongs()) {
                repository.delete(performerSong);
            }
            repository.delete(song);
        }

        repository.delete(album);
        
    }

}
