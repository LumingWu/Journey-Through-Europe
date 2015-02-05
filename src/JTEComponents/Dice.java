
package JTEComponents;

import JTEGraphic.JTEUI;
import java.util.ArrayList;

public class Dice {
    private JTEUI _ui;
    private ArrayList<Integer> _history;
    public Dice(JTEUI ui){
        _ui=ui;
        _history=new ArrayList();
    }
    public void Roll(){
        int result=(int)(Math.random()*6)+1;
        if(result==6){
            _ui.getIGH().changeMoreTurn(true);
        }
        _history.add(result);
    }
    public ArrayList<Integer> getHistory(){
        return _history;
    }
    public int getRoll(){
        return _history.get(_history.size()-1);
    }
    public void setHistory(ArrayList<Integer> history){
        _history=history;
    }
}
