/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.HexToDec;

import Server.exceptions.StringContainsCharacters;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Bartosz Kawalkiewicz
 * @version 3.0
 */
public class ConversionModel {

    public class History {

        private Date dateOfConversion;
        private String firstValue;
        private String secondValue;

        History() {
        }

        ;
        History(Date date, String fVal, String sVal) {
            this.dateOfConversion = date;
            this.firstValue = fVal;
            this.secondValue = sVal;
        }

        /**
         * getter for Date 
         * @return Date of conversion
         */
        public Date getDate() {
            return this.dateOfConversion;
        }

        /**
         * sets date 
         * @param date
         */
        public void setDate(Date date) {
            this.dateOfConversion = date;
        }

        /**
         * gets first value of conversion
         * @return firstValue
         */
        public String getFirstValue() {
            return this.firstValue;
        }

        /**
         * sets first value 
         * @param hexValue
         */
        public void setFirstValue(String hexValue) {
            this.firstValue = hexValue;
        }

        /**
         * gets second value of conversion
         * @return secondValue
         */
        public String getSecondValue() {
            return this.secondValue;
        }

        /**
         * sets second value of conversion
         * @param decValue
         */
        public void setSecondValue(String decValue) {
            this.secondValue = decValue;
        }

        /**
         * setter for all fields
         * @param date
         * @param hexValue
         * @param decValue
         */
        public void setAll(Date date, String hexValue, String decValue) {
            this.dateOfConversion = date;
            this.firstValue = hexValue;
            this.secondValue = decValue;
        }

    }
        /**
         * constructor for model which also creates empty array list
         */
    ConversionModel() {
        history = new ArrayList<>();
    }

    private String value;

    private ArrayList<History> history;
    
    /**
     * getter for the first value field of last added element of list 
     * @return last element first value
     */
    public String getListLastElementFirstValue()
    {
         if (history != null && !history.isEmpty()) 
         {
            return history.get(history.size()-1).getFirstValue();
         }
         else return "";
    }
    
    /**
     * getter for the second value field of last added element of list 
     * @return last element second value
     */
    public String getListLastElementSecondValue()
    {
         if (history != null && !history.isEmpty()) 
         {
            return history.get(history.size()-1).getSecondValue();
         }
         else return "";
    }
    
    /**
     * getter for the date value field of last added element of list 
     * @return last element date value
     */
    public Date getListLastElementDate()
    {
         if (history != null && !history.isEmpty()) 
         {
            return history.get(history.size()-1).getDate();
         }
         else return null;
    }

    /**
     * getter for the list, used in testing
     * @return list of conversions
     */
    public ArrayList<History> getList()
    {
         return this.history;
    }

    /**
     * method used to format String and parse it to Date class format 
     * @param toBeFormated string to be formated
     * @return string parsed to a Date class format 
     * @throws ParseException if the string is invalid
     */
    public Date formatDate(String toBeFormated) throws ParseException 
    {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date date = dateFormat.parse(toBeFormated);
        return date;
    }

    /**
     * gets list from file, and adds to list
     * @param filename filename to be passed 
     * @throws FileNotFoundException if file was not found
     * @throws ParseException if something bad happens in file
     */
    public void getListFromFile(String filename) throws FileNotFoundException, ParseException 
    {
        if("".equals(filename)) throw new FileNotFoundException();
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String tmp = sc.nextLine();
            if(tmp.equals("\n")) return;
            else
            {
            String[] values = tmp.split(",");
            Date tmpDate = this.formatDate(values[0]);
            String tmpfVal = values[1];
            String tmpsVal = values[2];
            history.add(new History(tmpDate, tmpfVal, tmpsVal));
            }
        }

    }

    /**
     * adds to list
     * @param date date of conversion
     * @param fVal first value of conversion 
     * @param sVal second value of conversion 
     */
    public void addToList(Date date, String fVal, String sVal) {
        history.add(new History(date, fVal, sVal));
    }

    /**
     * appends new value to file 
     * @param date date to be added 
     * @param fVal first value of conversion 
     * @param sVal second value of conversion 
     * @throws IOException pretty much it shouldnt be thrown 
     */
    public void appendToFile(Date date, String fVal, String sVal) throws IOException {
        File file = new File("HIS.txt");
        try (FileWriter fr = new FileWriter(file, true)) {
            BufferedWriter br = new BufferedWriter(fr);
            br.write(date + "," + fVal + "," + sVal + "\n");
            br.close();
        }
    }

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
     * 
     */
    public void calculate(String method, String value) throws StringContainsCharacters, IOException {
        switch (method) {
            case "hex to dec":
                calculateHexToDec(value);
                break;
            case "dec to hex":
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
    private void calculateHexToDec(String value) throws StringContainsCharacters, IOException {
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
        this.addToList(today, value, Integer.toString(dec_val));
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
    private void calculateDecToHex(String value) throws StringContainsCharacters, IOException,IllegalArgumentException {
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
        this.addToList(today, value, Integer.toHexString(tmp).toUpperCase());
        this.value = Integer.toHexString(tmp).toUpperCase();
    }
}
