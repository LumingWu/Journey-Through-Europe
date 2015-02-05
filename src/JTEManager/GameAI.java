package JTEManager;

import JTEComponents.City;
import JTEComponents.Player;
import JTEGraphic.JTEUI;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;

public class GameAI {

    private JTEUI _ui;
    private AnimationTimer _delay;
    private long _current;
    public GameAI(JTEUI ui) {
        _ui = ui;
        _current=0;
        _delay=new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(_current==0){
                    _current=now;
                }
                if((now-_current)/1000000000==5){
                    operation();
                    _current=0;
                    _delay.stop();
                }
            }
        };
    }

    public void start() {
        _delay.start();
    }
    public void operation(){
        Player currentPlayer = _ui.getGM().getPlayer();
        String start = currentPlayer.getCity().getName();
        String end = null;
        if (currentPlayer.getHand().size() == 1) {
            end = currentPlayer.getHand().get(0).getName();
        } else {
            try{
                end = currentPlayer.getHand().get(1).getName();
            }
            catch(IndexOutOfBoundsException e){/*System.out.println("Game Over");*/}
        }
        String cityname=currentPlayer.getNextCityName();
        ArrayList<City> cities=_ui.getGS().getAllCities();
        for(int i=0;i<cities.size();i++){
            if(cities.get(i).getName().equals(cityname)){
                _ui.getIGH().respondCitySelection(cities.get(i));
            }
        }
        if(_ui.getGM().getPlayer().getStep()>0&&currentPlayer.equals(_ui.getGM().getPlayer())
                &&_ui.getGM().getPlayer().getType().equals("Computer")
                &&_ui.getWinnerBoard().getText().length()==0){
            _delay=new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(_current==0){
                    _current=now;
                }
                if((now-_current)/1000000000==5){
                    operation();
                    _current=0;
                    _delay.stop();
                }
            }
        };
            _delay.start();
        }
    }
}
