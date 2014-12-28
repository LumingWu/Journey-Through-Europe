package JTEManager;

import JTEComponents.City;
import JTEComponents.Player;
import JTEGraphic.JTEUI;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import properties_manager.PropertiesManager;

public class GameAI {

    private JTEUI _ui;
    private int _delay;
    private int _steps;
    private long _current;
    private AnimationTimer _timer;

    public GameAI(JTEUI ui) {
        _ui = ui;
        _timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (_current == 0) {
                    _current = now;
                }

                if ((now - _current) / 1000000000 == 5 * _delay && _delay <= _steps) {
                    _delay = _delay + 1;
                    operation();
                }
                if (_delay > _steps) {
                    _timer.stop();
                }
            }
        };
    }

    public void start() {
        _steps = _ui.getGM().getPlayer().getStep();
        _current = 0;
        _delay = 1;
        _timer.start();
    }

    public void operation() {
        Player currentPlayer = _ui.getGM().getPlayer();
        String start = currentPlayer.getCity().getName();
        String cityname = currentPlayer.getNextCityName();
        ArrayList<City> cities = _ui.getGS().getAllCities();
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equals(cityname)) {
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                if (props.getPropertyOptionsList(start + "_SEA").contains(cityname)) {
                    _steps = 0;
                }
                _ui.getIGH().respondCitySelection(cities.get(i));
            }
        }

    }
}
