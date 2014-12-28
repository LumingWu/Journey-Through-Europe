package JTEGraphic;

import JTEComponents.FlightPlan;
import JTEComponents.History;
import JTEHandler.InGameHandler;
import JTEHandler.OutGameHandler;
import JTEManager.Dijkstra;
import JTEManager.GameAI;
import JTEManager.GameManager;
import JTEManager.GameSaver;
import JTEStart.Main.JTEPropertyType;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

public class JTEUI {
    private final String _dataPath;
    private final String _defaultImagePath;
    private final String _pieceImagePath;
    private final String _flagImagePath;
    private final String _diceImagePath;
    private final String _greenImagePath;
    private final String _redImagePath;
    private final String _yellowImagePath;
    private final String _fontFamily;
    private final int _fontSize;
    
    private Stage primaryStage;
    private final GameManager _GM;
    private final InGameHandler _IGH;
    private final OutGameHandler _OGH;
    
    private double _width;
    private double _height;
    private Insets _marginlessinsets;
    private Insets _buttoninsets;
    
    private BorderPane _mainPane;
    
    private StackPane _splashScreen;
    
    private BorderPane _gameScreen;
    private StackPane _gameBoard;
    private GameDynamic _gameDynamic;
    private GameStatic _gameStatic;
    
    private BorderPane _selectionScreen;
    private ArrayList<TextField> _fields;
    
    private VBox _aboutScreen;
    
    private BorderPane _levelHistoryScreen;
    
    private BorderPane _flightPlanScreen;
    
    private ArrayList _eastElements;
    private ArrayList _westElements;
    private History _history;
    
    private GameSaver _gs;
    private BorderPane _winScreen;
    private Label _winner;
    private GameAI _ai;
    public JTEUI(){
        _GM=new GameManager(this);
        _IGH=new InGameHandler(this);
        _OGH=new OutGameHandler(this);
        _gs=new GameSaver(this);
        _ai=new GameAI(this);
        
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        _dataPath=props.getProperty(JTEPropertyType.DATA_PATH);
        _fontFamily=props.getProperty(JTEPropertyType.LETTERS_FONT_FAMILY);
        _fontSize=Integer.parseInt(props.getProperty(JTEPropertyType.LETTERS_FONT_SIZE));
        _defaultImagePath=props.getProperty(JTEPropertyType.DEFAULT_IMG_PATH);
        _pieceImagePath=props.getProperty(JTEPropertyType.PIECE_PATH);
        _diceImagePath=props.getProperty(JTEPropertyType.DICE_PATH);
        _flagImagePath=props.getProperty(JTEPropertyType.FLAG_PATH);
        _greenImagePath=props.getProperty(JTEPropertyType.GREEN_CARD_PATH);
        _yellowImagePath=props.getProperty(JTEPropertyType.YELLOW_CARD_PATH);
        _redImagePath=props.getProperty(JTEPropertyType.RED_CARD_PATH);
        
        initialJTEUI();
    }
    
    public void initialJTEUI(){
        _buttoninsets=new Insets(1,1,1,1);
        _marginlessinsets=new Insets(0,0,0,0);
        initialMainPane();
        initialSplashScreen();
        initialAboutScreen();
        initialSelectionScreen();
        initialGameScreen();
        initialHistoryScreen();
        initialFlightPlanScreen();
        initialWin();
        
        changeScreen(JTEUIState.SPLASH_SCREEN_STATE);
    }
    
    public void initialMainPane(){
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        _width=Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_WIDTH));
        _height=Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_HEIGHT));
        _mainPane=new BorderPane();
        _mainPane.resize(_width, _height);
        _mainPane.setPadding(_marginlessinsets);
    }
    
    public void initialSplashScreen(){
        _splashScreen=new StackPane();
        VBox _splashSelection=new VBox();
        _splashSelection.setAlignment(Pos.CENTER);
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        
        FlowPane _splashArt=new FlowPane();
        Label _splashArtImage=new Label();
        _splashArtImage.setGraphic(loadImage(_defaultImagePath,props.getProperty(JTEPropertyType.SPLASH_SCREEN_NAME)));
        _splashArt.getChildren().add(_splashArtImage);
        
        Button start=new Button();
        start.setGraphic(loadImage(_defaultImagePath,props.getProperty(JTEPropertyType.START_IMG_NAME)));
        start.setPadding(_buttoninsets);
        start.setOnAction((ActionEvent event) -> {
            _OGH.respondSwitchScreen(JTEUIState.VIEW_SELECTION_STATE);
        });
        Button load=new Button();
        load.setGraphic(loadImage(_defaultImagePath,props.getProperty(JTEPropertyType.LOAD_IMG_NAME)));
        load.setPadding(_buttoninsets);
        load.setOnAction((ActionEvent event) -> {
            _GM.loadGame();
            _OGH.respondLoadGame();
        });
        Button about=new Button();
        about.setGraphic(loadImage(_defaultImagePath,props.getProperty(JTEPropertyType.ABOUT_IMG_NAME)));
        about.setPadding(_buttoninsets);
        about.setOnAction((ActionEvent event) -> {
            _OGH.respondSwitchScreen(JTEUIState.VIEW_ABOUT_STATE);
        });
        Button quit=new Button();
        quit.setGraphic(loadImage(_defaultImagePath,props.getProperty(JTEPropertyType.QUIT_IMG_NAME)));
        quit.setPadding(_buttoninsets);
        quit.setOnAction((ActionEvent event) -> {
            _OGH.respondExitGame(primaryStage);
        });
        _splashSelection.getChildren().addAll(start,load,about,quit);
        
        _splashScreen.setAlignment(Pos.CENTER);
        _splashScreen.getChildren().addAll(_splashArt,_splashSelection);
    }
    
    public void initialSelectionScreen(){
        _selectionScreen=new BorderPane();
        _selectionScreen.setStyle("-fx-background-color:coral");
        
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        
        HBox topBar=new HBox();
        topBar.setSpacing(10);
        topBar.setAlignment(Pos.CENTER);
        Label numberOfPlayers=new Label("Number of Players:");
        numberOfPlayers.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        ChoiceBox choice=new ChoiceBox();
        choice.setItems(FXCollections.observableArrayList("1","2","3","4","5","6"));
        final List options=choice.getItems();
        choice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                _OGH.respondChangePlayerNumber((int)newValue+1);
            }
        });
        choice.setStyle("-fx-font:"+_fontSize+"px "+_fontFamily);
        
        Button go= new Button("GO!");
        go.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        go.setOnAction((ActionEvent event) -> {
            _OGH.respondNewGame();            
        });
        
        Button back= new Button("Back");
        back.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        back.setOnAction((ActionEvent event) -> {
            _OGH.respondSwitchScreen(JTEUIState.SPLASH_SCREEN_STATE);    
        });
        topBar.getChildren().addAll(numberOfPlayers,choice,go,back);
        
        
        GridPane _gridPane=new GridPane();
        _gridPane.setStyle("-fx-background-color:coral");
        _gridPane.setPrefSize(_width, _height-50);
        
        _fields=new ArrayList();
        ArrayList flagnames=new ArrayList();
        flagnames=props.getPropertyOptionsList(JTEPropertyType.FLAG_NAME_OPTIONS);
        for(int i=0;i<6;i++){
            int index=i;
            StackPane stacker=new StackPane();
            Label _background=new Label();
            _background.setGraphic(loadImage(_defaultImagePath,props.getProperty(JTEPropertyType.SELECTION_BACKGROUND_NAME)));
            HBox box=new HBox();
            box.setAlignment(Pos.CENTER);
            box.setPrefSize(_background.getWidth(),_background.getHeight());
            
            Label flag=new Label();
            flag.setGraphic(loadImage(_flagImagePath,flagnames.get(i)+""));
            
            VBox subbox=new VBox();
            subbox.setAlignment(Pos.CENTER);
            ToggleGroup group=new ToggleGroup();
            RadioButton button1=new RadioButton("Player  ");
            button1.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
            button1.setToggleGroup(group);
            button1.setSelected(true);
            button1.setOnAction((ActionEvent event) -> {
                _OGH.respondChangePlayerType(index,"Player");   
            });
            RadioButton button2=new RadioButton("Computer");
            button2.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
            button2.setToggleGroup(group);
            button2.setOnAction((ActionEvent event) -> {
                _OGH.respondChangePlayerType(index,"Computer");    
            });
            subbox.getChildren().addAll(button1,button2);
            
            TextField option=new TextField();
            option.setPromptText("Name");
            option.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
            _fields.add(option);
            
            stacker.getChildren().addAll(_background,box);
            box.getChildren().addAll(flag,subbox,option);
            stacker.setVisible(false);
            if(i<3){
                _gridPane.add(stacker,i,1);
            }
            else{
                _gridPane.add(stacker,i-3,2);
            }
        }HBox gap=new HBox();gap.setPrefHeight(_height/10);_gridPane.add(gap,1,0);
        
        _selectionScreen.setTop(topBar);
        _selectionScreen.setBottom(_gridPane);
    }
    
    public void initialAboutScreen(){
        _aboutScreen=new VBox();
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        
        VBox root=new VBox();
        final WebView browser=new WebView();
        final WebEngine webEngine=browser.getEngine();
        root.getChildren().add(browser);
        webEngine.load("http://en.wikipedia.org/wiki/Journey_Through_Europe");
        
        Label companyText=new Label(props.getProperty(JTEPropertyType.ABOUT_TEXT));
        companyText.setWrapText(true);
        companyText.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        
        Button returnButton=new Button("Return");
        returnButton.setStyle("-fx-background-color:lightgray");
        returnButton.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        returnButton.setOnAction((ActionEvent event) -> {
            _OGH.respondSwitchScreen(JTEUIState.SPLASH_SCREEN_STATE);
        });
        
        _aboutScreen.setAlignment(Pos.CENTER);
        _aboutScreen.getChildren().addAll(root,companyText,returnButton);
    }
    
    public void initialGameScreen(){
        _gameScreen=new BorderPane();
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        
        ScrollPane _westScroller=new ScrollPane();
        VBox _westToolBar=new VBox();
        
        _westElements=new ArrayList();
        Label _playerTurnWest=new Label();
        _playerTurnWest.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        _westElements.add(_playerTurnWest);
        _westToolBar.getChildren().add(_playerTurnWest);
        for(int i=0;i<3;i++){
            Label card=new Label();
            card.setOnMouseClicked((MouseEvent event) -> {
                _OGH.respondFlipCard(card);
            });
            _westElements.add(card);
            _westToolBar.getChildren().add(card);
        }
        _westToolBar.setPadding(_marginlessinsets);
        _westScroller.setContent(_westToolBar);
        _westScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        _westScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        int cardWidth=Integer.parseInt(props.getProperty(JTEPropertyType.CARD_WIDTH));
        _westScroller.setPrefWidth(cardWidth);//CardWidth
        _westToolBar.setStyle("-fx-background-color:brown");
        _westToolBar.setPrefHeight(Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_HEIGHT)));
        
        ScrollPane _eastScroller=new ScrollPane();
        VBox _eastToolBar=new VBox();
        _eastElements=new ArrayList();
        Label playerTurn=new Label();
        playerTurn.setContentDisplay(ContentDisplay.RIGHT);
        playerTurn.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        Label roll=new Label();
        roll.setContentDisplay(ContentDisplay.BOTTOM);
        roll.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        _eastElements.add(playerTurn);
        _eastElements.add(roll);
        
        GridPane square=new GridPane();
        
        Label AC14=new Label();
        AC14.setStyle("-fx-background-color:red");
        AC14.setPrefSize(cardWidth/3,cardWidth/3);
        AC14.setOnMouseClicked((MouseEvent event) -> {
            _IGH.respondSelectSector("AC14");
        });
        square.add(AC14,1,1);
        Label AC58=new Label();
        AC58.setStyle("-fx-background-color:blue");
        AC58.setPrefSize(cardWidth/3,cardWidth/3);
        AC58.setOnMouseClicked((MouseEvent event) -> {
            _IGH.respondSelectSector("AC58");
        });
        square.add(AC58,1,2);
        Label DF14=new Label();
        DF14.setStyle("-fx-background-color:yellow");
        DF14.setPrefSize(cardWidth/3,cardWidth/3);
        DF14.setOnMouseClicked((MouseEvent event) -> {
            _IGH.respondSelectSector("DF14");
        });
        square.add(DF14,2,1);
        Label DF58=new Label();
        DF58.setStyle("-fx-background-color:green");
        DF58.setPrefSize(cardWidth/3,cardWidth/3);
        DF58.setOnMouseClicked((MouseEvent event) -> {
            _IGH.respondSelectSector("DF58");
        });
        square.add(DF58,2,2);
        
        Label signAC=new Label(" A-C");
        signAC.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        square.add(signAC,1,0);
        Label signDF=new Label(" D-F");
        signDF.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        square.add(signDF,2,0);
        Label sign14=new Label("   1-4");
        sign14.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        square.add(sign14,0,1);
        Label sign58=new Label("   5-8");
        sign58.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        square.add(sign58,0,2);
        
        Button gameHistory=new Button();
        gameHistory.setGraphic(loadImage(_defaultImagePath,props.getProperty(JTEPropertyType.GAMEHISTORY_IMG_NAME)));
        gameHistory.setPadding(_buttoninsets);
        gameHistory.setOnAction((ActionEvent event) -> {
            _history.update();
            _OGH.respondSwitchScreen(JTEUIState.VIEW_HISTORY_STATE);    
        });
        Button aboutJTE=new Button();
        aboutJTE.setGraphic(loadImage(_defaultImagePath,props.getProperty(JTEPropertyType.ABOUTJTE_IMG_NAME)));
        aboutJTE.setPadding(_buttoninsets);
        aboutJTE.setOnAction((ActionEvent event) -> {
            _OGH.respondSwitchScreen(JTEUIState.VIEW_ABOUT_STATE);    
        });
        Button flight=new Button();
        flight.setGraphic(loadImage(_defaultImagePath,props.getProperty(JTEPropertyType.FLIGHT_IMG_NAME)));
        flight.setPadding(_buttoninsets);
        flight.setOnAction((ActionEvent event) -> {
            _OGH.respondSwitchScreen(JTEUIState.VIEW_FLIGHT_PLAN_STATE);
        });
        
        Label selectedCity=new Label("Select City");
        selectedCity.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        _eastElements.add(selectedCity);
        
        _eastToolBar.getChildren().addAll(playerTurn,roll,selectedCity,square,gameHistory,aboutJTE,flight);
        _eastToolBar.setPadding(new Insets(10,10,10,10));
        _eastToolBar.setAlignment(Pos.CENTER);
        _eastToolBar.setSpacing(10);
        _eastScroller.setContent(_eastToolBar);
        _eastScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        _eastScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        _eastScroller.setPrefWidth(Integer.parseInt(props.getProperty(JTEPropertyType.EAST_WIDTH)));
        _eastToolBar.setPrefHeight(Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_HEIGHT)));
        _eastScroller.setStyle("-fx-background-color:brown");
        _eastToolBar.setStyle("-fx-background-color:brown");
        _eastToolBar.setPrefWidth(Integer.parseInt(props.getProperty(JTEPropertyType.EAST_WIDTH)));
        
        _gameBoard=new StackPane();
        _gameDynamic=new GameDynamic(this);
        _gameDynamic.setWidth(Integer.parseInt(props.getProperty(JTEPropertyType.BOARD_WIDTH)));
        _gameDynamic.setHeight(_height);
        _gameDynamic.drawMap();
        _gameBoard.getChildren().add(_gameDynamic);
        
        Pane linePane=new Pane();
        _gameBoard.getChildren().add(linePane);
        
        _gameStatic=new GameStatic(this);
        _gameBoard.getChildren().add(_gameStatic);
        
        _gameScreen.setLeft(_westScroller);
        _gameScreen.setCenter(_gameBoard);
        _gameScreen.setRight(_eastScroller);
    }
    
    public void initialHistoryScreen(){
        _levelHistoryScreen=new BorderPane();
        _levelHistoryScreen.setStyle("-fx-background-color:brown");
        
        BorderPane top=new BorderPane();
        _history=new History(this);
        top.setCenter(_history);
        FlowPane bottom=new FlowPane();
        bottom.setAlignment(Pos.CENTER);
        Button _save=new Button("Save");
        _save.setStyle("-fx-background-color:lightgray");
        _save.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        _save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(_winner.getText().length()==0){
                    _gs.save();
                    _save.setDisable(true);
                    _save.setText("Saved");
                }
                else{
                    _save.setDisable(false);
                    _save.setText("No Save");
                }
            }
        });
        Button _return=new Button("Return");
        _return.setStyle("-fx-background-color:lightgray");
        _return.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        _return.setOnAction((ActionEvent event) -> {
            _save.setDisable(false);
            _save.setText("Save");
            if(_winner.getText().length()==0){
                _OGH.respondSwitchScreen(JTEUIState.VIEW_GAME_STATE); 
            }
            else{
                _OGH.respondSwitchScreen(JTEUIState.VIEW_WIN_STATE);
            }
        });
        bottom.getChildren().add(_save);
        bottom.getChildren().add(_return);
        
        _levelHistoryScreen.setTop(top);
        _levelHistoryScreen.setBottom(bottom);
        
    }
    
    public void initialFlightPlanScreen(){
        _flightPlanScreen=new BorderPane();
        _flightPlanScreen.setStyle("-fx-background-color:brown");
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        
        BorderPane top=new BorderPane();
        FlightPlan flightplan=new FlightPlan(this);
        top.setCenter(flightplan);
        
        BorderPane bottom=new BorderPane();
        Button _return=new Button("Return");
        _return.setStyle("-fx-background-color:lightgray");
        _return.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize));
        _return.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                _OGH.respondSwitchScreen(JTEUIState.VIEW_GAME_STATE);
            }
        });
        bottom.setCenter(_return);
        
        _flightPlanScreen.setTop(top);
        _flightPlanScreen.setBottom(bottom);
    }
    
    public void initialWin(){
        _winScreen=new BorderPane();
        _winScreen.setStyle("-fx-background-color:brown");
        VBox box=new VBox();
        _winScreen.setCenter(box);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        
        Label title=new Label("Game Result");
        title.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize*8));
        box.getChildren().add(title);
        
        _winner=new Label();
        _winner.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize*4));
        box.getChildren().add(_winner);
        
        Button stat=new Button("Stats");
        stat.setAlignment(Pos.CENTER);
        stat.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize*2));
        stat.setStyle("-fx-background-color:lightgray");
        stat.setPrefSize(200,100);
        stat.setOnAction((ActionEvent event) -> {
            _history.update();
            _OGH.respondSwitchScreen(JTEUIState.VIEW_HISTORY_STATE);
        });
        box.getChildren().add(stat);
        
        Button exit=new Button("Exit");
        exit.setAlignment(Pos.CENTER);
        exit.setFont(Font.font(_fontFamily,FontWeight.BOLD,_fontSize*2));
        exit.setStyle("-fx-background-color:lightgray");
        exit.setPrefSize(200,100);
        exit.setOnAction((ActionEvent event) -> {
            _OGH.respondExitGame(primaryStage);
        });
        box.getChildren().add(exit);
    }
    
    public void changeScreen(JTEUIState state){
        switch(state){
            case SPLASH_SCREEN_STATE:
                _mainPane.setCenter(_splashScreen);
                break;
            case VIEW_ABOUT_STATE:
                _mainPane.setCenter(_aboutScreen);
                break;
            case VIEW_SELECTION_STATE:
                _mainPane.setCenter(_selectionScreen);
                break;
            case VIEW_GAME_STATE:
                _mainPane.setCenter(_gameScreen);
                break;
            case VIEW_HISTORY_STATE:
                _mainPane.setCenter(_levelHistoryScreen);
                break;
            case VIEW_FLIGHT_PLAN_STATE:
                _mainPane.setCenter(_flightPlanScreen);
                break;
            case VIEW_WIN_STATE:
                _mainPane.setCenter(_winScreen);
                break;
        }
    }
    
    public enum JTEUIState{
        SPLASH_SCREEN_STATE, VIEW_HISTORY_STATE, VIEW_ABOUT_STATE,VIEW_FLIGHT_PLAN_STATE,VIEW_GAME_STATE,
        VIEW_SELECTION_STATE,VIEW_WIN_STATE,
        
    }
    
    public ImageView loadImage(String ImgPath,String imageName) {
        Image img = new Image(ImgPath + imageName);
        ImageView imgView=new ImageView(img);
        return imgView;
    }
    
    public void setStage(Stage _stage){
        primaryStage=_stage;
    }
    
    public Stage getStage(){
        return primaryStage;
    }
    
    public BorderPane GetMainPane(){
        return _mainPane;
    }
    
    public GameManager getGM(){
        return _GM;
    }
    public GameDynamic getGD(){
        return _gameDynamic;
    }
    public OutGameHandler getOGH(){
        return _OGH;
    }
    public InGameHandler getIGH(){
        return _IGH;
    }
    public TextField getTextField(int i){
        return _fields.get(i);
    }
    
    public GridPane getGridPane(){
        return (GridPane)_selectionScreen.getBottom();
    }
    
    public ArrayList getWestElements(){
        return _westElements;
    }
    
    public ArrayList getEastElements(){
        return _eastElements;
    }
    public GameStatic getGS(){
        return _gameStatic;
    }
    public Pane getLineBoard(){
        return (Pane)_gameBoard.getChildren().get(1);
    }
    public Label getWinnerBoard(){
        return _winner;
    }
    public GameAI getAI(){
        return _ai;
    }
}
