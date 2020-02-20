
package services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the services package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Calculate_QNAME = new QName("http://services/", "calculate");
    private final static QName _CalculateResponse_QNAME = new QName("http://services/", "calculateResponse");
    private final static QName _ShowHistory_QNAME = new QName("http://services/", "showHistory");
    private final static QName _ShowHistoryResponse_QNAME = new QName("http://services/", "showHistoryResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Calculate }
     * 
     */
    public Calculate createCalculate() {
        return new Calculate();
    }

    /**
     * Create an instance of {@link CalculateResponse }
     * 
     */
    public CalculateResponse createCalculateResponse() {
        return new CalculateResponse();
    }

    /**
     * Create an instance of {@link ShowHistory }
     * 
     */
    public ShowHistory createShowHistory() {
        return new ShowHistory();
    }

    /**
     * Create an instance of {@link ShowHistoryResponse }
     * 
     */
    public ShowHistoryResponse createShowHistoryResponse() {
        return new ShowHistoryResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Calculate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "calculate")
    public JAXBElement<Calculate> createCalculate(Calculate value) {
        return new JAXBElement<Calculate>(_Calculate_QNAME, Calculate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "calculateResponse")
    public JAXBElement<CalculateResponse> createCalculateResponse(CalculateResponse value) {
        return new JAXBElement<CalculateResponse>(_CalculateResponse_QNAME, CalculateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShowHistory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "showHistory")
    public JAXBElement<ShowHistory> createShowHistory(ShowHistory value) {
        return new JAXBElement<ShowHistory>(_ShowHistory_QNAME, ShowHistory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShowHistoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "showHistoryResponse")
    public JAXBElement<ShowHistoryResponse> createShowHistoryResponse(ShowHistoryResponse value) {
        return new JAXBElement<ShowHistoryResponse>(_ShowHistoryResponse_QNAME, ShowHistoryResponse.class, null, value);
    }

}
