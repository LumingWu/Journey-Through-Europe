/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JTEStart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import properties_manager.PropertiesManager;

/**
 *
 * @author Luming
 */
public class Parser {
    public Parser(){
        /*
        PropertiesManager props=PropertiesManager.getPropertiesManager();
        ArrayList<String> red=props.getPropertyOptionsList("RED_CARD_NAME_OPTIONS");
        ArrayList<String> green=props.getPropertyOptionsList("GREEN_CARD_NAME_OPTIONS");
        ArrayList<String> yellow=props.getPropertyOptionsList("YELLOW_CARD_NAME_OPTIONS");
        File file=new File(System.getProperty("user.dir")+"/data/airXML.txt");
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            BufferedWriter bw=new BufferedWriter(fw);
            for(int i=0;i<red.size();i++){
                bw.write("<property_options name="+"\""+red.get(i)+"_AIR2\">");
                bw.newLine();
                bw.write("</property_options>");
                bw.newLine();
                bw.write("<property_options name="+"\""+red.get(i)+"_AIR4\">");
                bw.newLine();
                bw.write("</property_options>");
                bw.newLine();
            }
            for(int i=0;i<green.size();i++){
                bw.write("<property_options name="+"\""+green.get(i)+"_AIR2\">");
                bw.newLine();
                bw.write("</property_options>");
                bw.newLine();
                bw.write("<property_options name="+"\""+green.get(i)+"_AIR4\">");
                bw.newLine();
                bw.write("</property_options>");
                bw.newLine();
            }
            for(int i=0;i<red.size();i++){
                bw.write("<property_options name="+"\""+yellow.get(i)+"_AIR2\">");
                bw.newLine();
                bw.write("</property_options>");
                bw.newLine();
                bw.write("<property_options name="+"\""+yellow.get(i)+"_AIR4\">");
                bw.newLine();
                bw.write("</property_options>");
                bw.newLine();
            }
            bw.close();
            System.out.println("Done");
        } catch (IOException ex) {
            System.out.println("File Does Not Exist");
        }*/
        File toread=new File(System.getProperty("user.dir")+"/data/predes.txt");
        File file=new File(System.getProperty("user.dir")+"/data/descriptions.txt");
        FileWriter fw;
        try {
            Scanner scanner=new Scanner(toread);
            fw = new FileWriter(file);
            BufferedWriter bw=new BufferedWriter(fw);
            while(scanner.hasNext()){
                bw.write("<property_options name="+"\""+scanner.nextLine()+"_D\">");
                bw.newLine();
                bw.write("<option>");
                bw.write(scanner.nextLine());
                bw.write("</option>");
                bw.newLine();
                bw.write("</property_options>");
                bw.newLine();
            }
            bw.close();
            scanner.close();
        } catch (FileNotFoundException ex) {}catch (IOException ex) {}System.out.println("Done");
    }
}
