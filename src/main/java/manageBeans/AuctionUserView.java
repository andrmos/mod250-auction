/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionUserFacade;
import boundary.FeedbackFacade;
import entities.Auction;
import entities.AuctionUser;
import entities.Feedback;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


/**
 *
 * @author oleeskild
 */
@Named(value = "auctionUserView")
@SessionScoped
public class AuctionUserView implements Serializable {

    
    @EJB
    private AuctionUserFacade userFacade;
    @EJB
    private FeedbackFacade feedbackFacade;
    private AuctionUser user;
    
    /**
     * Creates a new instance of AuctionUserView
     */
    public AuctionUserView() {
    }

    public AuctionUser getUser() {
        return user;
    }

    public void setUser(AuctionUser user) {
        this.user = user;
    }
    
    /**
     * Method gets the users full name if they have one, else their username
     * @return user's full name
     */
    public String getUserFullname(){
        user = userFacade.getAuctionUser();
        if(user.getContactinfo() == null){
            return user.getUsername();
        }
        return user.getContactinfo().getName();
    }
     
    
    /**
     * Method to get the sellers rating
     * @return 
     */
    public double getSellersRating(){
        user = userFacade.getAuctionUser(); //finds the logged in user
        if(!user.getRole().equals("seller")){
            throw new IllegalArgumentException("Only sellers have rating");
        }
        return user.getSellers_rating();
    }
    
       
    public ArrayList<Auction> getFinishedAuctions(){
        ArrayList<Auction> arr = new ArrayList<Auction>();
        arr.addAll(this.userFacade.getFinishedAuctions());
        return arr;
    }
    
    public ArrayList<Auction> getCurrentAuctions(){
        ArrayList<Auction> arr = new ArrayList<Auction>();
        arr.addAll(this.userFacade.getCurrentAuctions());
        return arr;
    }
    
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "index.xhtml?faces-redirect=true";
    }
    
    
    /**
     * Sets new sellers rating
     * @param rating 
     */
    public void setNewSellersRating(double newRating){
        double oldRating = getUser().getSellers_rating();
        double totalRating = (oldRating+newRating)/2;
        getUser().setSellers_rating((int) totalRating);
    }
    
}
