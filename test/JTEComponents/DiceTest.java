/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JTEComponents;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Luming
 */
public class DiceTest {
    
    public DiceTest() {
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
     * Test of Roll method, of class Dice.
     */

    @Test
    public void testRoll(){
        Dice _dice=new Dice(null);
        int Result=_dice.getRoll();
        System.out.println("Dice Roll: "+Result);
        assertTrue("That Sucks",1<=Result&&Result<=6);
    }
    
}
