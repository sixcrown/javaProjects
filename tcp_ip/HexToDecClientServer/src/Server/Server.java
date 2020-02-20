/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package Server;

import java.net.*;
import java.io.*;
import java.util.Properties;
import java.util.Scanner;


/**
 * class running the server
 * @author Bartosz Kawałkiewicz
 * @version 3.0
 */

public class Server implements Closeable {
    private static int PORT;
    private ServerSocket serverSocket;
    private Properties servProperties;
    
   /**
   * PORT getter.
   * @return port number
   */
  public int getPort(){
    return this.PORT;
  }
  
   /**
   * PORT setter.
   * @param PORT - a value this.PORT is going to be initialized with
   */
  public void setPort(int PORT){
    this.PORT = PORT;
  }
    
    Server(String propertiesPath) throws IOException {
        //serverSocket = new ServerSocket(PORT);
        Properties properties = new Properties();
        try(FileInputStream file = new FileInputStream(propertiesPath))
        {
            properties.load(file);
            servProperties = properties;
            PORT= Integer.parseInt(this.servProperties.getProperty("PORT"));
            serverSocket = new ServerSocket(PORT);
        }
        catch(IOException a)
        {
            System.out.println("Could not make a connection, server is probably already running or port was incorrect");
        }
    }
    
   /**
   * serverProperties getter.
   * @return server properties
   */
  public Properties getServerProperties(){
    return this.servProperties;
  }
   /**
   * serverProperties setter.
   * @param serverProperties - properties this.serverProperties are going to
   * be initialized with
   */
  public void setServerProperties(Properties serverProperties){
    this.servProperties = serverProperties;
  }
  
   /**
   * serverSocket setter.
   * @param serverSocket - a socket this.serverSocket is going to be initialized with
   */
  public void setServerSocket(ServerSocket serverSocket){
    this.serverSocket = serverSocket;
  }
  
  /**
   * serverSocket getter.
   * @return socket waiting for client connections
   */
  public ServerSocket getServerSocket(){
    return this.serverSocket;
  }  
    
    public static void main(String args[]){
        
        File file;
        String propFilePath;
        Scanner scanner = new Scanner(System.in);
        if(args.length <1)
        {
            System.out.print("No property file filepath, please enter correct property filepath ");
            propFilePath = scanner.nextLine();
        }
        else 
        {
            propFilePath = args[0];
        }
        file = new File(propFilePath);
        while(!file.exists())
        {
            System.out.print("Enter correct filepath to .properties file");
                 propFilePath = scanner.nextLine();
                 file = new File(propFilePath);
        }
        
        try (Server server = new Server(propFilePath)){
            System.out.println("Server has started.");
            System.out.println("Server port: " + PORT);

            while(true){
                System.out.println("Waiting for client ...");
                Socket socket = server.serverSocket.accept();
                
                try (ServerService serverService = new ServerService(socket)){
                    serverService.realize();
                    
                    if(!serverService.getIsServerOpen())
                        break;
                }
                catch (IOException ex){
                    System.err.println(ex.getMessage());
                }
            }
        }
        catch (IOException ex){
            System.err.println(ex.getMessage());
        }
        
    }
    
    @Override
    public void close() throws IOException {
        if(serverSocket != null){
            serverSocket.close();
        }
    }
    
}
