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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ConversionController;
import model.ConversionModel;

/**
 * @version 5.0
 * @author Bartosz Kawalkiewicz
 */
public class CollectionDB extends HttpServlet {
  /**
   * controller responsible for executing methods of ConversionModel
   */
  private ConversionController controller;
  
  /**
   * controller getter
   * @return private instance of Controller class.
   */
  public ConversionController getController(){
    return this.controller;
  }
  
  /**
   * contoller setter
   * @param controller - controller value to set
   */
  public void setController(ConversionController controller){
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
    response.setContentType("text/html;charset=UTF-8");
    
    ServletContext context = this.getServletContext();
    this.controller = (ConversionController) context.getAttribute("controller");
    
    if (this.controller == null){
      ConversionModel model = new ConversionModel();
      this.controller = new ConversionController(model);
      context.setAttribute("controller", this.controller);
    }
    
    try (PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Data Stored in Database</title>");      
      out.println("</head>");
      out.println("<body>");
      out.println("<h2>Data: </h2>");
      
      if(this.controller.getConversionModel().getList() == null){
        response.sendError(HttpServletResponse.SC_NOT_FOUND,
                "collection was not initialized!");
      }
      else {      
        try {
      // loading the JDBC driver
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
          ResultSet rs = statement.executeQuery("SELECT * FROM Conversion");

          while (rs.next()) {
            out.println("<h2>");
            out.println("<tr>");
            out.println("<td>" + rs.getInt("ID") + "</td>");
            out.println("<td>" + rs.getString("VALUE1") + "</td>");
            out.println("<td>\t" + rs.getString("VALUE2") + "</td>");
            out.println("<td>\t" + rs.getString("DATE") + "</td>");
            out.println("</tr>");
            out.println("</h2>");
          }
          rs.close();
        } catch (SQLException ex) {
          System.err.println(ex.getMessage());
        }
        out.println("</table>");        
      }
      
      out.println("</body>");
      out.println("</html>");
    }
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

  /**
   * Returns a short description of the servlet.
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Servlet used to provide data stored in model's collection";
  }
}
