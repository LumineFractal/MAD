package controller;

import facade.Facade;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import player.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    private Facade facade = new Facade();

    @FXML
    private Button nextButton;

    @FXML
    private Slider volume;

    @FXML
    private MenuItem undoButton;

    @FXML
    private MenuItem redoButton;

    @FXML
    private ToggleButton loopmodeButton;

    @FXML
    private Button previousButton;

    @FXML
    private ToggleButton playmodeButton;

    @FXML
    private TabPane playlistContainer;

    @FXML
    private Slider progressBar;

    @FXML
    private Button playButton;

    @FXML
    private MenuItem menuAddList;

    @FXML
    private Label currentTime;
    
    @FXML
    private Label totalLength;

    
    public TabPane getPlaylistContainer() {
        return playlistContainer;
    }

    @FXML
    void playButtonActionListener(ActionEvent event) {
        if (!playlistContainer.getTabs().isEmpty()) {
            facade.playTrack(-1, null, true);
        }
        changePlayButton(true);
    }

    @FXML
    void previousButtonActionListener(ActionEvent event) {
        if (!playlistContainer.getTabs().isEmpty()) {
            facade.previousTrack();
        }
    }

    @FXML
    void nextButtonActionListener(ActionEvent event) {
        if (!playlistContainer.getTabs().isEmpty()) {
            facade.nextTrack();
        }
    }

    @FXML
    void playmodeButtonActionListener(ActionEvent event) {
        if (!playmodeButton.isSelected())
            facade.setNameIterator(true, true);
        else
            facade.setNameIterator(false, true);
    }

    @FXML
    void loopmodeButtonActionListener(ActionEvent event) {
        if (!loopmodeButton.isSelected())
            facade.setNameIterator(true, false);
        else
            facade.setNameIterator(false, false);
    }

    @FXML
    void addListActionListener(ActionEvent event) {
        undoButton.setDisable(false);
        facade.createPlaylist(facade.namePlaylistUnique("Playlist"));
        Tab tab = new Tab(facade.getLastPlaylist().getName());
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete");
        MenuItem copy = new MenuItem("Copy");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                facade.removePlaylist(tab.getText());
                playlistContainer.getTabs().remove(tab);
            }
        });

        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        contextMenu.getItems().add(delete);
        contextMenu.getItems().add(copy);
        tab.setContextMenu(contextMenu);
        playlistContainer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
                    TextField name = new TextField();
                    String nameOlder = playlistContainer.getSelectionModel().getSelectedItem().getText();
                    name.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event1) {
                            if (event1.getCode() == KeyCode.ENTER && !name.getText().isEmpty()) {
                                String nameCorrect = facade.namePlaylistUnique(name.getText());
                                playlistContainer.getTabs().get(playlistContainer.getSelectionModel().getSelectedIndex()).setText(nameCorrect);
                                facade.getPlaylist(nameOlder).setName(nameCorrect);
                                playlistContainer.getTabs().get(playlistContainer.getSelectionModel().getSelectedIndex()).setGraphic(null);
                            } else if (event1.getCode() == KeyCode.ENTER) {
                                playlistContainer.getTabs().get(playlistContainer.getSelectionModel().getSelectedIndex()).setText(nameOlder);
                                playlistContainer.getTabs().get(playlistContainer.getSelectionModel().getSelectedIndex()).setGraphic(null);
                            }
                        }
                    });
                    playlistContainer.getTabs().get(playlistContainer.getSelectionModel().getSelectedIndex()).setText("");
                    playlistContainer.getTabs().get(playlistContainer.getSelectionModel().getSelectedIndex()).setGraphic(name);
                } else {
                    for (int i = 0; i < playlistContainer.getTabs().size(); i++) {
                        if (playlistContainer.getTabs().get(i).getGraphic() != null) {
                            playlistContainer.getTabs().get(i).setGraphic(null);
                            playlistContainer.getTabs().get(i).setText(facade.getPlaylist(i).getName());
                        }
                    }
                }
            }
        });
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/ListMusic.fxml"));

        TableView tableTrack = null;

        try {
            tableTrack = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ListController listController = loader.getController();

        listController.setParent(this);
        listController.setFacade(facade);

        tab.setContent(tableTrack);
        playlistContainer.getTabs().add(tab);
    }

   @FXML
    void redoActionListener(ActionEvent event) {
        //TODO
        facade.redo();
    }

    @FXML
    void undoActionListener(ActionEvent event) {
        //TODO
        facade.undo();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changePlayButton(false);

        playButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changePlayButton(true);
            }
        });
        playButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changePlayButton(false);
            }
        });

        iconLoadingButton(nextButton, "icon_next");
        iconLoadingButton(previousButton, "icon_previous");
        iconLoadingToggleButton(loopmodeButton, "icon_loop");
        iconLoadingToggleButton(playmodeButton, "icon_random");

        volume.setValue(0.5 * 100);
        volume.valueProperty().addListener((Observable observable) -> {
            facade.setVolume(volume.getValue());
        });

        progressBar.setOnMouseReleased((MouseEvent event) -> {
            setCurrentTime();
        });

        refreshProgressBar();
    }

    public void refreshProgressBar() {
        Task t2 = new Task<Void>() {
            @Override
            public Void call() {
                Player player = Player.getInstance();
                while (true) {
                    if ((player.getMediaPlayer() != null)) {
                        Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    totalLength.setText(facade.timeConverter(player.getTrack().getLength().toMillis()));
                                }
                            }); 
                        //chyba isTrackAssigned bedzie mozna wywalic
                        if (progressBar.isValueChanging() == false && player.isPlaying() && player.getTrack() != null) {
                            double timeMilis = player.getMediaPlayer().getCurrentTime().toMillis();
                            double timePercent = (timeMilis / player.getMediaPlayer().getStopTime().toMillis() * 100);
                            progressBar.setValue(timePercent);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    currentTime.setText(facade.timeConverter(timeMilis));
                                }
                            }); 
                        }
                    }
                    //jak ani razu nie uruchomimy piosenki
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        System.out.println("refreshSeek error");
                    }
                }
            }
        };

        Thread xx = new Thread(t2);
        xx.setDaemon(true);
        xx.start();
    }

    public void setCurrentTime() {
        Player player = Player.getInstance();
        double newSongTime = ((progressBar.getValue() / 100) * player.getMediaPlayer().getStopTime().toMillis());
        player.getMediaPlayer().seek(Duration.millis(newSongTime));
    }

    public void changePlayButton(boolean isHover) {
        Image icon_play;
        if (isHover) {
            if (facade.isPlaying()) {
                icon_play = new Image("file:image/icon_stop_hover.png", 60, 60, true, true);
            } else {
                icon_play = new Image("file:image/icon_play_hover.png", 60, 60, true, true);
            }
        } else {
            if (facade.isPlaying()) {
                icon_play = new Image("file:image/icon_stop.png", 60, 60, true, true);
            } else {
                icon_play = new Image("file:image/icon_play.png", 60, 60, true, true);
            }
        }
        playButton.setGraphic(new ImageView(icon_play));
    }

    public void iconLoadingButton(Button button, String nameFile) {
        Image icon = new Image("file:image/" + nameFile + ".png", 40, 40, true, true);
        button.setGraphic(new ImageView(icon));

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Image icon = new Image("file:image/" + nameFile + "_hover.png", 40, 40, true, true);
                button.setGraphic(new ImageView(icon));
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Image icon = new Image("file:image/" + nameFile + ".png", 40, 40, true, true);
                button.setGraphic(new ImageView(icon));
            }
        });

    }

    public void iconLoadingToggleButton(ToggleButton button, String nameFile) {
        Image icon = new Image("file:image/" + nameFile + ".png", 30, 30, true, true);
        button.setGraphic(new ImageView(icon));

        button.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (button.isSelected()) {
                    Image icon = new Image("file:image/" + nameFile + "_hover_selected.png", 30, 30, true, true);
                    button.setGraphic(new ImageView(icon));
                } else {
                    Image icon = new Image("file:image/" + nameFile + "_hover.png", 30, 30, true, true);
                    button.setGraphic(new ImageView(icon));
                }
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (button.isSelected()) {
                    Image icon = new Image("file:image/" + nameFile + "_selected.png", 30, 30, true, true);
                    button.setGraphic(new ImageView(icon));
                } else {
                    Image icon = new Image("file:image/" + nameFile + ".png", 30, 30, true, true);
                    button.setGraphic(new ImageView(icon));
                }
            }
        });

    }

}
