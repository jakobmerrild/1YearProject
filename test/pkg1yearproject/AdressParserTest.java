/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1yearproject;

import java.nio.charset.Charset;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jakob Merrild
 */
public class AdressParserTest {
    
    public AdressParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of parseAdress method, of class AdressParser.
     */
    @Test
    public void testParseAdress() throws Exception {
        System.out.println("parseAdress");
        String[] inputs = new String[]{"Degnebakken",
                                       "Alsædtægten 4 b 2. th 5000 Odense", 
                                       "",
                                       "Elbagade i København S",   
                                       "København S, Elbagade"};
        String[][] expResults = new String[][]{
                                    {"degnebakken", null, null, null, null, null},
                                    {"alsædtægten", "4", "b", "2", "5000", "odense"},
                                    {null, null, null, null, null, null},
                                    {"elbagade", null, null, null, null, "københavn s"},
                                    {"elbagade", null, null, null, null, "københavn s"}
                                    };
        for (int i = 0; i < 5; i++) {
            try{
                String[] result = AdressParser.parseAdress(inputs[i]);
                assertArrayEquals(expResults[i], result);
            } catch(NaughtyException e){
                System.out.println("Test case: " + i + " - threw NaughtyException - " + e.getMessage());
                continue;
            }

        }
    }
}
