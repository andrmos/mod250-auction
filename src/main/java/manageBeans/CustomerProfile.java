/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionUserFacade;
import entities.AuctionUser;
import entities.ContactInfo;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author oleeskild
 */
@Named(value = "customerProfile")
@ViewScoped
public class CustomerProfile implements Serializable{
    
    @EJB
    AuctionUserFacade auctionUserFacade;

    /**
     * Creates a new instance of CustomerProfile
     */
    public CustomerProfile() {
    }
    
    public String getFullName(){
        AuctionUser user = this.auctionUserFacade.getAuctionUser();
        ContactInfo contactInfo = user.getContactinfo();
        if(contactInfo == null){
            return user.getUsername();
        }
        return contactInfo.getName();
               
    }
    
    
}
