/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soapclient;

import java.util.Arrays;
import javax.xml.ws.WebServiceRef;
/**
 *
 * @author Eier
 */
import webservices.SoapService_Service;/**
 *
 * @author Eier
 */
public class Main {

    @WebServiceRef(wsdlLocation = "http://localhost:8080/mod250_auction/SoapService?WSDL")
    private static SoapService_Service service;

       
   
    public static void main(String[] args) {
        System.out.println(activeAucitons().get(0).getInitPrice());
        
    }

    private static java.util.List<webservices.Auction> activeAucitons() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webservices.SoapService port = service.getSoapServicePort();
        return port.activeAucitons();
    }


    
}
