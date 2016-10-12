/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import boundary.AuctionUserFacade;
import entities.Auction;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import entities.AuctionUser;

/**
 *
 * @author Andreas
 */
@Named(value = "sellerCatalougeMB")
@ViewScoped
public class SellerCatalougeMB implements Serializable {

    /**
     * Creates a new instance of SellerCatalougeMB
     */
    @EJB
    AuctionFacade auctionFacade;
    @EJB
    AuctionUserFacade auctionUserFacade;        
    private List<Auction> activeAuctions;
    private String userID;    
   
    

    
    
    public SellerCatalougeMB() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
        .getRequest();
        userID=request.getParameter("uID");       
    }    
        
    
    public List<Auction> getActiveAuctions() {
        System.out.println(userID);
        return auctionFacade.getActiveAuctionsBasedOnUserID(userID);
    }
    public List<Auction> getFinishedAuctions() {
        return auctionFacade.getFinishedAuctionsBasedOnUserID(userID);
    }   
    
    public String getProfileName(){
     return auctionUserFacade.getProfileName(auctionUserFacade.find(Long.valueOf(userID)));
    }
    public int getUserRating() {        
        return auctionUserFacade.getSellerRating(auctionUserFacade.find(Long.valueOf(userID)));
    }
}
