/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

//import Server.exceptions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import javafx.scene.control.Alert;

/**
 *
 * @author Bartosz Kawalkiewicz
 * @version 5.0
 */
public class ConversionController {
    
    
    
     /**
      *
      *ConversionModel object 
    */
    private ConversionModel model;
    
    public ConversionController()           
    {
    }
    
         /**
     * simple constructor for Controller class
     * @param model ConversionModel object 
     */
     public ConversionController(ConversionModel model)
     {
         this.model = model;
     }
    /**
     * getter for model
     * @return model
     */
    public ConversionModel getConversionModel()
    {
        return this.model;
    }
    
    /**
     * setter for the model object
     * @param model
     */
    public void setConversionModel(ConversionModel model)
    {
        this.model = model;
    }
     
    }
    
    
    
