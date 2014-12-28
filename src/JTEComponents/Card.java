package JTEComponents;

import JTEStart.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import properties_manager.PropertiesManager;

public class Card {
    private String _name;
    private String _color;
    private ImageView _image;
    private int _counter;
    public Card(String name,String color){
        _name=name;
        _color=color;
        _counter=0;
    }
    public String getName(){
        return _name;
    }
    public String getColor(){
        return _color;
    }
    public ImageView getImage(){
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        Image img=new Image(props.getProperty(_color+"_CARD_PATH")+_name+".jpg");
        ImageView image=new ImageView(img);
        image.setFitHeight(Integer.parseInt(props.getProperty(Main.JTEPropertyType.CARD_HEIGHT)));
        image.setFitWidth(Integer.parseInt(props.getProperty(Main.JTEPropertyType.CARD_WIDTH)));
        return image;
    }
    public ImageView getBackImage(){
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        Image img=new Image(props.getProperty(_color+"_CARD_PATH")+_name+"_I.jpg");
        if(!img.isError()){
        ImageView image=new ImageView(img);
        image.setFitHeight(Integer.parseInt(props.getProperty(Main.JTEPropertyType.CARD_HEIGHT)));
        image.setFitWidth(Integer.parseInt(props.getProperty(Main.JTEPropertyType.CARD_WIDTH)));
        return image;
        }
        return getImage();
    }
    public ImageView changeImage(){
        switch(_counter){
            case 0:
                _counter=1;
                return getBackImage();
            case 1:
                _counter=0;
                return getImage();
        }
        return null;
    }
}
