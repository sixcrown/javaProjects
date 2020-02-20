/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  @version 6.0
 * @author Bartosz Kawalkiewicz
 */
@Entity
@Table
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "ConversionHistory.findAll", query = "SELECT c FROM ConversionHistory c")
 , @NamedQuery(name = "ConversionHistory.findById", query = "SELECT c FROM ConversionHistory c WHERE c.id = :id")})
public class ConversionHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue  
    @Column(name = "ID")
    private Long id;
    @Lob
    @Size(max = 32700)
    @Column(name = "VALUE1")
    private String value1;
    @Lob
    @Size(max = 32700)
    @Column(name = "VALUE2")
    private String value2;
    @Lob
    @Size(max = 32700)
    @Column(name = "DATE")
    private String date;

    /**
     *
     * @param id
     * @param value1 before conversion 
     * @param value2 after conversion
     * @param date
     */
    public ConversionHistory(Integer id, String value1, String value2, String date) {
        long l = id;
        this.id =l;
        this.value1 = value1;
        this.value2 = value2;
        this.date = date;
    }

    public ConversionHistory(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConversionHistory)) {
            return false;
        }
        ConversionHistory other = (ConversionHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Conversion[ id=" + id + " ]";
    }


    public ConversionHistory() {
    }

    private DataSource getRef() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/ref");
    }

    public void persist(Object object) {
        /* Add this to the deployment descriptor of this module (e.g. web.xml, ejb-jar.xml):
         * <persistence-context-ref>
         * <persistence-context-ref-name>persistence/LogicalName</persistence-context-ref-name>
         * <persistence-unit-name>HexDecAppPU</persistence-unit-name>
         * </persistence-context-ref>
         * <resource-ref>
         * <res-ref-name>UserTransaction</res-ref-name>
         * <res-type>javax.transaction.UserTransaction</res-type>
         * <res-auth>Container</res-auth>
         * </resource-ref> */
        try {
            Context ctx = new InitialContext();
            UserTransaction utx = (UserTransaction) ctx.lookup("java:comp/env/UserTransaction");
            utx.begin();
            EntityManager em = (EntityManager) ctx.lookup("java:comp/env/persistence/LogicalName");
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

}
