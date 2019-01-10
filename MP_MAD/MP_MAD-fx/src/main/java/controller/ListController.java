package controller;

import facade.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import sources.Track;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListController implements Initializable {

    private Facade facade;

    @FXML
    private TableColumn<?, Integer> nr;

    @FXML
    private TableColumn<Track, String> trackNumber;

    @FXML
    private TableColumn<Track, String> artist;

    @FXML
    private TableColumn<Track, String> year;

    @FXML
    private TableColumn<Track, String> album;

    @FXML
    private TableColumn<Track, String> name;

    @FXML
    private TableColumn<Track, String> genre;

    @FXML
    private TableColumn<Track, Integer> time;

    @FXML
    private TableView<Track> trackTable;

    @FXML
    void handle7(DragEvent event) {
        //System.out.println("On Drag Dropped");
        Dragboard db = event.getDragboard();
        List<File> files = (ArrayList<File>) db.getContent(DataFormat.FILES);

        boolean success = false;
        if (files != null) {
            File file = files.get(0);
            try {
                Track track = new Track(file);
                facade.addTrack(0, track);
                ObservableList<Track> tracks = FXCollections.observableArrayList();
                tracks.addAll(Facade.getPlaylistManager().getPlaylist(0).getTracks());

                trackTable.setItems(tracks);
                success = true;
            } catch (ReadOnlyFileException | InvalidAudioFrameException | CannotReadException | IOException | TagException ex) {
                Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        event.setDropCompleted(success);

        event.consume();
    }

    @FXML
    void handle(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facade = new Facade();
        trackNumber.setCellValueFactory(new PropertyValueFactory<Track, String>("trackNumber"));
        artist.setCellValueFactory(new PropertyValueFactory<Track, String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<Track, String>("album"));
        year.setCellValueFactory(new PropertyValueFactory<Track, String>("year"));
        genre.setCellValueFactory(new PropertyValueFactory<Track, String>("genre"));
        time.setCellValueFactory(new PropertyValueFactory<Track, Integer>("length"));
        name.setCellValueFactory(new PropertyValueFactory<Track, String>("title"));
    }
}