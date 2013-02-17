/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1yearproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author Marianne Merrild
 */
public class WriteRegex {
    
    private static String filePath = "files" + File.separator;

    
    public static void writeReverseCityRegEx() throws FileNotFoundException, UnsupportedEncodingException, IOException{
        String input = filePath + "citynames.txt";
        FileInputStream fileInput = new FileInputStream(input); //Create FileInputStream from the file at filepath
        InputStreamReader inputReader = new InputStreamReader(fileInput , "UTF-8"); //Create a reader with UTF-8 format
        BufferedReader bufferedReader = new BufferedReader(inputReader); //Create buffered reader to read the string
        
        String output = filePath + "reverseCityNameRegex.txt";
        FileOutputStream fileOutput = new FileOutputStream(output);
        OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput, "UTF-8");
        BufferedWriter bWriter = new BufferedWriter(outputWriter);
        
        ArrayList<String> cityNames = new ArrayList<>();
        
        String bs = bufferedReader.readLine();
        
        while(bs != null){
            cityNames.add("(" + bs + ")");
            bs = bufferedReader.readLine();
        }
        
        String outputString = "";
        for(int i = cityNames.size()-1; i>=0; i--){
            if(i != 0){
            outputString += cityNames.get(i) + "|";
            }
            else{
            outputString += cityNames.get(i);
            }
        }
        
        bWriter.write(outputString.toLowerCase());
        
        bufferedReader.close();

        bWriter.close();
    }
}
