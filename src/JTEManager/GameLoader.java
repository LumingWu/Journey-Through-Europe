package JTEManager;

import JTEComponents.Card;
import JTEComponents.City;
import JTEComponents.Player;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLoader {

    private GameManager _gm;

    public GameLoader(GameManager gm) {
        _gm = gm;
    }

    public void read() {
        readPlayer();
        readCities();
        readCards();
        readHistory();
        readRoll();
    }

    public void readPlayer() {
        try {
            Scanner scanner = new Scanner(new File(System.getProperty("user.dir") + "/data/players.txt"));
            ArrayList<Player> players = new ArrayList<Player>();
            while (scanner.hasNext()) {
                Player player = new Player(scanner.nextLine(), scanner.nextLine(), Integer.parseInt(scanner.nextLine()));
                player.setStep(Integer.parseInt(scanner.nextLine()));
                players.add(player);
            }
            scanner.close();
            _gm.setPlayers(players);
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getStep() > 0) {
                    _gm.setTurn(i);
                }
            }
        } catch (FileNotFoundException ex) {}
    }

    public void readCities() {
        try {
            Scanner scanner = new Scanner(new File(System.getProperty("user.dir") + "/data/cities.txt"));
            ArrayList<Player> players = _gm.getPlayers();
            int counter = 0;
            while (counter < players.size()) {
                City home = new City(scanner.nextLine(), scanner.nextLine(), Integer.parseInt(scanner.nextLine()),
                        Integer.parseInt(scanner.nextLine()), Integer.parseInt(scanner.nextLine()));
                City city = new City(scanner.nextLine(), scanner.nextLine(), Integer.parseInt(scanner.nextLine()),
                        Integer.parseInt(scanner.nextLine()), Integer.parseInt(scanner.nextLine()));
                players.get(counter).setHomeCity(home);
                players.get(counter).setCity(city);
                counter = counter + 1;
            }
            scanner.close();
        } catch (FileNotFoundException ex) {}
    }
    public void readCards(){
        try {
            Scanner scanner= new Scanner(new File(System.getProperty("user.dir") + "/data/cards.txt"));
            int state = 0;
            ArrayList<Player> players = _gm.getPlayers();
            while (scanner.hasNext()) {
                switch (state) {
                    case 0: {
                        String s = scanner.nextLine();
                        System.out.println(s);
                        if (s.charAt(0) == '1'||s.charAt(0) == ' ') {
                            state = 1;
                            break;
                        } else {
                            if(s.charAt(0) == '0'){
                                System.out.println("Skipped Name and Color");
                                players.get(state).getHand().add(new Card(scanner.nextLine(),scanner.nextLine()));
                            }
                            else{
                                System.out.println("Color");
                                players.get(state).getHand().add(new Card(s,scanner.nextLine()));
                            }
                        }
                        break;
                    }
                    case 1: {
                        String s = scanner.nextLine();
                        if (s.charAt(0) == '2'||s.charAt(0) == ' ') {
                            state = 2;
                            break;
                        } else {
                            if(s.charAt(0) == '1'){
                                players.get(state).getHand().add(new Card(scanner.nextLine(),scanner.nextLine()));
                            }
                            else{
                                players.get(state).getHand().add(new Card(s,scanner.nextLine()));
                            }
                        }
                        break;
                    }
                    case 2: {
                        String s = scanner.nextLine();
                        if (s.charAt(0) == '3'||s.charAt(0) == ' ') {
                            state = 3;
                            break;
                        } else {
                            if(s.charAt(0) == '2'){
                                players.get(state).getHand().add(new Card(scanner.nextLine(),scanner.nextLine()));
                            }
                            else{
                                players.get(state).getHand().add(new Card(s,scanner.nextLine()));
                            }
                        }
                        break;
                    }
                    case 3: {
                        String s = scanner.nextLine();
                        if (s.charAt(0) == '4'||s.charAt(0) == ' ') {
                            state = 4;
                            break;
                        } else {
                            if(s.charAt(0) == '3'){
                                players.get(state).getHand().add(new Card(scanner.nextLine(),scanner.nextLine()));
                            }
                            else{
                                players.get(state).getHand().add(new Card(s,scanner.nextLine()));
                            }
                        }
                        break;
                    }
                    case 4: {
                        String s = scanner.nextLine();
                        if (s.charAt(0) == '5'||s.charAt(0) == ' ') {
                            state = 5;
                            break;
                        } else {
                            if(s.charAt(0) == '4'){
                                players.get(state).getHand().add(new Card(scanner.nextLine(),scanner.nextLine()));
                            }
                            else{
                                players.get(state).getHand().add(new Card(s,scanner.nextLine()));
                            }
                        }
                        break;
                    }
                    case 5: {
                        String s = scanner.nextLine();
                        if (s.charAt(0) == '6'||s.charAt(0) == ' ') {
                            state = 6;
                            break;
                        } else {
                            if(s.charAt(0) == '5'){
                                players.get(state).getHand().add(new Card(scanner.nextLine(),scanner.nextLine()));
                            }
                            else{
                                players.get(state).getHand().add(new Card(s,scanner.nextLine()));
                            }
                        }
                        break;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException ex) {}
    }

    public void readHistory() {
        try {
            Scanner scanner = new Scanner(new File(System.getProperty("user.dir") + "/data/history.txt"));
            int state = 0;
            ArrayList<Player> players = _gm.getPlayers();
            for(int i=0;i<players.size();i++){
                players.get(i).getHistory().clear();
            }
            while (scanner.hasNext()) {
                switch (state) {
                    case 0: {
                        String s = scanner.nextLine();
                        if (s.charAt(0) == '1'||s.charAt(0) == ' ') {
                            state = 1;
                            break;
                        } else {
                            if(s.charAt(0)!='0'){
                                players.get(state).getHistory().add(s);
                            }
                        }
                        break;
                    }
                    case 1: {
                        String s = scanner.nextLine();
                        if (s.charAt(0) == '2'||s.charAt(0) == ' ') {
                            state = 2;
                            break;
                        } else {
                            if(s.charAt(0)!='1'){
                                players.get(state).getHistory().add(s);
                            }
                        }
                        break;
                    }
                    case 2: {
                        String s = scanner.nextLine();
                        if (s.charAt(0) == '3'||s.charAt(0) == ' ') {
                            state = 3;
                            break;
                        } else {
                            if(s.charAt(0)!='2'){
                                players.get(state).getHistory().add(s);
                            }
                        }
                        break;
                    }
                    case 3: {
                        String s = scanner.nextLine();
                        if (s.charAt(0) == '4'||s.charAt(0) == ' ') {
                            state = 4;
                            break;
                        } else {
                            if(s.charAt(0)!='3'){
                                players.get(state).getHistory().add(s);
                            }
                        }
                        break;
                    }
                    case 4: {
                        String s = scanner.nextLine();
                        if (s.charAt(0) == '5'||s.charAt(0) == ' ') {
                            state = 5;
                            break;
                        } else {
                            if(s.charAt(0)!='4'){
                                players.get(state).getHistory().add(s);
                            }
                        }
                        break;
                    }
                    case 5: {
                        String s = scanner.nextLine();
                        if (s.charAt(0) == '6'||s.charAt(0) == ' ') {
                            state = 6;
                            break;
                        } else {
                            if(s.charAt(0)!='5'){
                                players.get(state).getHistory().add(s);
                            }
                        }
                        break;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException ex) {}
    }
    public void readRoll(){
        try {
            Scanner scanner= new Scanner(new File(System.getProperty("user.dir") + "/data/rolls.txt"));
            ArrayList<Integer> history=new ArrayList<Integer>();
            while(scanner.hasNext()){
                history.add(Integer.parseInt(scanner.nextLine()));
            }
            _gm.getDice().setHistory(history);
        } catch (FileNotFoundException ex) {}
    }
}
