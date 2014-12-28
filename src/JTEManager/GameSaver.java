package JTEManager;

import JTEComponents.Card;
import JTEComponents.Player;
import JTEGraphic.JTEUI;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameSaver {
    private JTEUI _ui;
    public GameSaver(JTEUI ui){
        _ui=ui;
    }
    public void save(){
        ArrayList<Player> _players=_ui.getGM().getPlayers();
        File fileToSave=new File(System.getProperty("user.dir")+"/data/players.txt");
        FileWriter fw;
        File cityToSave=new File(System.getProperty("user.dir")+"/data/cities.txt");
        FileWriter fwc;
        File cardToSave=new File(System.getProperty("user.dir")+"/data/cards.txt");
        FileWriter fwc2;
        File hisToSave=new File(System.getProperty("user.dir")+"/data/history.txt");
        FileWriter fwh;
        File rollToSave=new File(System.getProperty("user.dir")+"/data/rolls.txt");
        FileWriter fwr;
        try {
            fw = new FileWriter(fileToSave);
            BufferedWriter bw=new BufferedWriter(fw);
            fwc=new FileWriter(cityToSave);
            BufferedWriter bwc=new BufferedWriter(fwc);
            fwc2=new FileWriter(cardToSave);
            BufferedWriter bwc2=new BufferedWriter(fwc2);
            fwh=new FileWriter(hisToSave);
            BufferedWriter bwh=new BufferedWriter(fwh);
            fwr=new FileWriter(rollToSave);
            BufferedWriter bwr=new BufferedWriter(fwr);
            for(int i=0;i<_players.size();i++){
                bw.write(_players.get(i).getName());
                bw.newLine();
                bw.write(_players.get(i).getType());
                bw.newLine();
                bw.write(_players.get(i).getColorNumber()+"");
                bw.newLine();
                bw.write(_players.get(i).getStep()+"");
                bw.newLine();
                
                bwc.write(_players.get(i).getHomeCity().getName());
                bwc.newLine();
                bwc.write(_players.get(i).getHomeCity().getColor());
                bwc.newLine();
                bwc.write(_players.get(i).getHomeCity().getQuarter()+"");
                bwc.newLine();
                bwc.write(_players.get(i).getHomeCity().getX()+"");
                bwc.newLine();
                bwc.write(_players.get(i).getHomeCity().getY()+"");
                bwc.newLine();
                bwc.write(_players.get(i).getCity().getName());
                bwc.newLine();
                bwc.write(_players.get(i).getCity().getColor());
                bwc.newLine();
                bwc.write(_players.get(i).getCity().getQuarter()+"");
                bwc.newLine();
                bwc.write(_players.get(i).getCity().getX()+"");
                bwc.newLine();
                bwc.write(_players.get(i).getCity().getY()+"");
                bwc.newLine();
                
                ArrayList<Card> hand=_players.get(i).getHand();
                bwc2.write(i+"");
                bwc2.newLine();
                for(int j=0;j<hand.size();j++){
                    bwc2.write(hand.get(j).getName());
                    bwc2.newLine();
                    bwc2.write(hand.get(j).getColor());
                    bwc2.newLine();
                }
                
                bwh.write(i+"");
                bwh.newLine();
                for(int j=0;j<_players.get(i).getHistory().size();j++){
                    bwh.write(_players.get(i).getHistory().get(j));
                    bwh.newLine();
                }
                
            }
            for(int j=0;j<_ui.getGM().getDice().getHistory().size();j++){
                    bwr.write(_ui.getGM().getDice().getHistory().get(j)+"");
                    bwr.newLine();
                }
            bw.close();
            bwc.close();
            bwc2.close();
            bwh.close();
            bwr.close();
        } catch (IOException ex) {}
    }
    
}
