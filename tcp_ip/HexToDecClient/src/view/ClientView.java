/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * view class for the client printing needed information based on server response
 * @version 3.0
 * @author Bartosz Kawalkiewicz
 */
public class ClientView {
  
  /**
   * Default class constructor
   */
  public ClientView(){
  }

  /**
   * A method for printing server's response
   * @param serverInput
   * @return false if quit
   * @throws IOException 
   */
  public boolean printResponse(BufferedReader serverInput) throws IOException{
    String responseLine = serverInput.readLine();
    while (!responseLine.toLowerCase().trim().equals("end")){
      if (responseLine.toLowerCase().trim().equals("quit")){
        return false;
      }
      
      System.out.println(responseLine);
      responseLine = serverInput.readLine();
    }
    return true;
  }
    
  /**
   * A method for printing UNKNOWN COMMAND
   */
  public void printUnknownCommand(){
    System.out.println("Command is unknown. Type HELP to print available commands.");
  }
    /**
   * A method for printing exception
   */
  public void printException(){
    System.out.println("Exception Thrown by model");
  }
}
