        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.Timeout;
/**
 *
 * @author Eier
 */
@Stateless
public class TimerSchedularForActionWinner {

    
    @Resource
    TimerService timerService;

    public void addTimer(long secondsFromNow){
        timerService.createTimer(new Date().getTime()+secondsFromNow, "Hello");
    }
    
    @Timeout
    public void execute(Timer timer){
        System.out.println("Timer works");
        timer.cancel();
    }
  /*  @EJB
    AuctionTopicPublisher auctionTopicPublisher;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public void setTimersForAuction(Auction auction){
        long duration = 6000;
        Timer timer =timerService.createSingleActionTimer(duration, new TimerConfig());
        auctionTopicPublisher.sendWhoWonAuction();
    }*/
}
