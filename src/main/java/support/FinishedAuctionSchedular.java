/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.util.TimerTask;
import jms.AuctionTopicPublisher;

/**
 *This class is used to trigger jms when auction is finished. 
 * @author Andreas
 */
public class FinishedAuctionSchedular extends TimerTask {
AuctionTopicPublisher auctionTopicPublisher=new AuctionTopicPublisher();
        @Override
        public void run() {
            auctionTopicPublisher.sendWhoWonAuction();
            //Push.sendAll("updateMenu");
        }
    
}
