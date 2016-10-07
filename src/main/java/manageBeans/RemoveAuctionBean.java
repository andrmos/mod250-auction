/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import entities.Auction;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author DidrikKvanvik
 */
@Named(value = "removeAuctionBean")
@SessionScoped
public class RemoveAuctionBean implements Serializable {

    @EJB
    AuctionFacade auctionFacade;
    
    private Auction auction;
    
    /**
     * Creates a new instance of RemoveAuctionBean
     */
    public RemoveAuctionBean() {
    }
    
    public void removeAuction(Long auctionID){
        auction = auctionFacade.find(auctionID);
        auctionFacade.remove(auction);
    }
    
}
