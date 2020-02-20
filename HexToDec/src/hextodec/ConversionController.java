/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hextodec;

import common.EnumCalMethod;
import exceptions.StringContainsCharacters;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author Bartosz Kawalkiewicz
 * @version 2.0
 */
public class ConversionController {
     /**
      *
      *ConversionModel object 
    */
    private ConversionModel model;
    
 /**
 *
 * view object 
 */
    private ConversionView view;
    
    /**
 *
 * adds listener to calculation button and catches exceptions thrown by ConversionModel class 
 */
    private void addButtonListener()
    {
        this.view.getButton().setOnAction(e-> 
        {
            try
            {
                this.model.calculate(this.view.getTextFieldArg(), this.view.getCalculateMethod().getKey());
                this.view.getTextFieldResult().setText(this.model.getComputee());
            }
            catch(StringContainsCharacters | IllegalArgumentException | UnsupportedOperationException | IOException  ex)
            {
                 new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }
            try
            {
                this.model.appendToFile(this.model.getListLastElementDate(), this.model.getListLastElementFirstValue(), this.model.getListLastElementSecondValue());
            }
            catch(IOException exx)
            {
                new Alert(Alert.AlertType.ERROR, exx.getMessage()).show();
            }
        });
        };
     /**
 *
 * checks the command line arguments and sets the default ones if needed and in the end fires the calculation button
 *    if string doesnt match anything in enum then select default 
     * @param args list of command line arguments 
 */
     public void calculateByParameters(List<String> args)
     {

      if(args.isEmpty()|| args.size()<2)
      {
          new Alert(Alert.AlertType.ERROR, "2 parameters are needed to be passed, default one will be selected").show();
          this.view.setTextFieldArg("10");
          this.view.setComboBoxMethodType(EnumCalMethod.get("Hex To Dec"));
      }
      else
      {
      if(args.get(0).matches("\\d+"))this.view.setTextFieldArg(args.get(0));
      else
      { 
          new Alert(Alert.AlertType.ERROR, "First parameter is not a number, default one will be selected").show();
          this.view.setTextFieldArg("10");   
      }
      if (EnumCalMethod.get(args.get(1)) == null) 
        {
            new Alert(Alert.AlertType.ERROR, "calculate method was wrong! Default will be selected").show();
            this.view.setComboBoxMethodType(EnumCalMethod.get("Hex To Dec"));
        } 
        else 
        {
            this.view.setComboBoxMethodType(EnumCalMethod.get(args.get(1)));
        }
        
      }
      this.view.getButton().fire();
    }
     /**
     * simple constructor for Controller class, adds button listener and initialies list from file 
     * @param model ConversionModel object
     * @param view view object 
     */
     public ConversionController(ConversionModel model, ConversionView view)
     {
         this.model = model;
         this.view = view;
         initializeListFromFile();
         this.addButtonListener();
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

    /**
     * getter for view
     * @return view 
     */
    public ConversionView getView() 
    {
        return this.view;
    }
     
    }
    
    
    
