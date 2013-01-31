/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1yearproject;

import java.util.regex.*;

/**
 *
 * @author Marianne Merrild
 */
public class AdressParser {
    //TODO Test string, add parameter to parseAdress (String str) later
    private static String str = "Elbagade 53, 2300 København S";
    
    /**
     * Parse a given adress string to split it into smaller components.
     * Components are as follows, the number before is the index of the return String[] in which the component is stored
     * 0: Street name
     * 1: Building number
     * 2: Building letter
     * 3: Floor
     * 4: Postcode
     * 5: City
     * If a component is not found it's index will be null.
     * If no components can be parsed a NaughtyException will be thrown.
     * @return String[] A String[] containing one or more of the components
     * @throws NaughtyException If the input String contains more than one Postcode or no components could be parsed.
     */
    public static String[] parseAdress() throws NaughtyException{
        //Remove all th, tv and mf(and variations of these) aswell as making the entire String lower case and trimming it.
        str = str.replaceAll("(t\\.?(h|v)\\.?)|(m\\.?f\\.?)\\b", "").toLowerCase().trim();
        //The String[] to be returned.
        String[] arr = new String[6];
        //StringBuffer sb = new StringBuffer(); Not used atm.
        
        //The first pattern to look for, Postcode. A word-boundary followed by 4 digits followed by a word-boundary
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        //Create the matcher using the input String
        Matcher m = p.matcher(str);
        //Check to see if there are more than 1 matches for the Postcode
        //        if(groupCount > 1) {
        //            //Throw an exception if there are.
        //            throw new NaughtyException("More than one postal code is not allowed.");
        //        } else if (groupCount == 1){ //Else check to see if there is a match for the Postcode
        if(m.find()){
            arr[4] = m.group().replaceAll("\\D", ""); //Save the Postcode at index 4
        }
        //Change the pattern, Building number or Floor. A word-boundary followed by 1-3 digits followed by a word-boundary.
        p = Pattern.compile("\\b\\d{1,3}\\b");
        m.usePattern(p); // Apply the pattern to the matcher
        if(m.find(0)){ //Assume that if there is a match the first match is the Building number
            arr[1] = m.group().replaceAll("\\D", ""); // Assign the building number to index 1
            if(m.find()){ //If there is another match assume it is the Floor
                arr[3] = m.group().replaceAll("\\D", ""); // Assign the Floor to index 3
            }
            //Change the pattern to look for a single letter following a digit with zero or more whitespaces
            p = Pattern.compile("\\d\\s*[a-z]\\b");
            m.usePattern(p);
            if(m.find(0)){ // Look for a match from the beginning
                arr[2] = m.group().replaceAll("[^a-z]", ""); //Add the building letter to index 2
            }
        }
        
        //        p = Pattern.compile("[a-zæøå]*(gade|vej|alle|allé|boulevard|vænget)");
        //        m = p.matcher(sb.toString().trim());
        //        if(m.find()){
        //            arr[0] = m.group();
        //        }
        String[] temparr = str.split(",|(\\si\\s)", 2);
        for(int i = 0; i < temparr.length; i++){
            String s = temparr[i];
            if(s.matches("([a-zæøå ]*)gade|vej|alle|allé|boulevard|vænget|torv|plads") ||
                    s.matches("\\b\\d{1,3}\\b")){
                arr[0] = s.replaceAll("[^(a-zæøå )]", "");                
            } else if(s.matches("\\b\\d{4}\\b")){
                s = s.replaceAll("[^(a-zæøå )]", "");
                if(!s.isEmpty()){
                    arr[5] = s;
                }
            }
        }
        if(arr[0] == null && arr[5] == null){
            arr[0] = temparr[0];
            arr[5] = temparr[1];
        }
        for(String s : arr){
            if(s != null){
                return arr;
            }
        }
        throw new NaughtyException("Couldn't understand your input.\nPlease try again using the following format:"
                + "\n<Road name> <Building number> <Building letter> <Floor>, <Post code> <City>");
    }
    
    

}
