/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1yearproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author Marianne Merrild
 */
public class AdressParser {
    //TODO Test string, add parameter to parseAdress (String str) later
    //private static String str = "Elbagade i København S";
    
    private static String roadNameRegex = initializeRoadNameRegex();
    
    public AdressParser(){
        
    }
    
    public static String getRoadNameRegex(){
        return roadNameRegex;
    }
    /**
     * Initialize the roadNameRegex field by reading the road_names.txt file.
     * road_names.txt is located in files\road_name.txt
     * @return String a regex pattern which contains all road names in DK
     */
    private static String initializeRoadNameRegex(){
        String s = null; // The return string
        try {
            String fs = File.separator; // The filepath seperator for the platform
            String filepath = "files" + fs + "roadNameRegEx.txt"; // The filepath, platform independant.
            FileInputStream fileInput = new FileInputStream(filepath); //Create FileInputStream from the file at filepath
            InputStreamReader inputReader = new InputStreamReader(fileInput , "UTF-8"); //Create a reader with UTF-8 format
            BufferedReader bufferedReader = new BufferedReader(inputReader); //Create buffered reader to read the string
            s = bufferedReader.readLine(); // Read the first line
            //Close the reader
            fileInput.close();
            inputReader.close();
            bufferedReader.close();
            //Reader closed
            s = s.toLowerCase(); //Make the return string lowercase
        } catch (FileNotFoundException ex) {
            //For debugging purposes
            //System.out.println("File not found");
            Logger.getLogger(AdressParser.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally{
            return s;
        }
    }
    
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
    public static String[] parseAdress(String str) throws NaughtyException{
        //Remove all th, tv and mf(and variations of these) aswell as making the entire String lower case and trimming it.
        str = str.replaceAll("((t\\.?(h|v)\\.?)|(m\\.?f\\.?))\\b", "").toLowerCase().trim();
        //The String[] to be returned.
        String[] arr = new String[6];
        //StringBuffer sb = new StringBuffer(); Not used atm.
        
        //Make a default pattern so a Matcher can be made.
        Pattern p = Pattern.compile(" ");
        //Create the matcher using the input String
        Matcher m = p.matcher(str);
        
        arr[0] = findRoadName(m);
        arr[1] = findHouseNumber(m);
        arr[2] = findHouseLetter(m);
        arr[3] = findFloor(m);
        arr[4] = findPostCode(m);
        arr[5] = findCity(str);
        
        /************** old code ******************************************
         * //Check to see if there are more than 1 matches for the Postcode
         * //        if(groupCount > 1) {
         * //            //Throw an exception if there are.
         * //            throw new NaughtyException("More than one postal code is not allowed.");
         * //        } else if (groupCount == 1){ //Else check to see if there is a match for the Postcode
         * //        if(m.find()){
         * //            arr[4] = m.group().replaceAll("\\D", ""); //Save the Postcode at index 4
         * //        }
         * //Change the pattern, Building number or Floor. A word-boundary followed by 1-3 digits followed by a word-boundary.
         * //        p = Pattern.compile("\\b\\d{1,3}\\b");
         * //        m.usePattern(p); // Apply the pattern to the matcher
         * //        if(m.find(0)){ //Assume that if there is a match the first match is the Building number
         * //            arr[1] = m.group().replaceAll("\\D", ""); // Assign the building number to index 1
         * //            if(m.find()){ //If there is another match assume it is the Floor
         * //                arr[3] = m.group().replaceAll("\\D", ""); // Assign the Floor to index 3
         * //            }
         * //            //Change the pattern to look for a single letter following a digit with zero or more whitespaces
         * //            p = Pattern.compile("\\d\\s*[a-z]\\b");
         * //            m.usePattern(p);
         * //            if(m.find(0)){ // Look for a match from the beginning
         * //                arr[2] = m.group().replaceAll("[^a-z]", ""); //Add the building letter to index 2
         * //            }
         * //        }
         * 
         * //        p = Pattern.compile("[a-zæøå]*(gade|vej|alle|allé|boulevard|vænget)");
         * //        m = p.matcher(sb.toString().trim());
         * //        if(m.find()){
         * //            arr[0] = m.group();
         * //        }
         ********************************************************************/
        
        //Check to see if there was atleast one match, return the array if there was
        for(String s : arr){
            if(s != null){
                return arr;
            }
        }
        //Otherwise throw exception
        throw new NaughtyException("Couldn't understand your input.\nPlease try again using the following format:"
                + "\n<Road name> <Building number> <Building letter> <Floor>, <Post code> <City>");
    }
    
    private static String findHouseNumber(Matcher m){
        String s = null; // The return string;
        //The pattern, Building number or Floor. A non-digit followed by 1-3 digits followed by a non-digit.
        Pattern p = Pattern.compile("\\D\\d{1,3}\\D");
        m.usePattern(p); // Apply the pattern to the matcher
        if(m.find(0)){ //Assume that if there is a match the first match is the Building number
            s = m.group().replaceAll("\\D", ""); // Assign the building number s
        }
        return s;
    }
    
    private static String findHouseLetter(Matcher m) {
        String s = null; // The return string
        //The pattern to look for; a single letter following a digit with zero or more whitespaces
        Pattern p = Pattern.compile("\\d\\s*[a-z]\\b");
        m.usePattern(p);
        if(m.find(0)){ // Look for a match from the beginning
            s = m.group().replaceAll("[^a-z]", ""); //Add the building letter to the return string
        }
        return s;
    }
    
    private static String findFloor(Matcher m) {
        String s = null; // The return string;
        //The pattern, Building number or Floor. A non-digit followed by 1-3 digits followed by a non-digit.
        Pattern p = Pattern.compile("\\D\\d{1,3}\\D");
        m.usePattern(p); // Apply the pattern to the matcher
        if(m.find(0)){ //Assume that if there is a match the first match is the Building number
            if(m.find()){ //If there is another match assume it is the Floor
                s = m.group().replaceAll("\\D", ""); // Assign the Floor to the return string.
            }
        }
        return s;
    }
    
    private static String findPostCode(Matcher m){
        String s = null; //The return string
        //The pattern to look for, Postcode. A non-digit followed by 4 digits followed by a non-digit
        Pattern p = Pattern.compile("\\D\\d{4}\\D");
        m.usePattern(p);
        if(m.find(0)){
            s = m.group().replaceAll("\\D", ""); //Assign the postcode to the return string
        }
        return s;
    }
    
    private static String findCity(String str) throws NaughtyException {
        String s; //The return string
        String[] arr = str.split("[,\\d]|(\\si\\s)", 2); //split the string at first comma, digit or " i "
        //If the length of the array is 1 then I can't deduce which is road and which is city
        //TODO Make a better regex so you don't have to throw exception.
        if(arr.length == 1){
            throw new NaughtyException("You have to use either a digit, a comma or \" i \" to seperate city from road ");
        } else {
            int index = -1; // The index in the array in which the city is found.
            for(int i = 0; i<arr.length; i++){
                //Attempt to guess if this part of the split contains the roadname.
                if(Pattern.matches("([a-zæøå ]*)(gade|vej|allé|boulevard|vænget|torv|plads)", arr[i])){
                    //If it does set cityindex to the opposite of the index which contains the roadname
                    switch(i){
                        case 0: index = 1;
                        break;
                        case 1: index = 0;
                        break;
                        default: break;
                    }
                }
            }
            //If couldn't guess which side contained the roadname assume that the "right hand" side contains city
            if(index == -1){
                index = 1;
            }
            s = arr[index].replaceAll("\\.", "");//Remove any possible dots
            s = s.replaceAll("^\\s*[a-z]", ""); // Remove any possible house letter
            s = s.replaceAll("[^a-zæøå ]", "");//Remove any remaining chars that can't be part of a city in DK
            return s.trim();
        }
    }
    
    private static String findRoadName(Matcher m) throws NaughtyException {
        String s = null; // The return string
        //The pattern to look for, roadNameRegex
        Pattern p = Pattern.compile(roadNameRegex);
        m.usePattern(p);
        if(m.find(0)){
            s = m.group();
        }
        return s;
        
        /******* OLD CODE *****
        String[] arr = str.split("[,\\d]|(\\si\\s)", 2); //split the string at first comma, digit or " i "
        //If the length of the array is 1 then I can't deduce which is road and which is city
        //TODO Make a better regex so you don't have to throw exception.
        if(arr.length == 1){
            throw new NaughtyException("You have to use either a digit, a comma or \" i \" to seperate city from road ");
        } else {
            int index = -1;
            for(int i = 0; i<arr.length; i++){
                //Attempt to guess if this part of the split contains the roadname.
                if(Pattern.matches("([a-zæøå ]*)(gade|vej|allé|boulevard|vænget|torv|plads)", arr[i])){
                    index = i;
                }
            }
            if(index == -1){//If a match wasn't found then assume that the "left hand" is roadname
                index = 0;
            }
            s = arr[index].replaceAll("[^a-zæøå ]", "");//Remove any char that can't be part of roadname
            return s.trim();
        }**********/
    } 
}

