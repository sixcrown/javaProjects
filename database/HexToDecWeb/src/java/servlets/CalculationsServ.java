/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import model.ConversionModel;
import model.ConversionController;
import model.StringContainsCharacters;

/**
 * Servlet responsible for doing calculations
 * @version 5.0
 * @author Bartosz Kawałkiewicz
 */
public class CalculationsServ extends HttpServlet {
    
    /**
   * controller responsible for executing methods of NumberModel
   */
    private int count=0;
  /**
   * controller responsible for executing methods of NumberModel
   */
  private ConversionController controller;
  
  /**
   * controller getter
   * @return private instance of NumberController class.
   */
  public ConversionController getNumberController(){
    return this.controller;
  }
  
  /**
   * contoller setter
   * @param controller - controller value to set
   */
  public void setConversionModel(ConversionController controller){
    this.controller = controller;
  }
  

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    PrintWriter out = response.getWriter();         
    response.setContentType("text/html;charset=UTF-8");
   
    ServletContext context = this.getServletContext();
    this.controller = (ConversionController) context.getAttribute("controller");
            
    if (this.controller == null)
    {
        
      ConversionModel model = new ConversionModel();
      model.setConfig(this.getServletConfig());
      this.controller = new ConversionController(model);
      context.setAttribute("controller", this.controller);
    }
     Boolean wasTableCreated = (Boolean) context.getAttribute("dbFlag");
     if (wasTableCreated == null){
     this.createTable(context);
    }
      HttpSession session = request.getSession(true);   
      this.controller.getConversionModel().setSession(session);
      boolean badParams=false;
      String output = "";
      String radio="";
      String list="";
      radio = request.getParameter("radiochk");
      list = request.getParameter("input");
      count++;
      try
      {
      if(list.isEmpty()){
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "List of numbers is empty");
        badParams=true;
      }
      }
      catch(NullPointerException a){
                  response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "List of numbers is empty");
                  badParams=true;
      }
      try
      {
          if(radio.isEmpty())
      {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "Please mark one of the calculation methods");
                badParams=true;
      }
      }
       catch(NullPointerException a){
                 response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "Please mark one of the calculation methods");
                 badParams=true;
      }
      if(!badParams){
        StringTokenizer st = new StringTokenizer(list);
        while (st.hasMoreTokens()) {
          try {
            this.controller.getConversionModel().calculate(radio, st.nextToken());
            output+=this.controller.getConversionModel().getComputee()+" ";
          }
          catch (NumberFormatException  e) {
              response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "One of the arguments might be excelling Integer size");
          } 
          catch(StringContainsCharacters a)
          {
              response.sendError(HttpServletResponse.SC_BAD_REQUEST,
              a.getMessage());
          }
          catch(IllegalArgumentException d)
          {
              response.sendError(HttpServletResponse.SC_BAD_REQUEST,
              "Empty string");
          }
          catch (UnsupportedOperationException b)
          {
               response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "Incorrect method");
               break;
          }
          catch (IOException c)
          {
               response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "Input/output exception");
               break;
          }
          catch (SQLException g)
          {
              response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                g.getMessage()+" Sql exception");
               break;
          }
        }
        Cookie cookie = new Cookie("operation" + count, " input string:" + list + " output string:" + output);     
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
      }
      
                  out.println("<html>\n" +
"    <head>\n" +
"        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"        <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"        <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css\" integrity=\"sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB\"\n" +
"          crossorigin=\"anonymous\">\n" +
"        <title>Conversion Calculator</title>\n" +
"    </head>\n" +
"    <body>\n" +
"        <div class=\"container\">\n" +
"            <h1>Conversion Calculator</h1>\n" +
"            <form action=\"Conversion Calculator\" method=\"GET\">\n" +
"                <h2 style=\"padding-top: 20px;\">Output:</h2>\n" +
"                <p><textarea rows=\"20\" cols=\"100\" name=input>");
            response.setContentType("text/plain; charset=ISO-8859-2");
            out.print(output);
            response.setContentType("text/html; charset=ISO-8859-2");
            out.println("</textarea></p>\n" +
"            </form>\n" +
"            \n" +
"        </div>\n" +
"    </body>\n" +
"</html>");
      
      
  }
  
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }
    private void createTable(ServletContext context){
    try { // loading the JDBC driver
      Class.forName("org.apache.derby.jdbc.ClientDriver");
    } catch (ClassNotFoundException cnfe) {
      System.err.println(cnfe.getMessage());
      return;
    }

    try (Connection con = DriverManager.getConnection(
            this.getServletConfig().getInitParameter("databaseURL"),
            this.getServletConfig().getInitParameter("user"),
            this.getServletConfig().getInitParameter("password"))) {
      
      Statement statement = con.createStatement();
      statement.executeUpdate("CREATE TABLE CONVERSION ("
              + "ID BIGINT NOT NULL primary key generated always as identity(START WITH 1, INCREMENT BY 1), "
              + "VALUE1 LONG VARCHAR, "
              + "VALUE2 LONG VARCHAR,"
      +"DATE LONG VARCHAR)");
      System.out.println("Table created");
    } 
    catch (SQLException sqle) 
    {
      System.err.println(sqle.getMessage());
    }
    
    context.setAttribute("dbFlag", true);
  }
}