/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.HexToDec;

//import Server.exceptions;
import Server.exceptions.StringContainsCharacters;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import javafx.scene.control.Alert;

/**
 *
 * @author Bartosz Kawalkiewicz
 * @version 3.0
 */
public class ConversionController {
    
     public ConversionController()
    {
        this.model = new ConversionModel();
        initializeListFromFile();
    }
    
     /**
      *
      *ConversionModel object 
    */
    private ConversionModel model;
   

    
    /**
 *
 * does the calculation and returns calculated number
     * @param params 
     * @return  calculated number
 */
    public String calculate(String [] params)
    {
        
            try
            {
                this.model.calculate(params[0], params[1]);
               // this.view.getTextFieldResult().setText(this.model.getComputee());
            }
            catch(StringContainsCharacters | IllegalArgumentException | UnsupportedOperationException| IOException  ex)
            {
                 return"EXCEPTION";
            }
            try
            {
                this.model.appendToFile(this.model.getListLastElementDate(), this.model.getListLastElementFirstValue(), this.model.getListLastElementSecondValue());
            }
            catch(IOException exx)
            {
                return"EXCEPTION";
            }
            return this.model.getComputee();
        }

    
     /**
     * simple constructor for Controller class, adds button listener and initialies list from file 
     * @param model ConversionModel object 
     */
     public ConversionController(ConversionModel model)
     {
         this.model = model;
         initializeListFromFile();
         //this.addButtonListener();
     }
     
    /** initializes list from file and catches exceptions thrown by model(IOexception and ParseException)
     * also creates new file if needed
     *
     */
    public void initializeListFromFile() 
     {
         try
         {
             model.getListFromFile("HIS.TXT");
         }
         catch(FileNotFoundException e)
         {
            new Alert(Alert.AlertType.ERROR, "File not found, default one will be created").show();
            File file = new File("HIS.TXT");
            try
            {
            file.createNewFile();
            }
            catch(IOException a)
            {
                new Alert(Alert.AlertType.ERROR, "Error creating file").show();
            }
         }
         catch(ParseException e)
         {
             new Alert(Alert.AlertType.ERROR, "Error parsing data format").show();
         }
     }
     
    }
    
    
    
