package rs.ac.bg.fon.ps.operation.album;

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

public class SearchAlbums extends AbstractGenericOperation {

    private List<Album> albums;

    public List<Album> getAlbums() {
        return albums;
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Album)) {
            throw new Exception();
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Album album = (Album) param;
        StringBuilder sb = new StringBuilder();

        boolean previousSet = false;

        if (album.getAlbumID() != 0) {
            sb.append("id = ").append(album.getAlbumID());
            previousSet = true;
        }
        if (album.getName() != null && !album.getName().isEmpty()) {
            if (previousSet) {
                sb.append(" AND ");
            }
            sb.append("name = '").append(album.getName()).append("'");
            previousSet = true;
        }
        if (album.getDuration() != 0) {
            if (previousSet) {
                sb.append(" AND ");
            }
            sb.append("duration <= ").append(album.getDuration());
            previousSet = true;
        }
        if (album.getNumberOfSongs() != 0) {
            if (previousSet) {
                sb.append(" AND ");
            }
            sb.append("numberOfSongs = ").append(album.getNumberOfSongs());
            previousSet = true;
        }
        if (album.getUser() != null) {
            if (previousSet) {
                sb.append(" AND ");
            }
            sb.append("userId = ").append(album.getUser().getUserID());
        }
        String searchCondition = null;
        if(!sb.toString().isEmpty()){
            searchCondition = sb.toString();
        }
        albums = (List<Album>) (List<?>) repository.getAll(new Album(), searchCondition);

        for (Album alb : albums) {

            User user = (User) repository.get(alb.getUser(), null);
            alb.setUser(user);

            List<Song> songs = (List<Song>) (List<?>) repository.getAll(new Song(), Song.generateAlbumCondition(album));

            for (Song song : songs) {

                Genre genre = (Genre) repository.get(song.getGenre(), null);
                song.setGenre(genre);

                List<PerformerSong> performerSongs = (List<PerformerSong>) (List<?>) repository.getAll(new PerformerSong(), 
                        PerformerSong.generateSongCondition(song));

                for (PerformerSong performerSong : performerSongs) {
                    performerSong.setSong(song);
                    Performer performer = (Performer) repository.get((Performer)performerSong.getPerformer(), null);
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

            alb.setSongs(songs);
        }
    }
}
