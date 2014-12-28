package JTEHandler;

import JTEComponents.Card;
import JTEComponents.Player;
import JTEGraphic.JTEUI;
import JTEGraphic.JTEUI.JTEUIState;
import JTEManager.GameManager;
import JTEStart.Main.JTEPropertyType;
import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import properties_manager.PropertiesManager;

public class OutGameHandler {

    private JTEUI _ui;
    private GameManager _gm;
    private String _defaultImagePath;
    private String _pieceImagePath;
    private String _diceImagePath;

    private int _cn;
    private int _mutiplier;
    private AudioClip _cardmusic;

    public OutGameHandler(JTEUI ui) {
        _ui = ui;
        _gm = _ui.getGM();
        _cardmusic = new AudioClip(getClass().getClassLoader()
                .getResource("card.wav").toString());

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        _defaultImagePath = props.getProperty(JTEPropertyType.DEFAULT_IMG_PATH);
        _pieceImagePath = props.getProperty(JTEPropertyType.PIECE_PATH);
        _diceImagePath = props.getProperty(JTEPropertyType.DICE_PATH);
    }

    public void respondSwitchScreen(JTEUI.JTEUIState uiState) {
        switch (uiState) {
            case VIEW_ABOUT_STATE:
                _ui.changeScreen(JTEUIState.VIEW_ABOUT_STATE);
                break;
            case SPLASH_SCREEN_STATE:
                if (_gm.gameStarted()) {
                    _ui.changeScreen(JTEUIState.VIEW_GAME_STATE);
                } else {
                    _ui.changeScreen(JTEUIState.SPLASH_SCREEN_STATE);
                }
                break;
            case VIEW_SELECTION_STATE:
                _ui.changeScreen(JTEUIState.VIEW_SELECTION_STATE);
                break;
            case VIEW_HISTORY_STATE:

                _ui.changeScreen(JTEUIState.VIEW_HISTORY_STATE);
                break;
            case VIEW_GAME_STATE:
                _ui.changeScreen(JTEUIState.VIEW_GAME_STATE);
                switch (_ui.getGM().getPlayer().getCity().getQuarter()) {
                    case 1:
                        _ui.getIGH().respondSelectSector("AC14");
                        break;
                    case 3:
                        _ui.getIGH().respondSelectSector("AC58");
                        break;
                    case 2:
                        _ui.getIGH().respondSelectSector("DF14");
                        break;
                    case 4:
                        _ui.getIGH().respondSelectSector("DF58");
                        break;
                }
                break;
            case VIEW_FLIGHT_PLAN_STATE:
                _ui.changeScreen(JTEUIState.VIEW_FLIGHT_PLAN_STATE);
                break;
            case VIEW_WIN_STATE:
                _ui.changeScreen(JTEUIState.VIEW_WIN_STATE);
        }
    }

    public void respondNewGame() {
        if (_gm.getPlayers().size() > 0) {
            getPlayerNames();
            _gm.startNewGame();
            refreshGameScreen();
            _ui.changeScreen(JTEUIState.VIEW_GAME_STATE);
            playAnimation();
            if (_gm.getPlayer().getType().equals("Computer")) {
                _ui.getAI().start();
            }
        }
    }

    public void respondLoadGame() {
        refreshGameScreen();
        _ui.changeScreen(JTEUIState.VIEW_GAME_STATE);
        playAnimation();
    }

    public void refreshGameScreen() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        ArrayList list = _ui.getWestElements();
        Player _player = _gm.getPlayer();
        ((Label) list.get(0)).setText("Player: " + _player.getName());
        ((Label) list.get(0)).setTextFill(Paint.valueOf(_player.getColor()));
        for (int i = 1; i < _player.getHand().size() + 1; i++) {
            ((Label) list.get(i)).setGraphic(((Card) (_player.getHand().get(i - 1))).getImage());
            //System.out.println(((Card) (_player.getHand().get(i - 1))).getName());
        }

        ArrayList list2 = _ui.getEastElements();
        ((Label) list2.get(0)).setText(_player.getName() + "'s Turn");
        ((Label) list2.get(0)).setTextFill(Paint.valueOf(_player.getColor()));
        ((Label) list2.get(0)).setGraphic(loadImage(_pieceImagePath,
                props.getPropertyOptionsList(JTEPropertyType.PIECE_NAME_OPTIONS).get(_player.getColorNumber())));
        ((Label) list2.get(1)).setText("Rolled " + _gm.getDice().getRoll());
        ((Label) list2.get(1)).setGraphic(loadImage(_diceImagePath,
                props.getPropertyOptionsList(JTEPropertyType.DICE_NAME_OPTIONS).get(_gm.getDice().getRoll() - 1)));
        ArrayList list3 = _ui.getEastElements();
        ((Label) list3.get(2)).setText("Select City");
    }

    public void playAnimation() {
        ArrayList list = _ui.getWestElements();
        for (int i = 1; i < list.size(); i++) {
            ((Label) list.get(i)).setVisible(false);
        }
        Player _player = _gm.getPlayer();

        PathTransition _pt;
        Path _path;
        LineTo _lt;
        _pt = new PathTransition();
        _path = new Path();
        _lt = new LineTo(-440, 0);
        _path.getElements().add(new MoveTo(100f, 125f));
        _path.getElements().add(_lt);
        _pt.setDuration(Duration.millis(1000));
        _pt.setPath(_path);
        _pt.setCycleCount(1);

        _cn = 0;
        _mutiplier = 0;
        Label card = new Label();
        card.setGraphic(((Card) (_player.getHand().get(_cn))).getImage());
        ((StackPane) _ui.getStage().getScene().getRoot()).getChildren().add(card);

        _pt.setNode(new Label());
        _pt.setOnFinished((ActionEvent event) -> {
            ((StackPane) _ui.getStage().getScene().getRoot()).getChildren().remove(1);
            if (_cn < list.size()) {
                ((Label) list.get(_cn)).setVisible(true);
            }

            if (_cn < _player.getHand().size()) {
                Label card1 = new Label();
                card1.setGraphic(((Card) (_player.getHand().get(_cn))).getImage());
                ((StackPane) _ui.getStage().getScene().getRoot()).getChildren().add(card1);
                if (_cn == 0) {
                    _cn = -1;
                    _mutiplier = 100;
                }
                _lt.setY((_cn) * _mutiplier - 10);
                if (_mutiplier == 125) {
                    _mutiplier = 175;
                }
                if (_mutiplier == 100) {
                    _mutiplier = 125;
                }
                if (_cn == -1) {
                    _cn = 0;
                }
                _cardmusic.play();
                _pt.setNode(card1);
                _pt.play();
            }
            _cn = _cn + 1;
        });
        _pt.play();
        _pt.setDuration(Duration.millis(1000));
    }

    public void respondFlipCard(Label card) {
        ArrayList list = _ui.getWestElements();
        Player currentPlayer = _gm.getPlayer();
        card.setGraphic(currentPlayer.getHand().get(list.indexOf(card) - 1).changeImage());
    }

    public void respondExitGame(Stage primarystage) {
        primarystage.close();
    }

    public void respondChangePlayerNumber(int number) {
        _gm.changePlayerNumber(number);
        GridPane _grid = _ui.getGridPane();
        for (int i = 0; i < number; i++) {
            _grid.getChildren().get(i).setVisible(true);
        }
        for (int i = 5; i > number - 1; i--) {
            _grid.getChildren().get(i).setVisible(false);
        }
    }

    public void respondChangePlayerType(int index, String type) {
        _gm.getPlayers().get(index).setType(type);
    }

    public void getPlayerNames() {
        for (int i = 0; i < _gm.getPlayers().size(); i++) {
            if (_ui.getTextField(i).getText().length() > 8) {
                _gm.getPlayers().get(i).setName(_ui.getTextField(i).getText(0, 7));
            } else {
                _gm.getPlayers().get(i).setName(_ui.getTextField(i).getText());
            }
        }
    }

    public ImageView loadImage(String ImgPath, String imageName) {
        Image img = new Image(ImgPath + imageName);
        ImageView imgView = new ImageView(img);
        return imgView;
    }

}
