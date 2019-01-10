package controller;

import facade.Facade;
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
        //System.out.println("On Drag Dropped");
        Dragboard db = event.getDragboard();
        List<File> files = (ArrayList<File>) db.getContent(DataFormat.FILES);

        boolean success = false;
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                Track track = new Track(file);
                facade.addTrack(parent.getPlaylistContainer().getSelectionModel().getSelectedIndex(), track);
                ObservableList<Track> tracks = FXCollections.observableArrayList();
                tracks.addAll(Facade.getPlaylistManager().getPlaylist(parent.getPlaylistContainer().getSelectionModel().getSelectedIndex()).getTracks());

                trackTable.setItems(tracks);
                success = true;
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
        if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
            facade.playTrack(parent.getPlaylistContainer().getSelectionModel().getSelectedIndex(), trackTable.getSelectionModel().getSelectedItem(), false);
        }
    }

    @FXML
    void releasedActionListener(MouseEvent event) {
        facade.editPlaylist(parent.getPlaylistContainer().getSelectionModel().getSelectedIndex(), trackTable.getItems());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trackNumber.setCellValueFactory(new PropertyValueFactory<Track, String>("trackNumber"));
        artist.setCellValueFactory(new PropertyValueFactory<Track, String>("artist"));
        album.setCellValueFactory(new PropertyValueFactory<Track, String>("album"));
        year.setCellValueFactory(new PropertyValueFactory<Track, String>("year"));
        genre.setCellValueFactory(new PropertyValueFactory<Track, String>("genre"));
        time.setCellValueFactory(new PropertyValueFactory<Track, Integer>("length"));
        name.setCellValueFactory(new PropertyValueFactory<Track, String>("title"));
        ContextMenu contextMenu = new ContextMenu();

        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                facade.removetrack(parent.getPlaylistContainer().getSelectionModel().getSelectedIndex(), trackTable.getSelectionModel().getSelectedItem());
                trackTable.getItems().remove(trackTable.getSelectionModel().getSelectedItem());
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