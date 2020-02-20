/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Bartosz Kawalkiewicz
 * @version 6.0
 */
public class ConversionModel {
  
    public ConversionModel() {
    }

    private String value;

    

    /**
     * setter for value to be converted
     *
     * @param param is the String
     */
    public void setCompute(String param) {
        value = param;
    }

    /**
     *
     * @return String value
     */
    public String getComputee() {
        return this.value;
    }

    /**
     * method that chooses conversion method based on string containing it
     *
     * @param value value to be converted
     * @param method type of method
     * @throws StringContainsCharacters if illegal characters are available
     * @throws java.io.IOException if parsing gone wrong 
     * @throws java.sql.SQLException 
     * 
     */
    public void calculate(String method, String value) throws StringContainsCharacters, IOException, SQLException {
        switch (method) {
            case "hex_to_dec":
                calculateHexToDec(value);
                break;
            case "dec_to_hex":
                calculateDecToHex(value);
                break;
            default:
                throw new UnsupportedOperationException("this operation does not exists");
        }

    }

    /**
     * Process the calculation from Hexadecimal format to decimal
     *
     * @param value to be converted
     * @throws StringContainsCharacters if illegal characters are available in
     * entered String
     * @throws java.io.IOException if parsing gone wrong 
     */
    private void calculateHexToDec(String value) throws StringContainsCharacters, IOException, SQLException {
        Date today = new Date();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Entered value is empty");
        }
        int len = value.length();
        int base = 1;
        int dec_val = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (value.charAt(i) >= '0' && value.charAt(i) <= '9') {
                dec_val += (value.charAt(i) - 48) * base;
                base = base * 16;
            } else if (value.charAt(i) >= 'A' && value.charAt(i) <= 'F') {
                dec_val += (value.charAt(i) - 55) * base;
                base = base * 16;
            } else {
                throw new StringContainsCharacters("Entered value has illegal characters");
            }
        }    
        this.value = Integer.toString(dec_val);
    }

    /**
     * Process the calculation from decimal format to hexadecimal
     *
     * @param value to be converted
     * @throws StringContainsCharacters if illegal characters are available in
     * entered String
     * @throws java.io.IOException if parsing gone wrong 
     */
    private void calculateDecToHex(String value) throws StringContainsCharacters, IOException,IllegalArgumentException, SQLException {
        Date today = new Date();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Entered value is empty");
        }
        int len = value.length();
        for (int i = 0; i < len; i++) {
            if (value.charAt(i) < '0' || value.charAt(i) > '9') {
                throw new StringContainsCharacters("Entered value has illegal characters");
            }
        }
        int tmp = Integer.parseInt(value);
        this.value = Integer.toHexString(tmp).toUpperCase();
    }
}
