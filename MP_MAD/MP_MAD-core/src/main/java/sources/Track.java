package sources;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import javafx.util.Duration;

/**
 *
 * @author Kaveri
 */
public class Track {
    private String title;
    private String artist;
    private String album;
    private String year;
    private String genre;
    private String trackNumber;
    private Duration length;
    private String path;


    public Track(File file) {
        AudioFile f = null;
        try {
            f = AudioFileIO.read(file);
        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        Tag tag = f.getTag();
        this.artist = tag.getFirst(FieldKey.ARTIST);
        this.album = tag.getFirst(FieldKey.ALBUM);
        this.title = tag.getFirst(FieldKey.TITLE);
        this.year = tag.getFirst(FieldKey.YEAR);
        this.genre = tag.getFirst(FieldKey.GENRE);
        this.trackNumber = tag.getFirst(FieldKey.TRACK);
        this.length = Duration.seconds(f.getAudioHeader().getTrackLength());
        this.path = file.getAbsolutePath();
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public Duration getLength() {
        return length;
    }

    public String getPath() {
        return path;
    }  
}
