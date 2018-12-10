package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button nextButton;

    @FXML
    private Slider volume;

    @FXML
    private ToggleButton loopmodeButton;

    @FXML
    private Button previousButton;

    @FXML
    private ToggleButton playmodeButton;

    @FXML
    private Slider progressBar;

    @FXML
    private Button playButton;

    @FXML
    void playButtonActionListener(ActionEvent event) {

    }

    @FXML
    void previousButtonActionListener(ActionEvent event) {

    }

    @FXML
    void nextButtonActionListener(ActionEvent event) {

    }

    @FXML
    void playmodeButtonActionListener(ActionEvent event) {

    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}

