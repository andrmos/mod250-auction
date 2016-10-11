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
    
    /**
     * Removes an auction
     * @param auctionID 
     */
    public void removeAuction(long auctionID){
        auction = auctionFacade.find(auctionID);
        auctionFacade.removeAuction(auction);     
    }
    
    /**
     * Will only render delete button to a finished and published auction
     * @param auctionID
     *          auction to check
     * @return boolean
     *          true if finished, else false
     */
    public boolean renderDeleteButton(long auctionID){
        auction = auctionFacade.find(auctionID);
        //if an auction is published and finished
        if(auction.isPublished() && auctionFacade.isAuctionFinished(auction)){
            return true;
        }
        return false;
    }   
}
