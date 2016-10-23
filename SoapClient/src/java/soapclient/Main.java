/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soapclient;

import java.util.Arrays;
import javax.xml.ws.WebServiceRef;
import javax.swing.*;
/**
 *
 * @author Eier
 */
import webservices.SoapService_Service;/**
 *
 * @author Eier
 */
public class Main extends JFrame {

    @WebServiceRef(wsdlLocation = "http://localhost:8080/mod250_auction/SoapService?WSDL")
    private static SoapService_Service service;
    

   
    public static void main(String[] args) {
        System.out.println(activeAucitons().get(0).getInitPrice());
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                createAndShowGUI();
            }
        });
        
    }

    private static java.util.List<webservices.Auction> activeAucitons() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webservices.SoapService port = service.getSoapServicePort();
        return port.activeAucitons();
    }

    
     /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    
}
