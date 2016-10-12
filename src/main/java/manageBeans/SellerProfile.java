/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import boundary.AuctionUserFacade;
import entities.AuctionUser;
import entities.ContactInfo;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import support.AuctionSupport;

/**
 *
 * @author oleeskild
 */
@Named(value = "sellerProfile")
@ViewScoped
public class SellerProfile implements Serializable{
    
    @EJB
    AuctionUserFacade auctionUserFacade;
    @EJB
    AuctionFacade auctionFacade;

    /**
     * Creates a new instance of CustomerProfile
     */
    public SellerProfile() {
    }
    
    /**
     * Gets the auction users full name if they have contact information
     * records, else returns their username as their fullname
     * @return string
     *          username/fullname
     */
    public String getFullName(){
        AuctionUser user = this.auctionUserFacade.getAuctionUser();
        ContactInfo contactInfo = user.getContactinfo();
        if(contactInfo == null){
            return user.getUsername();
        }
        return contactInfo.getName();
               
    }
    
    /**
     * Gets the current price of an auction based on the auction id
     * @param auctionId
     *          auction
     * @return current highest bid
     */
    public double getPrice(String auctionId){
        return AuctionSupport.getCurrentPrice(auctionFacade.find(Long.valueOf(auctionId)));
    }
    
    
}
