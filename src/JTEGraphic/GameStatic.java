package JTEGraphic;

import JTEComponents.City;
import JTEStart.Main.JTEPropertyType;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import properties_manager.PropertiesManager;

public class GameStatic extends Label {

    private int _width;
    private int _height;
    private String _dataPath;
    private int _currentQuarter;
    private JTEUI _ui;
    private ArrayList<ArrayList<City>> _quarters;
    private ArrayList<City> _allCities;
    private Scanner scanner;
    private boolean _ishomecity;
    private int _ox;
    private int _oy;

    public GameStatic(JTEUI ui) {
        _ui = ui;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        _width = Integer.parseInt(props.getProperty(JTEPropertyType.BOARD_WIDTH));
        _height = Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_HEIGHT));
        _dataPath = props.getProperty(JTEPropertyType.DATA_PATH);
        setPrefSize(_width, _height);

        _quarters = new ArrayList<ArrayList<City>>();
        loadQuarter(1);
        loadQuarter(2);
        loadQuarter(3);
        loadQuarter(4);
        _allCities=new ArrayList<City>();
        for(int i=0;i<_quarters.size();i++){
            for(int j=0;j<_quarters.get(i).size();j++){
                _allCities.add(_quarters.get(i).get(j));
            }
        }
        setCurrentQuarter(1);
        //setActionListener();
        setDragListener();
    }

    private void loadQuarter(int x) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<City> quarter = new ArrayList<City>();
        try {
            scanner = new Scanner(
                    new File(System.getProperty("user.dir")
                            + _dataPath + props.getPropertyOptionsList(JTEPropertyType.QUARTER_FILE_NAMES).get(x - 1)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameStatic.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (scanner.hasNext()) {
            quarter.add(new City(scanner.nextLine(), scanner.nextLine(), x,
                    Integer.parseInt(scanner.nextLine()), Integer.parseInt(scanner.nextLine())));
        }
        _quarters.add(quarter);
    }

    private void setActionListener() {
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ArrayList list = _ui.getEastElements();
                ((Label) list.get(2)).setText(event.getX() + "," + event.getY());
                for (int i = 0; i < _quarters.get(_currentQuarter - 1).size(); i++) {
                    if (_quarters.get(_currentQuarter - 1).get(i).isItYou((int) event.getX(), (int) event.getY())/*&&
                            (_ox==(int) event.getX()&&_oy==(int) event.getY())*/) {
                        _ui.getIGH().respondCitySelection(_quarters.get(_currentQuarter - 1).get(i));
                        System.out.println(event.getX()+","+event.getY()+"A");
                    }
                }
            }
        });
    }

    private void setDragListener() {
        setOnMousePressed((MouseEvent event) -> {
            _ishomecity = false;
            _ox=(int) event.getX();
            _oy=(int) event.getY();
            for (int i = 0; i < _quarters.get(_currentQuarter - 1).size(); i++) {
                if (_quarters.get(_currentQuarter - 1).get(i).isItYou((int) event.getX(), (int) event.getY())) {
                    if (_quarters.get(_currentQuarter - 1).get(i).getName().
                            equals(_ui.getGM().getPlayer().getCity().getName())) {
                        _ishomecity = true;
                    }
                }
            }
        });
        setOnMouseReleased((MouseEvent event) -> {
            if (_ishomecity||(_ox==(int) event.getX()&&_oy==(int) event.getY())) {
                for (int i = 0; i < _quarters.get(_currentQuarter - 1).size(); i++) {
                    if (_quarters.get(_currentQuarter - 1).get(i).isItYou((int) event.getX(), (int) event.getY())) {
                        _ui.getIGH().respondCitySelection(_quarters.get(_currentQuarter - 1).get(i));
                    }
                }
            }
        });
    }

    public void setCurrentQuarter(int i) {
        _currentQuarter = i;
    }

    public int getCurrentQuarter() {
        return _currentQuarter;
    }

    public ArrayList<ArrayList<City>> getCities() {
        return _quarters;
    }

    public City getCity(String name) {
        for (int i = 0; i < _quarters.size(); i++) {
            for (int j = 0; j < _quarters.get(i).size(); j++) {
                if (_quarters.get(i).get(j).getName().equals(name)) {
                    return _quarters.get(i).get(j);
                }
            }
        }
        System.out.println("Can't find city:" + name);
        return null;
    }
    public ArrayList<City> getAllCities(){
        return _allCities;
    }
}
