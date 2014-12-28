package JTEHandler;

import JTEComponents.City;
import JTEComponents.Player;
import JTEGraphic.JTEUI;
import JTEStart.Main.JTEPropertyType;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import properties_manager.PropertiesManager;

public class InGameHandler {

    private JTEUI _ui;
    private int state;
    private ArrayList<AudioClip> _musics;
    private long _current;
    private AnimationTimer _timer;
    public InGameHandler(JTEUI ui) {
        _ui = ui;
        _musics = new ArrayList<AudioClip>();
        _musics.add(new AudioClip(getClass().getClassLoader()
                .getResource("land.mp3").toString()));
        _musics.add(new AudioClip(getClass().getClassLoader()
                .getResource("sail.mp3").toString()));
        _musics.get(1).setVolume(0.5);
        _musics.add(new AudioClip(getClass().getClassLoader()
                .getResource("fly.mp3").toString()));
        _musics.add(new AudioClip(getClass().getClassLoader().getResource("cardreach.mp3").toString()));
        _musics.add(new AudioClip(getClass().getClassLoader().getResource("win.wav").toString()));
        _timer=new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(_current==0){
                    _current=now;
                }
                
                if((now-_current)/1000000000==1){
                    _current=0;
                    _ui.getGM().nextTurn();
                    _timer.stop();
                }
            }
        };
    }
    public AnimationTimer getTimer(){
        return _timer;
    }
    public void respondSelectSector(String sector) {
        switch (sector) {
            case "AC14":
                _ui.getGD().drawAC14();
                _ui.getGS().setCurrentQuarter(1);
                _ui.getGD().drawPlayer(1);
                break;
            case "AC58":
                _ui.getGD().drawAC58();
                _ui.getGS().setCurrentQuarter(3);
                _ui.getGD().drawPlayer(3);
                break;
            case "DF14":
                _ui.getGD().drawDF14();
                _ui.getGS().setCurrentQuarter(2);
                _ui.getGD().drawPlayer(2);
                break;
            case "DF58":
                _ui.getGD().drawDF58();
                _ui.getGS().setCurrentQuarter(4);
                _ui.getGD().drawPlayer(4);
                break;
        }
    }

    public void respondCitySelection(City city) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Player currentPlayer = _ui.getGM().getPlayer();
        City currentCity = currentPlayer.getCity().getCopy();
        ArrayList land = currentPlayer.getLandNeighbors();
        ArrayList sea = currentPlayer.getSeaNeighbors();
        if (currentPlayer.getStep() > 0) {
            for (int i = 0; i < land.size(); i++) {
                if (land.get(i).equals(city.getName())) {
                    _musics.get(0).play();
                    currentPlayer.setCity(city);
                    currentPlayer.setStep(currentPlayer.getStep() - 1);
                    checkCard(city);
                    _ui.getGD().drawQuarter(currentPlayer.getCity().getQuarter());
                    if (currentCity.getQuarter() == city.getQuarter()) {
                        playPieceAnimation(currentCity.getX(), currentCity.getY(), city.getX(), city.getY());
                    } else {_ui.getGD().drawPlayer(_ui.getGM().getPlayer().getCity().getQuarter());
                        if (_ui.getGM().getPlayer().getStep() == 0) {
                            _current=0;
                            _timer.start();
                        }
                    }
                }
            }
            for (int i = 0; i < sea.size(); i++) {
                if (sea.get(i).equals(city.getName())) {
                    _musics.get(1).play();
                    currentPlayer.setCity(city);
                    currentPlayer.setStep(0);
                    checkCard(city);
                    _ui.getGD().drawQuarter(currentPlayer.getCity().getQuarter());
                    if (currentCity.getQuarter() == city.getQuarter()) {
                        playPieceAnimation(currentCity.getX(), currentCity.getY(), city.getX(), city.getY());
                    } else {_ui.getGD().drawPlayer(_ui.getGM().getPlayer().getCity().getQuarter());
                        if (_ui.getGM().getPlayer().getStep() == 0) {
                            _current=0;
                            _timer.start();
                        }
                    }
                }
            }
            if(currentCity.getName().equals(city.getName())){
                if(!props.getPropertyOptionsList(city.getName()+"_SEA").isEmpty()){
                    _musics.get(0).play();
                    currentPlayer.setCity(city);
                    currentPlayer.setStep(0);
                    if (_ui.getGM().getPlayer().getStep() == 0) {
                        _current=0;
                        _timer.start();
                        }
                }
            }
            updateInfo(city);
        }
    }

    public void respondFly(City city, int cost) {
        Player currentPlayer = _ui.getGM().getPlayer();
        _musics.get(2).play();
        currentPlayer.setCity(city);
        currentPlayer.setStep(currentPlayer.getStep() - cost);
        checkCard(city);
        if (_ui.getGM().getPlayer().getStep() == 0) {
            _current=0;
            _timer.start();
        }
        updateInfo(city);
    }

    public void playPieceAnimation(int x1, int y1, int x2, int y2) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        int width = Integer.parseInt(props.getProperty(JTEPropertyType.BOARD_WIDTH));
        int height = Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_HEIGHT));

        PathTransition pt = new PathTransition();
        Path path = new Path();
        MoveTo move = new MoveTo(x1 - width / 2 - 25, y1 - height / 2 + 25);
        LineTo line = new LineTo(x2 - width / 2 - 25, y2 - height / 2 + 25);
        path.getElements().add(move);
        path.getElements().add(line);

        pt.setPath(path);
        pt.setDuration(Duration.millis(1000));
        pt.setCycleCount(1);

        Label piece = new Label();
        piece.setGraphic(_ui.getGM().getPlayer().getPiece());
        ((StackPane) _ui.getStage().getScene().getRoot()).getChildren().add(piece);
        pt.setNode(piece);
        pt.setOnFinished((ActionEvent event) -> {
            ((StackPane) _ui.getStage().getScene().getRoot()).getChildren().remove(1);

            if (_ui.getGM().getPlayer().getStep() == 0) {
                _current=0;
                _timer.start();
            }
            if(_ui.getGM().getPlayer().getType().equals("Computer")){
                _ui.getGD().drawQuarter(_ui.getGM().getPlayer().getCity().getQuarter());
                _ui.getGD().drawPlayer(_ui.getGM().getPlayer().getCity().getQuarter());
            }
            else{
                _ui.getGD().drawQuarter(_ui.getGS().getCurrentQuarter());
                _ui.getGD().drawPlayer(_ui.getGS().getCurrentQuarter());
            }
        });
        pt.play();
    }

    public void checkCard(City city) {
        state = 0;
        Player currentPlayer = _ui.getGM().getPlayer();
        if (currentPlayer.getHand().size() == 1) {
            if (currentPlayer.getHand().get(0).getName().equals(city.getName())) {
                currentPlayer.setStep(0);
                currentPlayer.getHand().remove(0);
                state = 1;
            }
        } else {
            for (int j = 1; j < currentPlayer.getHand().size(); j++) {
                if (currentPlayer.getHand().get(j).getName().equals(city.getName())) {
                    currentPlayer.setStep(0);
                    currentPlayer.getHand().remove(j);
                    state = 1;
                }
            }
        }
        if (currentPlayer.getHand().isEmpty()) {
            state = 2;
        }
    }

    public void updateInfo(City city) {
        Player currentPlayer = _ui.getGM().getPlayer();
        switch (state) {
            case 0: {
                ArrayList list = _ui.getEastElements();
                ((Label) list.get(2)).setText(city.getName() + " " + (currentPlayer.getStep()) + "Steps");
                break;
            }
            case 1: {
                ArrayList list = _ui.getEastElements();
                ((Label) list.get(2)).setText("Card Reached:" + "\n" + city.getName());
                _musics.get(3).play();
                break;
            }
            case 2: {
                ArrayList list = _ui.getEastElements();
                Label winboard = _ui.getWinnerBoard();
                if (_ui.getGM().getPlayers().size() > 1) {
                    winboard.setText(_ui.getGM().getPlayers()
                            .get(_ui.getGM().getPlayers().indexOf(currentPlayer)).getName() + " Won");
                    winboard.setStyle("-fx-text-fill:"+_ui.getGM().getPlayers()
                            .get(_ui.getGM().getPlayers().indexOf(currentPlayer)).getColor());
                } else {
                    winboard.setText(_ui.getGM().getPlayers()
                            .get(0).getName() + " Won");
                    winboard.setStyle("-fx-text-fill:"+_ui.getGM().getPlayers()
                            .get(0).getColor());
                }
                _ui.getOGH().respondSwitchScreen(JTEUI.JTEUIState.VIEW_WIN_STATE);
                _musics.get(4).play();
                break;
            }
        }
    }
}
