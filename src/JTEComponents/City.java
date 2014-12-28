package JTEComponents;

import JTEStart.Main;
import JTEStart.Main.JTEPropertyType;
import properties_manager.PropertiesManager;

public class City {
    private String _name;
    private String _color;
    private int _x;
    private int _y;
    private int _radius;
    private int _quarter;
    public City(String name,String color,int quarter,int x,int y){
        _name=name;
        _color=color;
        _x=x;
        _y=y;
        _quarter=quarter;
        
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        _radius=Integer.parseInt(props.getProperty(JTEPropertyType.BLACK_DOT_RADIUS));
    }
    public String getName(){
        return _name;
    }
    public boolean isItYou(int x,int y){
        if((_x-x)*(_x-x)+(_y-y)*(_y-y)<=_radius*_radius){
            return true;
        }
        return false;
    }
    @Override
    public String toString(){
        return _name+","+_x+","+_y;
    }
    public int getQuarter(){
        return _quarter;
    }
    public int getX(){
        return _x;
    }
    public int getY(){
        return _y;
    }
    public String getColor(){
        return _color;
    }
    public City getCopy(){
        return new City(_name,_color,_quarter,_x,_y);
    }
}
