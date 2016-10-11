/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import boundary.AuctionUserFacade;
import boundary.FeedbackFacade;
import entities.Auction;
import entities.AuctionUser;
import entities.Feedback;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import support.AuctionSupport;

/**
 *
 * @author DidrikKvanvik 
 */

@Named(value = "rateProductManagedBean")
@ViewScoped
public class RateProductManagedBean implements Serializable{

    private Feedback feedback;
    
    @EJB
    FeedbackFacade feedbackFacade;
    @EJB
    AuctionUserFacade userFacade;
    @EJB
    AuctionFacade auctionFacade;
    
    AuctionUserView auctionUserView;
    private Double rating;
    private String comment;
    private Auction auction;
    private AuctionUser user;
    
    /**
     * Creates a new instance of RateProductManagedBean
     */
    public RateProductManagedBean() {
        feedback = new Feedback();
    }
    
    public Double getRating() {
        return rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    /**
     * Adds the new feedback for an auction
     * @param auctionID 
     *          auction to add feedback to
     */
    public void addFeedback(long auctionID){
        auction = auctionFacade.find(auctionID);
        //Adds feedback if there doesnt exists a feedback
        if(!feedbackFacade.checkForExistingFeedback(auction)){
            user = userFacade.getAuctionUser();
            //connects feedback to an auction and user
            feedback.setAuction(auction);
            feedback.setUser(user);
            double totalRating = feedbackFacade.setNewSellersRating(user, feedback.getRating()); //sets new sellers rating
            userFacade.setNewRating(user, totalRating);
            feedbackFacade.createFeedback(feedback); //creates new feedback
            
        }
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
    
    /**
     * Method checks if rate button is rendered to a customer, only
     * if they have not rated it from beforehand.
     * @param auctionID
     *          auction to check
     * @return boolean 
     *          true if no existing feedback, else false
     */
    public boolean renderFeedback(long auctionID){
        user = userFacade.getAuctionUser();
        auction = auctionFacade.find(auctionID);
        
        //If there is no user logged on
        if(user == null){
            return false;
        }
        //is auction finished, published and does user have highest bid check
        try{
            if(AuctionSupport.isAuctionFinished(auction) && user.equals(auction.getBid().getAuctionUser())){
                if(feedbackFacade.checkForExistingFeedback(auction)){
                    //Feedback for auction exists, do not render rating
                    return false; 
                }else{
                    return true;
                }
            }
        }catch(NullPointerException ex){
            return false;
        }
        //No feedback exists, render rating    
        return false;
    } 
}
