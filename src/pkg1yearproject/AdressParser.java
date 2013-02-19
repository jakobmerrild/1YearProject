/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1yearproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.*;
/**
 *
 * @author Marianne Merrild
 */
public class AdressParser {
    //TODO Test string, add parameter to parseAdress (String str) later
    //private static String str = "Elbagade i København S";
    
    private static AdressParser instance; //Singleton pattern, getInstance should be called to receive the instance of this class
    private Pattern regexPattern; //The pattern used to parse an Adress.
    
    //An array holding the groupNames
    private static String[] groupNames = new String[]{"roadName",      //index 0
                                                      "number",        //index 1
                                                      "houseLetter",   //index 2
                                                      "floor",         //index 3
                                                      "postCode",      //index 4
                                                      "city"};         //index 5
    /**
     * Creates an instance of this class. A regex is generated for parsing adresses.
     * The regex is partly generated based on data files.
     * @throws IOException If there was a problem reading the data files.
     */
    private AdressParser() throws IOException{
        //Regex based on data read by bReader. Input is files\reverseRoadNameRegEx.txt
        InputStreamReader iReader = new InputStreamReader(new FileInputStream("files" + File.separator + "reverseRoadNameRegEx.txt"), "UTF-8");
        BufferedReader bReader = new BufferedReader(iReader);        
        String roadNameRegex = bReader.readLine();
        
        //Regex based on data read by bReader. Input is files\reverseCityNameRegEx.txt
        iReader = new InputStreamReader(new FileInputStream("files" + File.separator + "reverseCityNameRegEx.txt"), "UTF-8");
        bReader = new BufferedReader(iReader);
        String cityRegex = bReader.readLine();
        bReader.close();
        
        // Regex finds 4 digits which are not preceded or followed by a digit
        String postCodeRegex = "(?<!\\d)\\d{4}(?!\\d)";
        
        // Regex finds 1-3 digits which are not preceded or followed by a digit
        String numberRegex = "(?<!\\d)\\d{1,3}(?![\\d.])";
        
        // Regex finds a letter which is preceded by (1-3 digits followed by 0-1 whitespace)
        // and not followed by one of the chars[a-zæøå]
        String houseLetterRegex = "(?<=\\D\\d{1,3}\\s{0,1})[a-z](?![a-zæøå])";
        
        // Regex finds 1-3 digits which are followed by a dot and aren't preceded by a digit
        // Or st with or without dots
        String floorRegex = "((?<!\\d)\\d{1,3}(?=\\.))|(s\\.?t\\.?)";
        
        /* Make a pattern which looks for either of the above matches.
         * Save each match in an named capturing group based on the names in groupNames[].
         * The index of the groupNames[] should match the index in the return ArrayList for that particular group.
         */
        String patternString = 	"(?<" + groupNames[0] + ">" + roadNameRegex + 	")|" +
                                "(?<" + groupNames[1] + ">" + numberRegex +	")|" +
                                "(?<" + groupNames[2] + ">" + houseLetterRegex +")|" +
                                "(?<" + groupNames[3] + ">" + floorRegex + 	")|" +
                                "(?<" + groupNames[4] + ">" + postCodeRegex + 	")|" +
                                "(?<" + groupNames[5] + ">" + cityRegex + 	")";
          
        regexPattern = Pattern.compile(patternString);
    }
    /**
     * Call to get an instance of the AdressParser class.
     * @return An instance of the AdressParser class, null if no such instance could be made.
     */
    public static AdressParser getInstance(){
        try{
            if(instance == null){ instance = new AdressParser();}
        } catch (IOException e){
            //TODO How to handle the Exception?
        }
        return instance;
    }
    /**
     * Parse an input string to retrieve all the adress information it contains.
     * The results of the parsing will be returned as an Array of ArrayList<String> based on the following indexes:
     * Index 0: An ArrayList<String> with all matches for the regex in reverseRoadNameRegEx.txt
     * Index 1: An ArrayList<String> with all matches for the regex (?<!\\d)\\d{1,3}(?![\\d.])
     * Index 2: An ArrayList<String> with all matches for the regex (?<=\\D\\d{1,3}\\s{0,1})[a-z](?![a-zæøå])
     * Index 3: An ArrayList<String> with all matches for the regex ((?<!\\d)\\d{1,3}(?=\\.))|(s\\.?t\\.?)
     * Index 4: An ArrayList<String> with all matches for the regex (?<!\\d)\\d{4}(?!\\d)
     * Index 5: An ArrayList<String> with all matches for the regex in reverseCityNameRegEg.txt
     * Note: Any of the above ArrayLists could be empty if there was no match found for the given group.
     * @param input The string to be parsed as an adress
     * @return An ArrayList<ArrayList<String>> with the results of the parsing
     */
    public ArrayList<ArrayList<String>> parseAdress(String input){
        
        input = input.toLowerCase();        
        //Create the return array.
        ArrayList<ArrayList<String>> matches = new ArrayList<>(6);
        for(int i = 0; i < groupNames.length; i++){
            matches.add(new ArrayList<String>());
        }
        
        //Create Matcher
        Matcher m = regexPattern.matcher(input);
        
        //So long as the matcher finds a new match, continue
        while(m.find()){
            //Check each group to see if the match was for that group.
            for(int i = 0; i < groupNames.length; i++){
                //Only store the match if it isn't null
                if(m.group(groupNames[i]) != null){
                    //Store it in the list of matches for that group
                    matches.get(i).add(m.group(groupNames[i]));
                }
            }
        }
        
        return matches;
    }
    //TODO remove this when no longer needed.
    /**
     * For debugging purposes, get the group names for the indexes in the result of parseAdress
     * @return A string[] with the group names
     */
    public static String[] getGroupNames(){
        return groupNames;
    }
    
    //
    //
    //    private static String roadNameRegex = initializeRoadNameRegex();
    //
    //    public AdressParser(){
    //
    //    }
    //
    //    public static String getRoadNameRegex(){
    //        return roadNameRegex;
    //    }
    //    /**
    //     * Initialize the roadNameRegex field by reading the road_names.txt file.
    //     * road_names.txt is located in files\road_name.txt
    //     * @return String a regex pattern which contains all road names in DK
    //     */
    //    private static String initializeRoadNameRegex(){
    //        String s = null; // The return string
    //        try {
    //            String fs = File.separator; // The filepath seperator for the platform
    //            String filepath = "files" + fs + "roadNameRegEx.txt"; // The filepath, platform independant.
    //            FileInputStream fileInput = new FileInputStream(filepath); //Create FileInputStream from the file at filepath
    //            InputStreamReader inputReader = new InputStreamReader(fileInput , "UTF-8"); //Create a reader with UTF-8 format
    //            BufferedReader bufferedReader = new BufferedReader(inputReader); //Create buffered reader to read the string
    //            s = bufferedReader.readLine(); // Read the first line
    //            //Close the reader
    //            bufferedReader.close();
    //            //Reader closed
    //            s = s.toLowerCase(); //Make the return string lowercase
    //        } catch (FileNotFoundException ex) {
    //            //For debugging purposes
    //            //System.out.println("File not found");
    //            Logger.getLogger(AdressParser.class.getName()).log(Level.SEVERE, null, ex);
    //
    //        } finally{
    //            return s;
    //        }
    //    }
    //
    //    /**
    //     * Parse a given adress string to split it into smaller components.
    //     * Components are as follows, the number before is the index of the return String[] in which the component is stored
    //     * 0: Street name
    //     * 1: Building number
    //     * 2: Building letter
    //     * 3: Floor
    //     * 4: Postcode
    //     * 5: City
    //     * If a component is not found it's index will be null.
    //     * If no components can be parsed a NaughtyException will be thrown.
    //     * @return String[] A String[] containing one or more of the components
    //     * @throws NaughtyException If the input String contains more than one Postcode or no components could be parsed.
    //     */
    //    public static String[] parseAdress(String str) throws NaughtyException{
    //        //Remove all th, tv and mf(and variations of these) aswell as making the entire String lower case and trimming it.
    //        str = str.replaceAll("((t\\.?(h|v)\\.?)|(m\\.?f\\.?))\\b", "").toLowerCase().trim();
    //        //The String[] to be returned.
    //        String[] arr = new String[6];
    //        //StringBuffer sb = new StringBuffer(); Not used atm.
    //
    //        //Make a default pattern so a Matcher can be made.
    //        Pattern p = Pattern.compile(" ");
    //        //Create the matcher using the input String
    //        Matcher m = p.matcher(str);
    //
    //        arr[0] = findRoadName(m);
    //        arr[1] = findHouseNumber(m);
    //        arr[2] = findHouseLetter(m);
    //        arr[3] = findFloor(m);
    //        arr[4] = findPostCode(m);
    //        arr[5] = findCity(str);
    //
    //        /************** old code ******************************************
    //         * //Check to see if there are more than 1 matches for the Postcode
    //         * //        if(groupCount > 1) {
    //         * //            //Throw an exception if there are.
    //         * //            throw new NaughtyException("More than one postal code is not allowed.");
    //         * //        } else if (groupCount == 1){ //Else check to see if there is a match for the Postcode
    //         * //        if(m.find()){
    //         * //            arr[4] = m.group().replaceAll("\\D", ""); //Save the Postcode at index 4
    //         * //        }
    //         * //Change the pattern, Building number or Floor. A word-boundary followed by 1-3 digits followed by a word-boundary.
    //         * //        p = Pattern.compile("\\b\\d{1,3}\\b");
    //         * //        m.usePattern(p); // Apply the pattern to the matcher
    //         * //        if(m.find(0)){ //Assume that if there is a match the first match is the Building number
    //         * //            arr[1] = m.group().replaceAll("\\D", ""); // Assign the building number to index 1
    //         * //            if(m.find()){ //If there is another match assume it is the Floor
    //         * //                arr[3] = m.group().replaceAll("\\D", ""); // Assign the Floor to index 3
    //         * //            }
    //         * //            //Change the pattern to look for a single letter following a digit with zero or more whitespaces
    //         * //            p = Pattern.compile("\\d\\s*[a-z]\\b");
    //         * //            m.usePattern(p);
    //         * //            if(m.find(0)){ // Look for a match from the beginning
    //         * //                arr[2] = m.group().replaceAll("[^a-z]", ""); //Add the building letter to index 2
    //         * //            }
    //         * //        }
    //         *
    //         * //        p = Pattern.compile("[a-zæøå]*(gade|vej|alle|allé|boulevard|vænget)");
    //         * //        m = p.matcher(sb.toString().trim());
    //         * //        if(m.find()){
    //         * //            arr[0] = m.group();
    //         * //        }
    //         ********************************************************************/
    //
    //        //Check to see if there was atleast one match, return the array if there was
    //        for(String s : arr){
    //            if(s != null){
    //                return arr;
    //            }
    //        }
    //        //Otherwise throw exception
    //        throw new NaughtyException("Couldn't understand your input.\nPlease try again using the following format:"
    //                + "\n<Road name> <Building number> <Building letter> <Floor>, <Post code> <City>");
    //    }
    //
    //    private static String findHouseNumber(Matcher m){
    //        String s = null; // The return string;
    //        //The pattern, Building number or Floor. A non-digit followed by 1-3 digits followed by a non-digit.
    //        Pattern p = Pattern.compile("\\D\\d{1,3}\\D");
    //        m.usePattern(p); // Apply the pattern to the matcher
    //        if(m.find(0)){ //Assume that if there is a match the first match is the Building number
    //            s = m.group().replaceAll("\\D", ""); // Assign the building number s
    //        }
    //        return s;
    //    }
    //
    //    private static String findHouseLetter(Matcher m) {
    //        String s = null; // The return string
    //        //The pattern to look for; a single letter following a digit with zero or more whitespaces
    //        Pattern p = Pattern.compile("\\d\\s*[a-z]\\b");
    //        m.usePattern(p);
    //        if(m.find(0)){ // Look for a match from the beginning
    //            s = m.group().replaceAll("[^a-z]", ""); //Add the building letter to the return string
    //        }
    //        return s;
    //    }
    //
    //    private static String findFloor(Matcher m) {
    //        String s = null; // The return string;
    //        //The pattern, Building number or Floor. A non-digit followed by 1-3 digits followed by a non-digit.
    //        Pattern p = Pattern.compile("\\D\\d{1,3}\\D");
    //        m.usePattern(p); // Apply the pattern to the matcher
    //        if(m.find(0)){ //Assume that if there is a match the first match is the Building number
    //            if(m.find()){ //If there is another match assume it is the Floor
    //                s = m.group().replaceAll("\\D", ""); // Assign the Floor to the return string.
    //            }
    //        }
    //        return s;
    //    }
    //
    //    private static String findPostCode(Matcher m){
    //        String s = null; //The return string
    //        //The pattern to look for, Postcode. A non-digit followed by 4 digits followed by a non-digit
    //        Pattern p = Pattern.compile("\\D\\d{4}\\D");
    //        m.usePattern(p);
    //        if(m.find(0)){
    //            s = m.group().replaceAll("\\D", ""); //Assign the postcode to the return string
    //        }
    //        return s;
    //    }
    //
    //    private static String findCity(String str) throws NaughtyException {
    //        String s; //The return string
    //        String[] arr = str.split("[,\\d]|(\\si\\s)", 2); //split the string at first comma, digit or " i "
    //        //If the length of the array is 1 then I can't deduce which is road and which is city
    //        //TODO Make a better regex so you don't have to throw exception.
    //        if(arr.length == 1){
    //            throw new NaughtyException("You have to use either a digit, a comma or \" i \" to seperate city from road ");
    //        } else {
    //            int index = -1; // The index in the array in which the city is found.
    //            for(int i = 0; i<arr.length; i++){
    //                //Attempt to guess if this part of the split contains the roadname.
    //                if(Pattern.matches("([a-zæøå ]*)(gade|vej|allé|boulevard|vænget|torv|plads)", arr[i])){
    //                    //If it does set cityindex to the opposite of the index which contains the roadname
    //                    switch(i){
    //                        case 0: index = 1;
    //                        break;
    //                        case 1: index = 0;
    //                        break;
    //                        default: break;
    //                    }
    //                }
    //            }
    //            //If couldn't guess which side contained the roadname assume that the "right hand" side contains city
    //            if(index == -1){
    //                index = 1;
    //            }
    //            s = arr[index].replaceAll("\\.", "");//Remove any possible dots
    //            s = s.replaceAll("\\d", ""); // Remove all digits
    //            s = s.replaceAll("^\\s*[a-z]\\b", ""); // Remove any possible house letter
    //            s = s.replaceAll("[^a-zæøå ]", "");//Remove any remaining chars that can't be part of a city in DK
    //            return s.trim();
    //        }
    //    }
    //
    //    private static String findRoadName(Matcher m) throws NaughtyException {
    //        String s = null; // The return string
    //        //The pattern to look for, roadNameRegex
    //        Pattern p = Pattern.compile(roadNameRegex);
    //        m.usePattern(p);
    //        if(m.find(0)){
    //            s = m.group();
    //        }
    //        return s;
    //
    //        /******* OLD CODE *****
    //        String[] arr = str.split("[,\\d]|(\\si\\s)", 2); //split the string at first comma, digit or " i "
    //        //If the length of the array is 1 then I can't deduce which is road and which is city
    //        //TODO Make a better regex so you don't have to throw exception.
    //        if(arr.length == 1){
    //            throw new NaughtyException("You have to use either a digit, a comma or \" i \" to seperate city from road ");
    //        } else {
    //            int index = -1;
    //            for(int i = 0; i<arr.length; i++){
    //                //Attempt to guess if this part of the split contains the roadname.
    //                if(Pattern.matches("([a-zæøå ]*)(gade|vej|allé|boulevard|vænget|torv|plads)", arr[i])){
    //                    index = i;
    //                }
    //            }
    //            if(index == -1){//If a match wasn't found then assume that the "left hand" is roadname
    //                index = 0;
    //            }
    //            s = arr[index].replaceAll("[^a-zæøå ]", "");//Remove any char that can't be part of roadname
    //            return s.trim();
    //        }**********/
//    }
}

