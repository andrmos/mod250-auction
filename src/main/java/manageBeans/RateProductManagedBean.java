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
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;


/**
 *
 * @author DidrikKvanvik
 */
@Named(value = "rateProductManagedBean")
@RequestScoped
public class RateProductManagedBean {

    private Feedback feedback;
    
    @EJB
    FeedbackFacade feedbackFacade;
    @EJB
    AuctionUserFacade userFacade;
    @EJB
    AuctionFacade auctionFacade;
    
    
    private Double rating;
    private String comment;
    private Auction auction;
    
    /**
     * Creates a new instance of RateProductManagedBean
     */
    public RateProductManagedBean() {
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
    
    public void addFeedback(long auctionID){
        auction = auctionFacade.find(auctionID);
        feedback.setAuction(auction);
        feedback.setUser(userFacade.getCurrentUser());
        
        feedbackFacade.createFeedback(feedback);
    }
    
}
