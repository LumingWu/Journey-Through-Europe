package JTEGraphic;


import JTEComponents.City;
import JTEComponents.Player;
import JTEStart.Main.JTEPropertyType;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;
import properties_manager.PropertiesManager;

public class GameDynamic extends Canvas{
    private JTEUI _ui;
    private GraphicsContext _gc;
    private Image AC14;
    private Image AC58;
    private Image DF14;
    private Image DF58;
    private Image mainMap;
    private ArrayList<Image> _flags;
    private ArrayList<Image> _pieces;
    private AnimationTimer _timer;
    private PathTransition _pt;
    public GameDynamic(JTEUI ui){
        _ui=ui;
        
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        String mapPath=props.getProperty(JTEPropertyType.MAP_PATH);
        ArrayList maps=props.getPropertyOptionsList(JTEPropertyType.SUBMAP_NAME_OPTIONS);
        AC14=new Image(mapPath+maps.get(0));
        AC58=new Image(mapPath+maps.get(1));
        DF14=new Image(mapPath+maps.get(2));
        DF58=new Image(mapPath+maps.get(3));
        mainMap=new Image(mapPath+props.getProperty(JTEPropertyType.MAIN_MAP));
        
        _flags=new ArrayList<Image>();
        String flagPath=props.getProperty(JTEPropertyType.FLAG_PATH);
        ArrayList flags=props.getPropertyOptionsList(JTEPropertyType.FLAG_NAME_OPTIONS);
        for(int i=0;i<flags.size();i++){
            _flags.add(new Image(flagPath+flags.get(i)));
        }
        
        _pieces=new ArrayList<Image>();
        String piecePath=props.getProperty(JTEPropertyType.PIECE_PATH);
        ArrayList pieces=props.getPropertyOptionsList(JTEPropertyType.PIECE_NAME_OPTIONS);
        for(int i=0;i<pieces.size();i++){
            _pieces.add(new Image(piecePath+pieces.get(i)));
        }
        
    }
    public void drawPlayer(int quarter){
        GraphicsContext gc=this.getGraphicsContext2D();
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        ArrayList<Player> _players=_ui.getGM().getPlayers();
        for(int i=0;i<_players.size();i++){
            Player currentPlayer=_ui.getGM().getPlayer();
            if(_players.get(i).getHomeQuarter()==quarter){
                gc.drawImage(_flags.get(i),_players.get(i).getHomeCity().getX()-_flags.get(i).getWidth()/2
                        ,_players.get(i).getHomeCity().getY()-_flags.get(i).getHeight()/2-10);
            }
            if(_players.get(i).getCity().getQuarter()==quarter){
                gc.drawImage(_pieces.get(i),_players.get(i).getCity().getX()-_pieces.get(i).getWidth()/2
                        ,_players.get(i).getCity().getY()-_pieces.get(i).getHeight()/2-10);
            }
            if(currentPlayer.getCity().getQuarter()==quarter){
                ArrayList<String> landNeighbors=props.getPropertyOptionsList(currentPlayer.getCity().getName()+"_LAND");
                ArrayList<String> seaNeighbors=props.getPropertyOptionsList(currentPlayer.getCity().getName()+"_SEA");
                ArrayList<City> quarterCities=_ui.getGS().getCities().get(quarter-1);
                City target=null;
                
                for(int j=0;j<landNeighbors.size();j++){
                    for(int k=0;k<quarterCities.size();k++){
                        if(quarterCities.get(k).getName().equals(landNeighbors.get(j))){
                            target=quarterCities.get(k);
                        }
                    }
                    if(target!=null){
                    Line line=new Line(currentPlayer.getCity().getX(),currentPlayer.getCity().getY(),
                    target.getX(),target.getY());
                    line.setStrokeWidth(5);
                    line.setStroke(Color.RED);
                    _ui.getLineBoard().getChildren().add(line);
                    }
                }
                for(int j=0;j<seaNeighbors.size();j++){
                    for(int k=0;k<quarterCities.size();k++){
                        if(quarterCities.get(k).getName().equals(seaNeighbors.get(j))){
                            target=quarterCities.get(k);
                        }
                    }
                    if(target!=null){
                    Line line=new Line(currentPlayer.getCity().getX(),currentPlayer.getCity().getY(),
                    target.getX(),target.getY());
                    line.setStrokeWidth(5);
                    line.setStroke(Color.RED);
                    _ui.getLineBoard().getChildren().add(line);
                    }
                }
                
            }
        }
    }
    public void drawAC14(){
        GraphicsContext gc=this.getGraphicsContext2D();
        gc.clearRect(0,0,getWidth(),getHeight());
        gc.drawImage(AC14,0,0,getWidth(),getHeight());
        _ui.getLineBoard().getChildren().clear();
    }
    public void drawAC58(){
        GraphicsContext gc=this.getGraphicsContext2D();
        gc.clearRect(0,0,getWidth(),getHeight());
        gc.drawImage(AC58,0,0,getWidth(),getHeight());
        _ui.getLineBoard().getChildren().clear();
    }
    public void drawDF14(){
        GraphicsContext gc=this.getGraphicsContext2D();
        gc.clearRect(0,0,getWidth(),getHeight());
        gc.drawImage(DF14,0,0,getWidth(),getHeight());
        _ui.getLineBoard().getChildren().clear();
    }
    public void drawDF58(){
        GraphicsContext gc=this.getGraphicsContext2D();
        gc.clearRect(0,0,getWidth(),getHeight());
        gc.drawImage(DF58,0,0,getWidth(),getHeight());
        _ui.getLineBoard().getChildren().clear();
    }
    public void drawMap(){
        GraphicsContext gc=this.getGraphicsContext2D();
        gc.clearRect(0,0,getWidth(),getHeight());
        gc.drawImage(mainMap,0,0,getWidth(),getHeight());
    }
    public void drawQuarter(int quarter){
        switch(quarter){
            case 1:
                drawAC14();
                break;
            case 2:
                drawDF14();
                break;
            case 3:
                drawAC58();
                break;
            case 4:
                drawDF58();
                break;
        }
    }
}
