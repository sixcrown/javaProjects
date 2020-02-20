package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import model.ConversionController;
import model.ConversionModel;
import model.ConversionModel.History;
/**
 * A servlet for collection
 * 
 * @version 5.0
 * @author Bartosz Kawałkiewicz
 */
public class Collection extends HttpServlet {
  
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
  public void setNumberController(ConversionController controller){
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
      out.println("<title>Collection Servlet</title>");      
      out.println("</head>");
      out.println("<body>");
      out.println("<h2>Data: </h2>");
      
      if(this.controller.getConversionModel().getList() == null){
        response.sendError(HttpServletResponse.SC_NOT_FOUND,
                "collection was not initialized!");
      }
      else {
        int i = 0;
        for(History val: this.controller.getConversionModel().getList()){
          out.println("<h4>List[" + i + "] = " + val.getDate()+" before conversion:"+val.getFirstValue()+
                  " after conversion:"+val.getSecondValue()+ "</h4>");
          i++;
        }
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
