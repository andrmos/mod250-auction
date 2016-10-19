/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package jms;

import javax.faces.bean.ManagedBean;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Named(value="sendMessage")
@RequestScoped
@ManagedBean
public class SendMessage {

    static final Logger logger = Logger.getLogger("SimpleMessageClient");
    
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/auctionQueue")
    private Queue queue;
    
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
}