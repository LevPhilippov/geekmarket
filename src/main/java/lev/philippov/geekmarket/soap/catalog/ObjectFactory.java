//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.30 at 05:05:40 PM KRAT 
//


package lev.philippov.geekmarket.soap.catalog;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the lev.philippov.geekmarket.soap.catalog package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: lev.philippov.geekmarket.soap.catalog
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetItemListResponse }
     * 
     */
    public GetItemListResponse createGetItemListResponse() {
        return new GetItemListResponse();
    }

    /**
     * Create an instance of {@link SoapItemDto }
     * 
     */
    public SoapItemDto createSoapItemDto() {
        return new SoapItemDto();
    }

    /**
     * Create an instance of {@link GetItemListRequest }
     * 
     */
    public GetItemListRequest createGetItemListRequest() {
        return new GetItemListRequest();
    }

}