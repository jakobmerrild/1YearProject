/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1yearproject;
import java.util.ArrayList;
/**
 *
 * @author Marianne Merrild
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        String input = "Ole Borchs vej 20 2500 Valby københavn";
 
            ArrayList<ArrayList<String>> output = AdressParser.getInstance().parseAdress(input);
            for(int i = 0; i < output.size(); i++) {
                ArrayList<String> a = output.get(i);
                System.out.print(AdressParser.getGroupNames()[i] + ": ");
                for(String s : a){
                    System.out.print(s + " ");
                }
                System.out.println();
            }

    }
}
