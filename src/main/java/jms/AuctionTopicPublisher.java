/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms;
import entities.Auction;
import java.util.Properties;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;                                                                           
import javax.jms.Topic;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicPublisher;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;
import javax.jms.TopicSession;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.naming.NamingException;
/**
 *
 * @author Andreas
 */
@Stateless
public class AuctionTopicPublisher {          
    final Properties initialContextProperties = new Properties();
    final String factoryDest = "jms/auctionTopicFactory";
    final String topicDest = "jms/auctionTopic";
    
    ConnectionFactory connectionFactory;
    Topic topic;
    private Destination dest;
    
    public void sendWhoWonAuction(Auction auction) throws NamingException{  
        final InitialContext ic = new InitialContext(initialContextProperties);
        connectionFactory=(ConnectionFactory) ic.lookup(factoryDest);
        topic= (Topic) ic.lookup(topicDest);
        dest=(Destination)topic;
          try (JMSContext context = connectionFactory.createContext();) {
              try{
              context.createProducer().send(dest,"Winner of auctionID "+ auction.getId()+ " is" +auction.getBid().getAuctionUser().getUsername());
              System.out.println("Sent messsssage: "+"Winner of auctionID "+ auction.getId()+ " is" +auction.getBid().getAuctionUser().getUsername());
              }
              catch(NullPointerException e){
                  context.createProducer().send(dest,"Auction is finished with no bids: " +auction.getProduct().getDescription());
                  System.out.println("Sent messsssage: "+"Auction is finished with no bids: " +auction.getProduct().getDescription());
              }
          }
          catch (JMSRuntimeException e) {
            System.err.println("Exception occurred: " + e.toString());
            System.exit(1);
        }
    }   
    
}
