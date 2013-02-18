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
        String input = "Alsædtægten 4 b 2. th 5000 Odense";
 
            // TODO code application logic here
            for(ArrayList<String> a : AdressParser.getInstance().parseAdress(input)) {
                for(String s : a){
                    System.out.println(s);
                }
            }

    }
}
