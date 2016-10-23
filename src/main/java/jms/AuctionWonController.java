/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms;

import boundary.AuctionFacade;
import entities.Auction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Andreas
 */
@Startup
@Singleton
public class AuctionWonController {    
    @EJB
    AuctionFacade auctionFacade;            
    List<Auction> activeActions;
    
    @PostConstruct
    void init(){
        activeActions=auctionFacade.getActiveAuctions();
    }
    
    
    public List<Auction> getTheAuctionThatHasBeenWon(){
        List<Auction> difference =new ArrayList<>(activeActions);        
        difference.removeAll(auctionFacade.getActiveAuctions());
        return difference;
    }
   
}
