/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms;
import javax.ejb.EJB;
import javax.naming.InitialContext;                                                                           
import javax.jms.Topic;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicPublisher;
import javax.jms.DeliveryMode;
import javax.jms.TopicSession;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
/**
 *
 * @author Andreas
 */
public class AuctionTopicPublisher {
    @EJB
    AuctionWonController auctionWonController;
    
    public void sendWhoWonAuction(){
        if(auctionWonController.getTheAuctionThatHasBeenWon().size()==0){
           //Nothing won
        }
        //else someone won
    }
}
