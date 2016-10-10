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
    AuctionUserFacade userFacade;
    @EJB
    FeedbackFacade feedbackFacade;
    
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
    public int getSellersRating(){
        user = userFacade.getAuctionUser(); //finds the logged in user
        if(!user.getRole().equals("seller")){
            throw new IllegalArgumentException("Only sellers have rating");
        }
        return (int) Math.round(user.getSellers_rating()); 
    }
       
    /**
     * Finds all finished auctions for a user
     * @return arr
     *          arrayList
     */
    public ArrayList<Auction> getFinishedAuctions(){
        ArrayList<Auction> arr = new ArrayList<Auction>();
        arr.addAll(this.userFacade.getFinishedAuctions());
        return arr;
    }
    
    /**
     * Finds all ongoing (current) auctions for a user
     * @return arr
     *          arrayList
     */
    public ArrayList<Auction> getCurrentAuctions(){
        ArrayList<Auction> arr = new ArrayList<Auction>();
        arr.addAll(this.userFacade.getCurrentAuctions());
        return arr;
    }
    
    /**
     * Logout method
     * @return index.xhtml
     *          redirect page after logout
     */
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "index.xhtml?faces-redirect=true";
    }
    
    /**
     * Sets new sellers rating
     * @param user
     *          user to set new rating
     * @param newRating 
     *          the rating to add to 
     */
    public void setNewSellersRating(AuctionUser user, double newRating){
        LinkedList<Feedback> feedbacks = feedbackFacade.getAllFeedbacksByUser(user);
        double counter = 0;
        double total = 0;
        //checks if the user have several ratings from beforehand
        if(feedbacks.size() > 0){
            for(int i = 0; i < feedbacks.size(); i++){
                total += feedbacks.get(i).getRating();
                counter++;
            }
            counter++;
            total = (total+newRating)/counter;
            user.setSellers_rating((int) total);
        }else{ //if this is the first rating
            user.setSellers_rating((int) newRating);
        }       
        userFacade.edit(user);
    }
    
}
