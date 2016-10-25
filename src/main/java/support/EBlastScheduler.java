/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import boundary.AuctionFacade;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.genericBooleanType;
import entities.Auction;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.inject.Inject;

/**
 *
 * @author Eier
 */

@Singleton 
@Startup
@Stateful
public class EBlastScheduler {

@EJB
TimerUtilityBean timerBean;

@EJB
AuctionFacade auctionFacade;


 
private List<Auction> activeAuctions;

@PostConstruct
 public void initialize(){         
  timerBean.cancelTimer("EBlastScheduler");
   activeAuctions=auctionFacade.getActiveAuctions();    
       Calendar c= Calendar.getInstance();
     c.add(Calendar.SECOND, 20);     
     timerBean.createTimer(c.getTime(), activeAuctions.get(0));
    setUpTimers(activeAuctions);
 }
 
 private void setUpTimers(List<Auction> auctions){
     for(int i=0; i<auctions.size();i++){
            addTimerToAuction(auctions.get(i));
       }  
    }

    public void addTimerToAuction(Auction auction) {
        Calendar c= (Calendar)auction.getStartTime().clone();
        c.add(Calendar.SECOND,Math.toIntExact(auction.getDuration()));
        timerBean.createTimer(c.getTime(),auction);        
    }     
}