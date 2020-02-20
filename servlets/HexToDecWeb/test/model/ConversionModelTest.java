/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Bartosz Kawalkiewicz
 * @version 3.0
 */
public class ConversionModelTest {
    
    public ConversionModelTest() {
    }
    
    /**
     * Test of addToList method, of class ConversionModel. Tests adding to list, list sizes and equality of elements 
     */
     @Test
    public void testAddToList(){
        Date date = new Date();
        String fVal = "";
        String sVal = "";
        
        ConversionModel instance1 = new ConversionModel();
        ConversionModel instance2 = new ConversionModel();
        assertEquals(0, instance1.getList().size());
        assertEquals(0, instance2.getList().size());
        instance1.addToList(date, fVal, sVal);
        assertEquals(1, instance1.getList().size());
        instance2.addToList(date, fVal, sVal);
        assertEquals(instance2.getList().size(), instance1.getList().size());
        assertEquals(instance2.getList().get(0).getFirstValue(),instance1.getList().get(0).getFirstValue());
        assertEquals(instance2.getList().get(0).getSecondValue(),instance1.getList().get(0).getSecondValue());
        assertEquals(instance2.getList().get(0).getDate(),instance1.getList().get(0).getDate());
        instance1.getList().get(0).setDate(null);
        instance2.getList().get(0).setDate(null);
        assertEquals(instance2.getList().get(0).getDate(),instance1.getList().get(0).getDate());
        instance1.getList().get(0).setFirstValue("aaa");
        assertNotEquals(instance2.getList().get(0).getFirstValue(),instance1.getList().get(0).getFirstValue());
        instance1.getList().remove(0);
        assertNotEquals(instance2.getList().size(), instance1.getList().size());
    }


    

    /**
     * Test of calculate method, of class ConversionModel. Tests whether the value calculated and expected are equal 
     * @throws java.lang.Exception
     * throws multiple exceptions tested below
     */
    @Test
    public void testCalculate() throws Exception {
        String value = "10";
        String method = "dec_to_hex";
        String expectedValue = "A";
        ConversionModel instance = new ConversionModel();
        instance.calculate(method, value);
        assertEquals(instance.getComputee(),expectedValue);
        value = "10";
        method = "hex_to_dec";
        expectedValue="16";
        instance.calculate(method, value);
        assertEquals(instance.getComputee(),expectedValue);
    }
    
    /**
     * Test for invalid hexadecimal string which should result in StringContainsCharacters exception
     * @throws StringContainsCharacters
     */
    @Test(expected = StringContainsCharacters.class)
    public void testCalculateForInvalidHexadecimalString() throws Exception {
        String value = ";24";
        String method = "hex_to_dec";
        ConversionModel instance = new ConversionModel();
        instance.calculate(method, value);
        fail();
    }
    
    /**
     *  Tests for invalid decimal string which should result in StringContainsCharacters exception
     * @throws StringContainsCharacters
     */
    @Test(expected = StringContainsCharacters.class)
    public void testCalculateForInvalidDecimalString() throws Exception {
        String value = "A1";
        String method = "dec_to_hex";
        ConversionModel instance = new ConversionModel();
        instance.calculate(method, value);
        fail();
    }
    
    /**
     * test for empty string which should result in IllegalArgumentException 
     * @throws IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateForEmptyString() throws Exception {
        String value = "";
        String method = "hex_to_dec";
        ConversionModel instance = new ConversionModel();
        instance.calculate(method, value);
        fail();
    }
    
    /**
     * test for empty method string which should result in UnsupportedOperationException
     * @throws UnsupportedOperationException
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testCalculateForUnsupportedOperation() throws Exception {
        String value = "";
        String method = "";
        ConversionModel instance = new ConversionModel();
        instance.calculate(value, method);
        fail();
    }

  
    
}


