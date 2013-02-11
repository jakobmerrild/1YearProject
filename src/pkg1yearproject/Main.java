/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1yearproject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marianne Merrild
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            String[] arr = AdressParser.parseAdress("elbagade 4 b 2. th 5000 Odense");
            for(String s : arr) {
                System.out.println(s);
            }
            System.out.println(AdressParser.getRoadNameRegex());
        } catch (NaughtyException ex) {
            System.out.println(ex);
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
