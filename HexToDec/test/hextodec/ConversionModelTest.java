/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hextodec;

import exceptions.StringContainsCharacters;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Bartosz Kawalkiewicz
 * @version 2.0
 */
public class ConversionModelTest {
    
    public ConversionModelTest() {
    }
    /**
     * Test of formatDate method, of class ConversionModel.
     */
    @Test(expected = Exception.class)
    public void testFormatDate() throws Exception {
        String toBeFormated = "Tue Jan 07 14:57:30 CET 2020";
        ConversionModel instance = new ConversionModel();
        String expResult = "Tue Jan 07 14:57:30 CET 2020";
        Date result = instance.formatDate(toBeFormated);
        String resultString = result.toString();
        assertEquals(expResult, resultString);
        toBeFormated = "";
        Date expected = null;
        assertEquals(instance.formatDate(toBeFormated), expected);
    }

    /**
     * Test of getListFromFile method, of class ConversionModel for no file exception
     * @throws java.io.FileNotFoundException
     * @throws java.text.ParseException
     */
    @Test(expected = FileNotFoundException.class)
    public void testGetListFromFileNoFile() throws FileNotFoundException,ParseException {
        ConversionModel instance = new ConversionModel();
            instance.getListFromFile("");
            fail();
    }
    
     /**
     * Test of getListFromFile method, of class ConversionModel for bad file which results in ParseException
     * @throws java.io.FileNotFoundException
     * @throws java.text.ParseException
     */
    @Test(expected = ParseException.class)
    public void testGetListFromFileBadFile() throws FileNotFoundException,ParseException {
        ConversionModel instance = new ConversionModel();

            instance.getListFromFile("BadFile.txt");
            fail();
    }
    
  /**
     * Test of getListFromFile method, of class ConversionModel for good file
     * @throws java.io.FileNotFoundException
     * @throws java.text.ParseException
     */
    @Test
    public void testGetListFromFileGoodFile() throws FileNotFoundException,ParseException {
        ConversionModel instance = new ConversionModel();
        instance.getListFromFile("HIS.txt");
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
     * Test of appendToFile method, of class ConversionModel. Tests appending to file
     */
    @Test
    public void testAppendToFile() throws Exception {
        Date date = new Date();
        String fVal = "10";
        String sVal = "16";
        ConversionModel instance = new ConversionModel();
        instance.appendToFile(date, fVal, sVal);
    }

    

    /**
     * Test of calculate method, of class ConversionModel. Tests whether the value calculated and expected are equal 
     * @throws java.lang.Exception
     * throws multiple exceptions tested below
     */
    @Test
    public void testCalculate() throws Exception {
        String value = "10";
        String method = "Dec To Hex";
        String expectedValue = "A";
        ConversionModel instance = new ConversionModel();
        instance.calculate(value, method);
        assertEquals(instance.getComputee(),expectedValue);
        value = "10";
        method = "Hex To Dec";
        expectedValue="16";
        instance.calculate(value, method);
        assertEquals(instance.getComputee(),expectedValue);
    }
    
    /**
     * Test for invalid hexadecimal string which should result in StringContainsCharacters exception
     * @throws StringContainsCharacters
     */
    @Test(expected = StringContainsCharacters.class)
    public void testCalculateForInvalidHexadecimalString() throws Exception {
        String value = ";24";
        String method = "Hex To Dec";
        ConversionModel instance = new ConversionModel();
        instance.calculate(value, method);
        fail();
    }
    
    /**
     *  Tests for invalid decimal string which should result in StringContainsCharacters exception
     * @throws StringContainsCharacters
     */
    @Test(expected = StringContainsCharacters.class)
    public void testCalculateForInvalidDecimalString() throws Exception {
        String value = "A1";
        String method = "Dec To Hex";
        ConversionModel instance = new ConversionModel();
        instance.calculate(value, method);
        fail();
    }
    
    /**
     * test for empty string which should result in IllegalArgumentException 
     * @throws IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateForEmptyString() throws Exception {
        String value = "";
        String method = "Hex To Dec";
        ConversionModel instance = new ConversionModel();
        instance.calculate(value, method);
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


