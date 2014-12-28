
package JTEStart;

import JTEManager.Dijkstra;
import JTEGraphic.JTEUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

public class Main extends Application{
    static String PROPERTY_TYPES_LIST = "property_types.txt";
    
    static String UI_PROPERTIES_FILE_NAME = "properties.xml";
    static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";
    static String DATA_PATH = "./data/";
    @Override
    public void start(Stage primaryStage){
        try {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(JTEPropertyType.UI_PROPERTIES_FILE_NAME,UI_PROPERTIES_FILE_NAME);
            props.addProperty(JTEPropertyType.PROPERTIES_SCHEMA_FILE_NAME,PROPERTIES_SCHEMA_FILE_NAME);
            props.addProperty(JTEPropertyType.DATA_PATH.toString(),DATA_PATH);
            props.loadProperties(UI_PROPERTIES_FILE_NAME,PROPERTIES_SCHEMA_FILE_NAME);

            String title = props.getProperty(JTEPropertyType.GAME_TITLE_TEXT);
            primaryStage.setTitle(title);

            JTEUI root = new JTEUI();
            BorderPane mainPane = root.GetMainPane();
            StackPane stacker = new StackPane();
            stacker.getChildren().add(mainPane);
            root.setStage(primaryStage);

            Scene scene = new Scene(stacker, Double.parseDouble(props.getProperty(JTEPropertyType.WINDOW_WIDTH)),
                    Double.parseDouble(props.getProperty(JTEPropertyType.WINDOW_HEIGHT)));
            primaryStage.setScene(scene);
            //new Parser();
            
            primaryStage.show();
        } catch (Exception e) {e.printStackTrace();}
    
        }
        
        public static void main(String[] args){
            launch(args);
        }
        
        public enum JTEPropertyType{
            UI_PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME,
            DATA_PATH, DEFAULT_IMG_PATH,DICE_PATH,FLAG_PATH,PIECE_PATH,MAP_PATH,GREEN_CARD_PATH,RED_CARD_PATH,YELLOW_CARD_PATH,
            ABOUTJTE_IMG_NAME,GAMEHISTORY_IMG_NAME,FLIGHT_IMG_NAME,FLIGHT_PLAN_NAME,MAIN_MAP,
            GAME_TITLE_TEXT,
            WINDOW_WIDTH,WINDOW_HEIGHT,CARD_WIDTH,CARD_HEIGHT,BOARD_WIDTH,BLACK_DOT_RADIUS,EAST_WIDTH,
            LETTERS_FONT_FAMILY,LETTERS_FONT_SIZE,
            SPLASH_SCREEN_NAME,START_IMG_NAME,LOAD_IMG_NAME,ABOUT_IMG_NAME,QUIT_IMG_NAME,SELECTION_BACKGROUND_NAME,
            ABOUT_TEXT,
            FLAG_NAME_OPTIONS,OPTIONS_FONT_SIZE,PIECE_NAME_OPTIONS,DICE_NAME_OPTIONS,SUBMAP_NAME_OPTIONS,
            QUARTER_FILE_NAMES,
        }
        
}
    

