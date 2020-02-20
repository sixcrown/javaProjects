/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.*;
import java.net.Socket;
import view.ClientView;

/**
 * Controller for conversion client.
 * @version 3.0
 * @author Bartosz Kawalkiewicz
 */
public class ClientController implements Closeable {
  /**
   * private attributes
   */  
  /**
   * boolean used to know if response with converted number is comming 
   */
   private boolean incommingString = false; 

  /**
   * socket - socket representing connection to the client
   */
  private Socket socket;
    
  /**
   * serverInput - buffered input character stream from server
   */
  private BufferedReader serverInput;
  
  /**
   * output - formatted output character stream
   */
  private PrintWriter output;
  
  /**
   * view - prints server responses 
   */
  private ClientView view;
  
  /**
   * userInput - buffered input character stream from user
   */
  private BufferedReader userInput;

  
  /**
   * attributes getters and setters
   */
  
  /**
   * socket getter.
   * @return socket
   */
  public Socket getSocket(){
    return this.socket;
  }
  
  /**
   * socket setter.
   * @param socket - a socket this.socket is going to be initialized with
   */
  public void setSocket(Socket socket){
    this.socket = socket;
  }
    
  /**
   * serverInput getter.
   * @return buffered serverInput character stream
   */
  public BufferedReader getServerInput(){
    return this.serverInput;
  }
  
  /**
   * serverInput setter.
   * @param serverInput - a buffered stream this.serverInput is going to be initialized with
   */
  public void setServerInput(BufferedReader serverInput){
    this.serverInput = serverInput;
  }
  
  /**
   * output getter.
   * @return formatted output character stream
   */
  public PrintWriter getOutput(){
    return this.output;
  }
  
  /**
   * output setter.
   * @param output - a formatted stream this.output is going to be initialized with
   */
  public void setOutput(PrintWriter output){
    this.output = output;
  }
   
  /**
   * view getter.
   * @return application view
   */
  public ClientView getView(){
    return this.view;
  }
  
  /**
   * view setter.
   * @param view - application view
   */
  public void setOutput(ClientView view){
    this.view = view;
  }
  
  /**
   * userInput getter.
   * @return buffered input character stream
   */
  public BufferedReader getUserInput(){
    return this.userInput;
  }
  
  /**
   * userInput setter.
   * @param userInput - a buffered stream this.userInput is going to be initialized with
   */
  public void setUserInput(BufferedReader userInput){
    this.userInput = userInput;
  }
  
  /**
   * An instance constructor of the ClientHandler class.
   * @param clientSocket - a socket representing client
   * @throws IOException
   */
  public ClientController(Socket clientSocket) throws IOException{
    this.view = new ClientView();
    this.socket = clientSocket;
    this.output = new PrintWriter(
            new BufferedWriter(
                    new OutputStreamWriter(
                            clientSocket.getOutputStream())), true);
    this.serverInput = new BufferedReader(
            new InputStreamReader(
                    clientSocket.getInputStream()));
    this.userInput = new BufferedReader(new InputStreamReader(System.in));
  }
  
  /**
   * other methods
   */
  
  /**
   * A method for sending requests
   * @param request a request to send
   */
  public void sendRequest(String request){
    this.output.println(request);
  }
  
  /**
   * A method for handling responses.
   * @return a boolean defining wheather to still handle incoming responses
   * @throws IOException
   */
  public boolean handleResponse() throws IOException{
    boolean stillRealize;
    String wasRequestValid = this.serverInput.readLine();
    wasRequestValid = wasRequestValid.toUpperCase().trim();
    if(incommingString)
    {
        incommingString = false;
        System.out.println(wasRequestValid);
        return true;
    }
    else
    {
    switch(wasRequestValid){
      case "OK": {
        stillRealize = this.view.printResponse(this.serverInput);
        break;
      }
      case "EXCEPTION":
      {
          this.view.printException();
          stillRealize = true;
          break;
      }
      default: {
        this.view.printUnknownCommand();
        stillRealize = true;
      }
    }
    return stillRealize;
  }
}
  
  /**
   * A method for providing params for server
   * @throws IOException
   */
  public void sendParams() throws IOException{
    String response = "";
    String param, wasRequestAccepted;
    wasRequestAccepted = this.serverInput.readLine(); 
    System.out.println(wasRequestAccepted);
    
    while(!response.toUpperCase().trim().equals("PARAMETERS OK")){
      System.out.println("Parameter: ");
      param = this.userInput.readLine();
      this.sendRequest(param);
      response = this.serverInput.readLine();
      System.out.println(response);
    }
    incommingString = true;
  }
  
  /**
   * A client side method to start handling requests and responses.
   */
  public void start(){
    boolean stillRealize = true;
    String request;
    
    try {      
      while (stillRealize) {
        System.out.println("Enter command: ");
        request = userInput.readLine();
        request = request.toLowerCase().trim();
        this.sendRequest(request);
        
        if(request.equals("calculate")){
          this.sendParams();
        }
        
        stillRealize = this.handleResponse();
      }
      System.out.println("closing...");
    } catch (IOException e) {
      System.out.println("An error occured while sending requests.");
    }
     finally {
      try {
        this.socket.close();
      } catch (IOException e) {
        System.out.println("An error occured while closing connection to server.");
      }
    }
  }
  
  /**
   * Overriden method for closing socket.
   * @throws IOException if could not close socket
   */
  @Override
  public void close() throws IOException {
    if (this.socket != null) {
      this.socket.close();
    }
  }
}
