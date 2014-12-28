package JTEComponents;

import JTEManager.Dijkstra;
import JTEStart.Main.JTEPropertyType;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import properties_manager.PropertiesManager;

public class Player {
    private String _name;
    private String _type;
    private String _color;
    private int _step;
    private int _colornumber;
    private City _city;
    private City _homecity;
    private ArrayList<Card> _hand;
    private ArrayList<String> _cityHistory;
    private Dijkstra _dj1;
    private Dijkstra _dj2;
    private Dijkstra _dj3;
    private ArrayList<String> _route;
    private boolean notcalculated;
    private int _counter;
    public Player(String name,String type,int color){
        _name=name;
        _type=type;
        _hand=new ArrayList<Card>();
        switch(color){
            case 0:_color="black";
                break;
            case 1:_color="blue";
                break;
            case 2:_color="green";
                break;
            case 3:_color="red";
                break;
            case 4:_color="white";
                break;
            case 5:_color="yellow";
                break;
        }
        _colornumber=color;
        _step=0;
        _cityHistory=new ArrayList<String>();
        _dj1=new Dijkstra();
        _dj2=new Dijkstra();
        _dj3=new Dijkstra();
        notcalculated=true;
        _route=new ArrayList<String>();
        _counter=-1;
    }
    public String getNextCityName(){
        try{
        if(notcalculated){
            ArrayList<String> list1=_dj1.getNextCityName(_hand.get(0).getName(),_hand.get(1).getName());
            for(int i=0;i<list1.size();i++){
                _route.add(list1.get(i));
            }
            ArrayList<String> list2=_dj2.getNextCityName(_hand.get(1).getName(),_hand.get(2).getName());
            for(int i=0;i<list2.size();i++){
                _route.add(list2.get(i));
            }
            ArrayList<String> list3=_dj3.getNextCityName(_hand.get(2).getName(),_hand.get(0).getName());
            for(int i=0;i<list3.size();i++){
                _route.add(list3.get(i));
            }
            //System.out.println(_route);
            notcalculated=false;
        }
        _counter=_counter+1;
        return _route.get(_counter);}catch(IndexOutOfBoundsException e){/*System.out.println("Random calculate again...");*/}
        return null;
    }
    
    public String getName(){
        return _name;
    }
    
    public void setName(String name){
        _name=name;
    }
    
    public String getType(){
        return _type;
    }
    
    public void setType(String type){
        _type=type;
    }
    
    public void setCity(City city){
        _cityHistory.add(city.getName());
        _city=city;
        
    }
    
    public City getCity(){
        return _city;
    }
    public ArrayList<String> getLandNeighbors(){
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        return props.getPropertyOptionsList(_city.getName()+"_LAND");
    }
    public ArrayList<String> getSeaNeighbors(){
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        return props.getPropertyOptionsList(_city.getName()+"_SEA");
    }
    public void setHomeCity(City home){
        _homecity=home;
    }
    
    public City getHomeCity(){
        return _homecity;
    }
    
    public Card useCard(String name,int color){
        return _hand.remove(0);
    }
    
    public ArrayList<Card> getHand(){
        return _hand;
    }
    
    public String getColor(){
        return _color;
    }
    
    public int getColorNumber(){
        return _colornumber;
    }
    public int getHomeQuarter(){
        return _homecity.getQuarter();
    }
    public ImageView loadImage(String ImgPath,String imageName) {
        Image img = new Image(ImgPath + imageName);
        ImageView imgView=new ImageView(img);
        return imgView;
    }
    public void setStep(int step){
        _step=step;
    }
    public int getStep(){
        return _step;
    }
    public ArrayList<String> getHistory(){
        return _cityHistory;
    }
    
    public ImageView getPiece(){
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        String path=props.getProperty(JTEPropertyType.PIECE_PATH);
        ArrayList<String> pieces=props.getPropertyOptionsList(JTEPropertyType.PIECE_NAME_OPTIONS);
        ImageView image=new ImageView(new Image(path+pieces.get(_colornumber)));
        return image;
    }
}
