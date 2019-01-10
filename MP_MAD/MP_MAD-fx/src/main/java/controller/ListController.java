package controller;

import facade.Facade;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import sources.Track;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class ListController implements Initializable {
    
    private Facade facade = new Facade();

    @FXML
    private TableColumn<?, ?> nr;

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
    void handle7(DragEvent event) {
        System.out.println("On Drag Dropped");
        Dragboard db = event.getDragboard();
        List<File> files = (ArrayList<File>) db.getContent(DataFormat.FILES);

        boolean success = false;
        if (files != null) {
            File file = files.get(0);
            Track track;
            try {
                track = new Track(file);
                facade.addtrack(0, track);
                success = true;
            } catch (ReadOnlyFileException | InvalidAudioFrameException | CannotReadException | IOException | TagException ex) {
                Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //artist.setText(file.getAbsolutePath());
            
        }

        event.setDropCompleted(success);

        event.consume();
    }

    @FXML
    void handle(DragEvent event) {
        //System.out.println("On Drag Over");
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}