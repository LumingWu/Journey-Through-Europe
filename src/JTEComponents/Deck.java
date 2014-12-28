package JTEComponents;

import java.util.ArrayList;
import java.util.Collections;
import properties_manager.PropertiesManager;

public class Deck {
    private ArrayList<Card> _greenDeck;
    private ArrayList<Card> _yellowDeck;
    private ArrayList<Card> _redDeck;
    public Deck(){
        _greenDeck=new ArrayList<Card>();
        _yellowDeck=new ArrayList<Card>();
        _redDeck=new ArrayList<Card>();
        
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        ArrayList green=props.getPropertyOptionsList("GREEN_CARD_NAME_OPTIONS");
        for(int i=0;i<green.size();i++){
            _greenDeck.add(new Card((String)green.get(i),"GREEN"));
        }
        ArrayList yellow=props.getPropertyOptionsList("YELLOW_CARD_NAME_OPTIONS");
        for(int i=0;i<yellow.size();i++){
            _yellowDeck.add(new Card((String)yellow.get(i),"YELLOW"));
        }
        ArrayList red=props.getPropertyOptionsList("RED_CARD_NAME_OPTIONS");
        for(int i=0;i<red.size();i++){
            _redDeck.add(new Card((String)red.get(i),"RED"));
        }
        
    }
    public Card drawGreen(){
        Collections.shuffle(_greenDeck);
        return _greenDeck.remove(0);
    }
    public Card drawYellow(){
        Collections.shuffle(_yellowDeck);
        if(_yellowDeck.get(0).getName().equals("TIRANE")){
            return _yellowDeck.remove(1);
        }
        return _yellowDeck.remove(0);
    }
    public Card drawRed(){
        Collections.shuffle(_redDeck);
        return _redDeck.remove(0);
    }
}
