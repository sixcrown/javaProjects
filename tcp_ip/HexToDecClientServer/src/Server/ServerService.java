/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.HexToDec.ConversionController;
import java.io.*;
import java.net.*;

/**
 * class for managing server-side communication with client(and Model)
 * @author Bartosz Kawałkiewicz
 * @version 3.0
 */
public class ServerService implements Closeable {
    
    //classes Frequency, HuffmanCode and HuffmanDecode belong to model and are used to invoke functions
    //class View belong to view and is used to manage input and output
    
    private ConversionController controller;
  /**
   * requestApprovedMsg - represents approved request
   */
  private final String approved = "OK"; 
  
  /**
   * requestDeclinedMsg - represents declined request
   */
  private final String declined = "UNKNOWN COMMAND"; 
  private final String paramsApproved = "PARAMETERS OK";

    /**
     * A socket variable for communicating with client
     */
    private Socket socket;
    /**
     * Input from client in a form of a BufferedReader Object
     */
    private BufferedReader input;
    /**
     * Output to client in a form of a PrintWriter Object
     */
    private PrintWriter output;
    /**
     * Control variable of a sort, used for determining when to stop the server
     */
    private boolean isServerOpen = true;
    
    private boolean realize = true;
    
    ServerService(Socket socket) throws IOException 
    {
        this.socket = socket;
        output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.controller = new ConversionController();
    }
   /**
   * A method for sending info to client wheather server can handle request or not
   * @param responseMsg - a response message to send
   */
  public void sendResponse(String responseMsg){
    this.output.println(responseMsg);
  }
    /**
   * A method used to show the user HELP message.
   */
  public void printHelp(){
    this.output.println("Available commands: calculate, quit, help");
    this.output.println("calculate requires calling method and number to be converted");
    this.output.println("first the calling method: Hex To Dec or Dec To Hex");
    this.output.println("Then the number to be converted");
    this.output.println("It is neccessary to provide correct parameters, or response will be declined");
    this.output.println("END");
  }
    
    /**
     * Function realizing server-side communication with client through sockets.
     * It receives all important input data from client and, using classes from Model, transforms it only to be sent to client. :( 
     */
    public void realize(){
        try{
            while(isServerOpen)
            {
                this.handleRq();
            }
        }
        catch(IOException ex)
        {
            System.err.println("Server is closing ...");
        }
        finally {
            try {
                socket.close();
            } 
            catch (IOException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    public void handleRq() throws IOException
    {
        String request = input.readLine();
        String response;
        String params[] = new String[2];
        request = request.toLowerCase().trim();
        switch(request)
        {
            case "quit":
                    {
                        this.isServerOpen = false;
                        this.sendResponse(this.approved);
                        this.output.println("quit");
                        this.realize = false;
                        break;
                    }
            case "help":
                    {
                        this.sendResponse(this.approved);
                        this.printHelp();
                        break;
                    }
            case "calculate":
                    {
                        this.sendResponse(this.approved);
                        this.getMethodAndNumber(params);
                        response = controller.calculate(params);
                        //this.output.println(this.paramsApproved);
                        this.output.println(response);
                        break;
                    }
            default:
                this.sendResponse(this.declined);
                break;
        }
    }
    public void getMethodAndNumber(String[] params)
    {
        Boolean firstLoop = true;
        Boolean secondLoop = true;
        String firstParam="";
        String secondParam="";
        while(this.realize)
        {
            try
            {
            while(firstLoop)
            {
            firstParam = this.input.readLine();
            if(firstParam.toLowerCase().trim().equals("hex to dec")||firstParam.toLowerCase().trim().equals("dec to hex"))
            {
                this.sendResponse(this.approved);
                params[0] =firstParam;
                firstLoop=false;
            }
            else 
            {
                this.sendResponse(this.declined);
            }
            }
            while(secondLoop)
            {
                secondParam = this.input.readLine();
                switch(firstParam)
                {
                    case "hex to dec":
                    {
                        if(this.CheckHexNumber(secondParam))
                        {
                            this.sendResponse(this.paramsApproved);
                            params[1] = secondParam;
                            secondLoop=false;
                           // break;
                        }
                        else
                        {
                            this.sendResponse(this.declined);
                        }
                        
                        break;
                    }
                    case "dec to hex":
                    {
                        if(this.CheckDecNumber(secondParam))
                        {
                            this.sendResponse(this.paramsApproved);
                            params[1] = secondParam;
                            secondLoop=false;
                        }
                        else
                        {
                            this.sendResponse(this.declined);
                        }
                        
                        break;
                    }
                    default:
                    {
                        this.sendResponse(this.declined);
                        break;
                    }
                }
            }
            if(!(firstLoop && secondLoop)) break;
           // this.sendResponse(this.paramsApproved);
            }
            catch (IOException a)
            {
                System.out.print("IOexception occured while providing parameters");
            }
            
        }
    }
    private boolean CheckHexNumber(String number)
    {
            int len = number.length();
            if(number.isEmpty()) return false;
            for (int i = len - 1; i >= 0; i--) 
        {
            if (!((number.charAt(i) >= '0' && number.charAt(i) <= '9')||(number.charAt(i) >= 'A' && number.charAt(i) <= 'F'))) 
            {
                 return false;
            } 
        }
        return true;
    }
    private boolean CheckDecNumber(String number)
    {
        if(number.isEmpty()) return false;
        for (int i = number.length() - 1; i >= 0; i--) 
        {
        if(number.charAt(i) < '0' || number.charAt(i) > '9')
        {
            return false;
        }
        }
        return true;
    }
    @Override
    public void close() throws IOException{
        if(socket != null){
            socket.close();
        }
    }
    
    public boolean getIsServerOpen(){
        return isServerOpen;
    }
}
