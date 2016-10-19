/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package jms;

import java.util.Properties;
import javax.faces.bean.ManagedBean;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;

public class SendMessage {

    static final Logger logger = Logger.getLogger("SimpleMessageClient");
    
    final Properties initialContextProperties = new Properties();
    final String factory = "java:comp/DefaultJMSConnectionFactory";
    final String queueName = "jms/auctionQueue";

    ConnectionFactory connectionFactory;
    Queue queue;
    
    /*
    //@Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;
    //@Resource(lookup = "jms/auctionQueue")
    private Queue queue;
    */
    
    public SendMessage(){
        try{
            final InitialContext ic = new InitialContext(initialContextProperties);

            this.connectionFactory = (ConnectionFactory) ic
                    .lookup(factory);
            this.queue = (Queue) ic.lookup(queueName);
        }catch(Exception e){
            this.connectionFactory = null;
            this.queue = null;
        }
    }
    
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void sendMessage(){
        try(JMSContext context = connectionFactory.createContext();){
            if(message != null){
                context.createProducer().send(queue, message);
            }
        }catch(Exception e){
            
        }
    }
    
    public void sendMessage(String msg){
        try(JMSContext context = connectionFactory.createContext();){
            if(msg != null){
                context.createProducer().send(queue, msg);
            }
        }catch(Exception e){
            System.out.println("Error: Could not create connection factory context");
            e.printStackTrace();
        }
    }
}