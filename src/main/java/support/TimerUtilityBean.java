/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import entities.Auction;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.naming.NamingException;
import jms.AuctionTopicPublisher;

/**
 *
 * @author Eier
 */
@Stateless
public class TimerUtilityBean {
    
     @EJB
     AuctionTopicPublisher auctionTopicPublisher;
    
    @Resource
    TimerService timerService;

     public void createTimer(Date exDate, Auction auction){   
        TimerConfig timerConfig=new TimerConfig();
        timerConfig.setInfo(auction);
        timerService.createSingleActionTimer(exDate, timerConfig);     
     }

     public void cancelTimer(String timerInfo){

      if (timerService.getTimers() != null) {

           for (Timer timer : timerService.getTimers()) 
               if (timer.getInfo().equals(timerInfo))
                   timer.cancel();
       }
     }

     @Timeout
     public void execute(Timer timer){
         try {
             auctionTopicPublisher.sendWhoWonAuction((Auction) timer.getInfo());
         } catch (NamingException ex) {
             Logger.getLogger(TimerUtilityBean.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

}
