/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JTEComponents;

import JTEGraphic.JTEUI;
import JTEStart.Main;
import JTEStart.Main.JTEPropertyType;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import properties_manager.PropertiesManager;

public class History extends BorderPane {

    private JTEUI _ui;
    private ArrayList<ScrollPane> _scrollpanes;
    private ArrayList<VBox> _names;
    private ArrayList<VBox> _steps;
    private Label _description;
    private FlowPane _top;
    private Font _font;

    public History(JTEUI ui) {
        _ui = ui;
        _top = new FlowPane();
        _top.setAlignment(Pos.CENTER);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        _scrollpanes = new ArrayList<ScrollPane>();
        for (int i = 0; i < 12; i++) {
            ScrollPane pane = new ScrollPane();
            pane.setPrefSize(Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_WIDTH)) / 12,
                    Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_HEIGHT)) - 234);
            pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            pane.setStyle("-fx-background-color:brown");
            _scrollpanes.add(pane);
            _top.getChildren().add(pane);
        }
        _names = new ArrayList<VBox>();
        for (int i = 0; i < 12; i = i + 2) {
            VBox name = new VBox();
            name.setStyle("-fx-background-color:brown");
            name.setPrefSize(Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_WIDTH)) / 12,
                    Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_HEIGHT)) - 234);
            _names.add(name);
            _scrollpanes.get(i).setContent(name);
        }
        _steps = new ArrayList<VBox>();
        for (int i = 1; i < 12; i = i + 2) {
            VBox step = new VBox();
            step.setStyle("-fx-background-color:brown");
            step.setPrefSize(Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_WIDTH)) / 12,
                    Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_HEIGHT)) - 234);
            _steps.add(step);
            _scrollpanes.get(i).setContent(step);
        }
        _font = Font.font(props.getProperty(Main.JTEPropertyType.LETTERS_FONT_FAMILY), FontWeight.BOLD,
                Integer.parseInt(props.getProperty(Main.JTEPropertyType.LETTERS_FONT_SIZE)));
        setPrefSize(Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_WIDTH)),
                Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_HEIGHT)) - 40);
        setStyle("-fx-background-color:brown");
        setTop(_top);
        _description = new Label();
        _description.setFont(_font);
        _description.setWrapText(true);
        setBottom(_description);
    }

    public void addName(int player, String name) {
        Text text = new Text(name);
        switch (player) {
            case 0:
                text.setFill(Color.BLACK);
                break;
            case 1:
                text.setFill(Color.BLUE);
                break;
            case 2:
                text.setFill(Color.GREEN);
                break;
            case 3:
                text.setFill(Color.RED);
                break;
            case 4:
                text.setFill(Color.WHITE);
                break;
            case 5:
                text.setFill(Color.YELLOW);
                break;
        }
        text.setFont(_font);
        _names.get(player).getChildren().add(text);
    }

    public void addStep(int player, int steps) {
        Text text = new Text("   "+steps);
        switch (player) {
            case 0:
                text.setFill(Color.BLACK);
                break;
            case 1:
                text.setFill(Color.BLUE);
                break;
            case 2:
                text.setFill(Color.GREEN);
                break;
            case 3:
                text.setFill(Color.RED);
                break;
            case 4:
                text.setFill(Color.WHITE);
                break;
            case 5:
                text.setFill(Color.YELLOW);
                break;
        }
        text.setFont(_font);
        _steps.get(player).getChildren().add(text);
    }

    public void update() {
        for (int i = 0; i < _names.size(); i++) {
            _names.get(i).getChildren().clear();
        }
        ArrayList<Player> players = _ui.getGM().getPlayers();
        for (int i = 0; i < players.size(); i++) {
            addName(i, players.get(i).getName());
            for (int j = 0; j < players.get(i).getHistory().size(); j++) {
                addName(i, players.get(i).getHistory().get(j));
            }
        }
        for (int i = 0; i < _steps.size(); i++) {
            _steps.get(i).getChildren().clear();
            Label step = new Label(" Rolls");
            step.setFont(_font);
            _steps.get(i).getChildren().add(step);
        }
        int count = 0;
        int size = _ui.getGM().getPlayers().size();
        for (int j = 0; j < _ui.getGM().getDice().getHistory().size(); j++) {
            if (count == size) {
                count = 0;
            }
            addStep(count, _ui.getGM().getDice().getHistory().get(j));
            count = count + 1;
        }
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String text ="";
        String name = _ui.getGM().getPlayer().getCity().getName();
        name = name.substring(0, 1) + name.substring(1, name.length()).toLowerCase();
        for(int i=0;i<56-name.length()/2;i++){
            text=text+" ";
        }
        try {
            text = text + name + "\n" +"   "+props.getPropertyOptionsList(name + "_D").get(0);
            _description.setText(text);
        } catch (NullPointerException e) {
            text = text + name + "\n" + "                                        Sorry No Information For This City.";
            _description.setText(text);
        }
    }
}
