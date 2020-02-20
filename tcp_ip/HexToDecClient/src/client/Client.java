/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.*;
import java.net.*;
import java.util.Properties;
import java.util.Scanner;

/**z
 * Client for Hexadecimal/decimal calculations
 * @version 3.0
 * @author Bartosz Kawalkiewicz
 */
public class Client implements Closeable {
  
  
  /**
   * clientProperties - client properties
   */
  private Properties clientProperties;
  
  /**
   * clientSocket - socket for client
   */
  private Socket clientSocket;
  
  /**
   * serverAddress - server address
   */
  private InetAddress serverAddress;
  
  /**
   * PORT - port number
   */
  private int PORT;
  
  /**
   * attributes getters and setters
   */
  
  /**
   * clientProperties getter.
   * @return client properties
   */
  public Properties getClientProperties()
  {
    return this.clientProperties;
  }
  
  /**
   * clientProperties setter.
   * @param clientProperties - properties this.clientProperties are going to
   * be initialized with
   */
  public void setClientProperties(Properties clientProperties)
  {
    this.clientProperties = clientProperties;
  }
  
  /**
   * clientSocket getter.
   * @return client socket
   */
  public Socket getClientSocket(){
    return this.clientSocket;
  }
  
  /**
   * clientSocket setter.
   * @param clientSocket - a socket this.clientSocket is going to be initialized with
   */
  public void setClientSocket(Socket clientSocket){
    this.clientSocket = clientSocket;
  }
    
  /**
   * serverAddress getter.
   * @return server address
   */
  public InetAddress getServerAddress(){
    return this.serverAddress;
  }
  
  /**
   * serverAddress setter.
   * @param serverAddress - an internet address
   * this.serverAddress is going to be initialized with
   */
  public void setServerAddress(InetAddress serverAddress){
    this.serverAddress = serverAddress;
  }
     
  /**
   * PORT getter.
   * @return port number
   */
  public int getPort(){
    return this.PORT;
  }
  
  /**
   * PORT setter.
   * @param PORT - port value to be initialized with 
   */
  public void setPort(int PORT){
    this.PORT = PORT;
  }
  
  /**
   * An constructor for the client class
   * @param propertiesFilePath path to a .properties file 
   * @throws IOException
   */
  public Client(String propertiesFilePath) throws IOException {
    Properties properties = new Properties();
    try (FileInputStream in = new FileInputStream(propertiesFilePath)) {
      properties.load(in);
      this.clientProperties = properties;
      this.PORT = Integer.parseInt(this.clientProperties.getProperty("PORT"));
      this.serverAddress = InetAddress.getByName(this.clientProperties.getProperty("ADDRESS"));
      this.clientSocket = new Socket(this.serverAddress, this.PORT);
    } catch (IOException e) {
      System.out.print("Could not make a connection. Check .properties file\n");
    }
  }
  
  /**
   * other methods
   */
  
  /**
   * main method in client.
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String propertiesFilePath;
    File file;
    
    if(args.length == 0){
      System.out.println("Enter a path to .properties file: ");
      propertiesFilePath = scan.nextLine();
    }
    else{
      propertiesFilePath = args[0];
    }
   
    file = new File(propertiesFilePath);

    while(!file.exists()){
      System.err.println(propertiesFilePath + " does not exist.");
      System.out.println("Enter a path to .properties file: ");
      propertiesFilePath = scan.nextLine();
      file = new File(propertiesFilePath);
    }
    
    try (Client tcpClient = new Client(propertiesFilePath)) {
      System.out.println("Client started");
      ClientController clientHandler = new ClientController(tcpClient.getClientSocket());
      clientHandler.start();
    } catch (IOException ex) {
      System.out.println("An error occured while connecting to server.");
    }
  }
  
  
  /**
   * Overriden method for closing client socket.
   * @throws IOException if could not close client socket
   */
  @Override
  public void close() throws IOException {
    if (this.clientSocket != null) {
      this.clientSocket.close();
    }
  }
}
