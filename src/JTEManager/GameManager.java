package JTEManager;

import JTEComponents.City;
import JTEComponents.Deck;
import JTEComponents.Dice;
import JTEComponents.Player;
import JTEGraphic.JTEUI;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameManager {

    private JTEUI _ui;

    private Deck _deck;
    private Dice _dice;
    private ArrayList<Player> _players;

    private int _playerTurn;
    private boolean _gameStarted;
    

    private GameLoader _gl;
    private ArrayList<MediaPlayer> _musics;
    public GameManager(JTEUI ui) {
        _ui = ui;
        _gl = new GameLoader(this);
        initialBasicData();
    }

    public void initialBasicData() {
        _deck = new Deck();
        _dice = new Dice(_ui);
        _playerTurn = 0;
        _players = new ArrayList<Player>();
        _gameStarted = false;
        _musics = new ArrayList<MediaPlayer>();
        _musics.add(new MediaPlayer(new Media(getClass().getClassLoader()
                .getResource("Cat.mp3").toString())));
        _musics.add(new MediaPlayer(new Media(getClass().getClassLoader()
                .getResource("Key.mp3").toString())));
        _musics.add(new MediaPlayer(new Media(getClass().getClassLoader()
                .getResource("Living Mice.mp3").toString())));
        for (int i = 0; i < _musics.size(); i++) {
            _musics.get(i).setVolume(0.25);
            _musics.get(i).setOnEndOfMedia(() -> {
                for(int j=0;j<_musics.size();j++){
                    _musics.get(j).stop();
                }
                Collections.shuffle(_musics);
                _musics.get(0).play();
            });
        }
    }

    public void loadGame() {
        _gl.read();
        _gameStarted = true;
        Collections.shuffle(_musics);
        _musics.get(0).play();
    }

    public void startNewGame() {
        _dice.Roll();
        _players.get(0).setStep(_dice.getRoll());
        ArrayList<ArrayList<City>> quarters = _ui.getGS().getCities();
        for (int i = 0; i < _players.size(); i++) {
            switch (i) {
                case 0: {
                    _players.get(i).getHand().add(_deck.drawRed());
                    for (int j = 0; j < quarters.size(); j++) {
                        for (int k = 0; k < quarters.get(j).size(); k++) {
                            if (_players.get(i).getHand().get(0).getName().equals(quarters.get(j).get(k).getName())) {
                                _players.get(i).setHomeCity(quarters.get(j).get(k).getCopy());
                                _players.get(i).setCity(quarters.get(j).get(k).getCopy());
                            }
                        }
                    }
                    _players.get(i).getHand().add(_deck.drawGreen());
                    _players.get(i).getHand().add(_deck.drawYellow());
                    break;
                }
                case 1: {
                    _players.get(i).getHand().add(_deck.drawGreen());
                    for (int j = 0; j < quarters.size(); j++) {
                        for (int k = 0; k < quarters.get(j).size(); k++) {
                            if (_players.get(i).getHand().get(0).getName().equals(quarters.get(j).get(k).getName())) {
                                _players.get(i).setHomeCity(quarters.get(j).get(k).getCopy());
                                _players.get(i).setCity(quarters.get(j).get(k).getCopy());
                            }
                        }
                    }
                    _players.get(i).getHand().add(_deck.drawYellow());
                    _players.get(i).getHand().add(_deck.drawRed());
                    break;
                }
                case 2: {
                    _players.get(i).getHand().add(_deck.drawYellow());
                    for (int j = 0; j < quarters.size(); j++) {
                        for (int k = 0; k < quarters.get(j).size(); k++) {
                            if (_players.get(i).getHand().get(0).getName().equals(quarters.get(j).get(k).getName())) {
                                _players.get(i).setHomeCity(quarters.get(j).get(k).getCopy());
                                _players.get(i).setCity(quarters.get(j).get(k).getCopy());
                            }
                        }
                    }
                    _players.get(i).getHand().add(_deck.drawRed());
                    _players.get(i).getHand().add(_deck.drawGreen());
                    break;
                }
                case 3: {
                    _players.get(i).getHand().add(_deck.drawRed());
                    for (int j = 0; j < quarters.size(); j++) {
                        for (int k = 0; k < quarters.get(j).size(); k++) {
                            if (_players.get(i).getHand().get(0).getName().equals(quarters.get(j).get(k).getName())) {
                                _players.get(i).setHomeCity(quarters.get(j).get(k).getCopy());
                                _players.get(i).setCity(quarters.get(j).get(k).getCopy());
                            }
                        }
                    }
                    _players.get(i).getHand().add(_deck.drawGreen());
                    _players.get(i).getHand().add(_deck.drawYellow());
                    break;
                }
                case 4: {
                    _players.get(i).getHand().add(_deck.drawGreen());
                    for (int j = 0; j < quarters.size(); j++) {
                        for (int k = 0; k < quarters.get(j).size(); k++) {
                            if (_players.get(i).getHand().get(0).getName().equals(quarters.get(j).get(k).getName())) {
                                _players.get(i).setHomeCity(quarters.get(j).get(k).getCopy());
                                _players.get(i).setCity(quarters.get(j).get(k).getCopy());
                            }
                        }
                    }
                    _players.get(i).getHand().add(_deck.drawYellow());
                    _players.get(i).getHand().add(_deck.drawRed());
                    break;
                }
                case 5: {
                    _players.get(i).getHand().add(_deck.drawYellow());
                    for (int j = 0; j < quarters.size(); j++) {
                        for (int k = 0; k < quarters.get(j).size(); k++) {
                            if (_players.get(i).getHand().get(0).getName().equals(quarters.get(j).get(k).getName())) {
                                _players.get(i).setHomeCity(quarters.get(j).get(k).getCopy());
                                _players.get(i).setCity(quarters.get(j).get(k).getCopy());
                            }
                        }
                    }
                    _players.get(i).getHand().add(_deck.drawRed());
                    _players.get(i).getHand().add(_deck.drawGreen());
                    break;
                }
            }
        }
        _gameStarted = true;
        Collections.shuffle(_musics);
        _musics.get(0).play();
    }

    public void nextTurn() {
        _dice.Roll();
        if (_playerTurn == _players.size() - 1) {
            _playerTurn = 0;
        } else {
            _playerTurn = _playerTurn + 1;
        }
        _players.get(_playerTurn).setStep(_dice.getRoll());
        _ui.getOGH().refreshGameScreen();
        if (!getPlayer().getHand().isEmpty()) {
            _ui.getOGH().playAnimation();
        }
        if (_players.get(_playerTurn).getType().equals("Computer")) {
            _ui.getAI().start();
        }
    }

    public void changePlayerNumber(int number) {
        if (number != _players.size()) {
            if (number > _players.size()) {
                for (int i = _players.size(); i < number; i++) {
                    _players.add(new Player("", "Player", i));
                }
            } else {
                for (int i = _players.size() - 1; i > number - 1; i--) {
                    _players.remove(i);
                }
            }
        }
    }

    public ArrayList<Player> getPlayers() {
        return _players;
    }

    public void setPlayers(ArrayList<Player> players) {
        _players = players;
    }

    public Player getPlayer() {
        return _players.get(_playerTurn);
    }

    public Dice getDice() {
        return _dice;
    }

    public boolean gameStarted() {
        return _gameStarted;
    }

    public void setTurn(int turn) {
        _playerTurn = turn;
    }

    public int getTurn() {
        return _playerTurn;
    }
}
