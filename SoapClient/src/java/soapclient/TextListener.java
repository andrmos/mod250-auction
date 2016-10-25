/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soapclient;

import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author Eier
 */
public class TextListener implements MessageListener{

    @Override
    public void onMessage(Message m) {
      try {
            if (m instanceof TextMessage) {
                System.out.println(
                        "Reading message: " + m.getBody(String.class));   
                Main.updateGuiList();
            } else {
                System.out.println("Message is not a TextMessage");
            }
        } catch (JMSException | JMSRuntimeException e) {
            System.err.println("JMSException in onMessage(): " + e.toString());
        }
    }
    
}
