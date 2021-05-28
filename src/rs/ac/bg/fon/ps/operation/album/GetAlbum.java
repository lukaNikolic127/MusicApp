package rs.ac.bg.fon.ps.operation.album;

import java.sql.SQLException;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Album;
import rs.ac.bg.fon.ps.domain.Band;
import rs.ac.bg.fon.ps.domain.Genre;
import rs.ac.bg.fon.ps.domain.Performer;
import rs.ac.bg.fon.ps.domain.PerformerSong;
import rs.ac.bg.fon.ps.domain.Singer;
import rs.ac.bg.fon.ps.domain.Song;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

public class GetAlbum extends AbstractGenericOperation {

    private Album album;

    public Album getAlbum() {
        return album;
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Album)) {
            throw new Exception();
        }
        this.album = (Album) repository.get((Album) param, null);
        if (this.album.getAlbumID() == 0) {
            throw new Exception("Album does not exist.");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        User user = (User) repository.get(album.getUser(), null);
        album.setUser(user);

        List<Song> songs = (List<Song>) (List<?>) repository.getAll(new Song(), Song.generateAlbumCondition(album));

        for (Song song : songs) {

            Genre genre = (Genre) repository.get(song.getGenre(), null);
            song.setGenre(genre);
            
            User userSong = (User) repository.get(song.getUser(), null);
            song.setUser(userSong);

            List<PerformerSong> performerSongs = (List<PerformerSong>) (List<?>) repository.getAll(new PerformerSong(),
                    PerformerSong.generateSongCondition(song));

            for (PerformerSong performerSong : performerSongs) {
                performerSong.setSong(song);

                Performer performer = (Performer) repository.get(performerSong.getPerformer(), null);
                switch (performer.getType()) {
                    case TYPE_SINGER:
                        Singer singer = new Singer();
                        singer.setPerformerID(performer.getPerformerID());
                        singer = (Singer) repository.get(singer, null);
                        performerSong.setPerformer(singer);
                        break;
                    case TYPE_BAND:
                        Band band = new Band();
                        band.setPerformerID(performer.getPerformerID());
                        band = (Band) repository.get(band, null);
                        performerSong.setPerformer(band);
                        break;
                }
            }

            song.setPerformerSongs(performerSongs);
        }
        album.setSongs(songs);
    }
}
