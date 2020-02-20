/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.ConversionHistory;
import model.ConversionModel;
import model.StringContainsCharacters;

/**
 * @version 6.0
 * @author Bartosz Kawalkiewicz
 */
@WebService(serviceName = "Services")
public class Services {
    EntityManager em;
    EntityManagerFactory emf;

    /**
     * Web service operation
     * @param method
     * @param value
     * @return string with output
     */
    @WebMethod(operationName = "calculate")
    public String calculate(@WebParam(name = "method") String method, @WebParam(name = "value") String value) {
        //TODO write your implementation code here:
        ConversionModel model = new ConversionModel();
        StringTokenizer st = new StringTokenizer(value);
        if(value.isEmpty()) return"Please enter value";
        if(method ==null) return"Please mark calculation method";
        String output="";
        String tmpInput="";
        Date today = new Date();
        String date =today.toString();
        while (st.hasMoreTokens()) 
        {
            try
            {  
                tmpInput=st.nextToken();
            model.calculate(method,tmpInput);
            output+=model.getComputee()+" ";
            
            }
            catch(StringContainsCharacters|IllegalArgumentException e )
            {
                return"Illegal characters entered";
            } catch (IOException ex) {
                return"IOException occured";
            } catch (SQLException ex) {
                Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (UnsupportedOperationException ex)
            {
                return"Please mark calculation method";
            }
            add(tmpInput,model.getComputee(),date);
        }
        
        return output;
    }
      private void add(String value1, String value2, String date) {
        emf = Persistence.createEntityManagerFactory("HexDecAppPU");
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ConversionHistory hist = new ConversionHistory(getNewId(), value1, value2,date);
            em.persist(hist);
            em.getTransaction().commit();

        } catch (Exception e) {

        } finally {
            em.close();
            emf.close();
        }
    }
    /**
     * Web service operation
     * @return history of transactions
     */
    @WebMethod(operationName = "showHistory")
    public String showHistory() {
        emf = Persistence.createEntityManagerFactory("HexDecAppPU");
        em = emf.createEntityManager();
        String result = "Historia: </br>";
        try {

            Query q = em.createNamedQuery("ConversionHistory.findAll");
            List<ConversionHistory> l = q.getResultList();
            for (ConversionHistory elem : l) 
            {
                result += ("</br>Operacja numer: " + elem.getId()+ "</br>");
                result += ("Input: " + elem.getValue1()+" "+" output: "+ elem.getValue2()+" Data: "+elem.getDate()+ "</br></br></br>");
            }

        } 
        catch (Exception e) 
        {
            return "Something gone wrong...";
        } 
        finally 
        {
            em.close();
            emf.close();
        }
                    return result;
    }
        private int getNewId() {
        Query q = em.createNamedQuery("ConversionHistory.findAll");
        return q.getResultList().size() + 1;

    }
}