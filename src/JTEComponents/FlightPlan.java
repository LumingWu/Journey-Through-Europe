package JTEComponents;

import JTEGraphic.JTEUI;
import JTEStart.Main;
import JTEStart.Main.JTEPropertyType;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import properties_manager.PropertiesManager;

public class FlightPlan extends Label {

    private ArrayList<Flight> _aircities;
    private JTEUI _ui;
    public FlightPlan(JTEUI ui) {
        _ui=ui;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        setGraphic(loadImage(props.getProperty(JTEPropertyType.DEFAULT_IMG_PATH),props.getProperty(JTEPropertyType.FLIGHT_PLAN_NAME)));
        _aircities=new ArrayList<Flight>();
        try {
            Scanner scanner=new Scanner(new File(System.getProperty("user.dir")+"/data/plan.txt"));
            while(scanner.hasNext()){
                _aircities.add(new Flight(scanner.next(),Integer.parseInt(scanner.next())
                        ,Integer.parseInt(scanner.next()),Integer.parseInt(scanner.next())));
            }
        } catch (FileNotFoundException ex) {}
        setOnMouseClicked((MouseEvent event) -> {
            for(int i=0;i<_aircities.size();i++){
                if(_aircities.get(i).IsItYou((int)event.getX(),(int)event.getY())){
                    Flight end=_aircities.get(i);
                    for(int j=0;j<_aircities.size();j++){
                        if(_ui.getGM().getPlayer().getCity().getName().equals(_aircities.get(j).getName())){
                            Flight start=_aircities.get(j);
                            ArrayList<String> air2=props.getPropertyOptionsList(start.getName()+"_AIR2");
                            ArrayList<String> air4=props.getPropertyOptionsList(start.getName()+"_AIR4");
                            if(air2.contains(end.getName())){
                                if(_ui.getGM().getPlayer().getStep()>=2){
                                    _ui.getIGH().respondFly(_ui.getGS().getCity(end.getName()),2);
                                }
                            }
                            if(air4.contains(end.getName())){
                                if(_ui.getGM().getPlayer().getStep()>=4){
                                    _ui.getIGH().respondFly(_ui.getGS().getCity(end.getName()),4);
                                }  
                            }
                        }
                    }
                }
            }
        });
    }

    public ImageView loadImage(String ImgPath, String imageName) {
        Image img = new Image(ImgPath + imageName);
        ImageView imgView = new ImageView(img);
        return imgView;
    }

    private class Flight {

        private String _name;
        private int _sector;
        private int _x;
        private int _y;

        public Flight(String name, int sector, int x, int y) {
            _name = name;
            _sector = sector;
            _x = x;
            _y = y;
        }

        public boolean IsItYou(int x, int y) {
            return (_x - x) * (_x - x) + (_y - y) * (_y - y) <= 144;
        }
        public String getName(){
            return _name;
        }
        public int getSector(){
            return _sector;
        }
        public int getX(){
            return _x;
        }
        public int getY(){
            return _y;
        }
        @Override
        public String toString(){
            return _name+","+_sector+","+_x+","+_y;
        }
    }
}
