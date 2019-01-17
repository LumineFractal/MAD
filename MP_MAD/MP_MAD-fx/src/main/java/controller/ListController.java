package controller;

import facade.Facade;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import sources.Track;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListController implements Initializable {

    private MainController parent;

    private Facade facade;

    @FXML
    private TableColumn<Track, String> nr;

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
    private TableColumn<Track, String> time;

    @FXML
    private TableView<Track> trackTable;

    public MainController getParent() {
        return parent;
    }

    public Facade getFacade() {
        return facade;
    }

    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    public void setParent(MainController parent) {
        this.parent = parent;
    }

    @FXML
    void handle7(DragEvent event) {
        Dragboard db = event.getDragboard();
        List<File> files = (ArrayList<File>) db.getContent(DataFormat.FILES);

        boolean success = false;
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                Track track = new Track(file);

                String namePlaylist = parent.getPlaylistContainer().getSelectionModel().getSelectedItem().getText();
                if (facade.getPlaylist(namePlaylist) != null) {
                    facade.addTrack(facade.getIndexPlaylist(parent.getPlaylistContainer().getSelectionModel().getSelectedItem().getText()), track);
                } else {

                }
                parent.loadPlaylists();
                ObservableList<Track> tracks = FXCollections.observableArrayList();
                tracks.addAll(facade.getPlaylist(parent.getPlaylistContainer().getSelectionModel().getSelectedItem().getText()).getTracks());

                trackTable.setItems(tracks);
                success = true;
            }
            if (!parent.getRedoButton().isDisable()) {
                parent.getRedoButton().setDisable(true);
            }
            if (parent.getUndoButton().isDisable()) {
                parent.getUndoButton().setDisable(false);
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

    @FXML
    void listTrackActionListener(MouseEvent event) {
        if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY) && !trackTable.getSelectionModel().isEmpty()) {
            String namePlaylist = parent.getPlaylistContainer().getSelectionModel().getSelectedItem().getText();
            if (facade.getPlaylist(namePlaylist) == null) {
                namePlaylist = namePlaylist.substring(0, namePlaylist.length() - 5);
            }
            facade.playTrack(facade.getIndexPlaylist(namePlaylist), trackTable.getSelectionModel().getSelectedItem(), false);
            facade.setTrackInIterator(trackTable.getSelectionModel().getSelectedItem());
        }

        parent.changePlayButton(false);
    }

    @FXML
    void releasedActionListener(MouseEvent event) {
        String namePlaylist = parent.getPlaylistContainer().getSelectionModel().getSelectedItem().getText();
        if (facade.getPlaylist(namePlaylist) != null) {
            facade.editPlaylist(facade.getIndexPlaylist(parent.getPlaylistContainer().getSelectionModel().getSelectedItem().getText()), new ArrayList<>(trackTable.getItems()));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trackNumber.setCellValueFactory(new PropertyValueFactory<Track, String>("trackNumber"));
        name.setCellValueFactory(new PropertyValueFactory<Track, String>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<Track, String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<Track, String>("album"));
        year.setCellValueFactory(new PropertyValueFactory<Track, String>("year"));
        genre.setCellValueFactory(new PropertyValueFactory<Track, String>("genre"));
        time.setCellValueFactory(track -> new ReadOnlyStringWrapper(facade.timeConverter(track.getValue().getLength().toMillis())));
        nr.setCellValueFactory(new PropertyValueFactory<Track, String>("isPlaying"));
        
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                facade.removetrack(facade.getIndexPlaylist(parent.getPlaylistContainer().getSelectionModel().getSelectedItem().getText()), trackTable.getSelectionModel().getSelectedItem());
                parent.loadPlaylists();
                trackTable.getItems().remove(trackTable.getSelectionModel().getSelectedItem());
                if (!parent.getRedoButton().isDisable()) {
                    parent.getRedoButton().setDisable(true);
                }
                if (parent.getUndoButton().isDisable()) {
                    parent.getUndoButton().setDisable(false);
                }
            }
        });
        contextMenu.getItems().add(delete);
        trackTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(trackTable, event.getScreenX(), event.getScreenY());
            }
        });
    }
}