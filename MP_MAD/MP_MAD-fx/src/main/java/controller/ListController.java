package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import sources.Track;

import java.net.URL;
import java.util.ResourceBundle;

public class ListController implements Initializable {

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
    void handle9(ActionEvent event) {
        System.out.println("On Drag Detected");
    }

    @FXML
    void handle8(ActionEvent event) {
        System.out.println("On Drag Done");
    }

    @FXML
    void handle7(ActionEvent event) {
        System.out.println("On Drag Dropped");
    }

    @FXML
    void handle6(ActionEvent event) {
        System.out.println("On Drag Entered");
    }

    @FXML
    void handle5(ActionEvent event) {
        System.out.println("On Drag Exited");
    }

    @FXML
    void handle(ActionEvent event) {
        System.out.println("On Drag Over");
    }

    @FXML
    void handle1(ActionEvent event) {
        System.out.println("On Mouse Drag Entered");
    }

    @FXML
    void handle2(ActionEvent event) {
        System.out.println("On Mouse Drag Exited");
    }

    @FXML
    void handle3(ActionEvent event) {
        System.out.println("On Mouser Drag Over");
    }

    @FXML
    void handle4(ActionEvent event) {
        System.out.println("On Mouse Drag Released");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
