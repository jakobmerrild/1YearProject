/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1yearproject;

import java.nio.charset.Charset;
import java.util.ArrayList;
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
        
        //TODO review test cases.
        String[] inputs = new String[]{"Degnebakken",
                                       "Alsædtægten 4 b 2. th 5000 Odense", 
                                       "",
                                       "Elbagade København S",   
                                       "København S, Elbagade"};
        
        ArrayList<ArrayList<ArrayList<String>>> expResults = new ArrayList<>(inputs.length);
        
        ArrayList<ArrayList<String>> result1 = new ArrayList<>(6),
                                     result2 = new ArrayList<>(6),
                                     result3 = new ArrayList<>(6),
                                     result4 = new ArrayList<>(6),
                                     result5 = new ArrayList<>(6);
        
        ArrayList<String>   result1_0 = new ArrayList<>(),
                            result1_1 = new ArrayList<>(),
                            result1_2 = new ArrayList<>(),
                            result1_3 = new ArrayList<>(),
                            result1_4 = new ArrayList<>(),
                            result1_5 = new ArrayList<>(),
                            result2_0 = new ArrayList<>(),
                            result2_1 = new ArrayList<>(),
                            result2_2 = new ArrayList<>(),
                            result2_3 = new ArrayList<>(),
                            result2_4 = new ArrayList<>(),
                            result2_5 = new ArrayList<>(),
                            result3_0 = new ArrayList<>(),
                            result3_1 = new ArrayList<>(),
                            result3_2 = new ArrayList<>(),
                            result3_3 = new ArrayList<>(),
                            result3_4 = new ArrayList<>(),
                            result3_5 = new ArrayList<>(),
                            result4_0 = new ArrayList<>(),
                            result4_1 = new ArrayList<>(),
                            result4_2 = new ArrayList<>(),
                            result4_3 = new ArrayList<>(),
                            result4_4 = new ArrayList<>(),
                            result4_5 = new ArrayList<>(),
                            result5_0 = new ArrayList<>(),
                            result5_1 = new ArrayList<>(),
                            result5_2 = new ArrayList<>(),
                            result5_3 = new ArrayList<>(),
                            result5_4 = new ArrayList<>(),
                            result5_5 = new ArrayList<>();
        
        result1_0.add("degnebakken");
        
        result2_0.add("alsædtægten"); result2_1.add("4"); result2_2.add("b"); result2_3.add("2"); result2_4.add("5000"); result2_5.add("odense");
        
        result4_0.add("elbagade"); result4_5.add("københavn s");
        
        result5_0.add("elbagade"); result5_5.add("københavn s");
        
        result1.add(result1_0); result1.add(result1_1); result1.add(result1_2); result1.add(result1_3); result1.add(result1_4); result1.add(result1_5);
        result2.add(result2_0); result2.add(result2_1); result2.add(result2_2); result2.add(result2_3); result2.add(result2_4); result2.add(result2_5);
        result3.add(result3_0); result3.add(result3_1); result3.add(result3_2); result3.add(result3_3); result3.add(result3_4); result3.add(result3_5);
        result4.add(result4_0); result4.add(result4_1); result4.add(result4_2); result4.add(result4_3); result4.add(result4_4); result4.add(result4_5);
        result5.add(result5_0); result5.add(result5_1); result5.add(result5_2); result5.add(result5_3); result5.add(result5_4); result5.add(result5_5);
        
        expResults.add(result1);
        expResults.add(result2);
        expResults.add(result3);
        expResults.add(result4);
        expResults.add(result5);
//        String[][] expResults = new String[][]{
//                                    {"degnebakken", null, null, null, null, null},
//                                    {"alsædtægten", "4", "b", "2", "5000", "odense"},
//                                    {null, null, null, null, null, null},
//                                    {"elbagade", null, null, null, null, "københavn s"},
//                                    {"elbagade", null, null, null, null, "københavn s"}
//                                    };
       for (int i = 0; i < 5; i++) {

               ArrayList<ArrayList<String>> result = AdressParser.getInstance().parseAdress(inputs[i]);
               assertEquals(expResults.get(i), result);


        }
  }
}
