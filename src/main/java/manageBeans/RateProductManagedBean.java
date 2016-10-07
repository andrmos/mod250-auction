/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import boundary.FeedbackFacade;
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

    @EJB
    FeedbackFacade feedbackFacade;
    AuctionFacade auction;
    Feedback feedback = new Feedback();

    private Double rating;
    private String comment;
    
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
    
    public void addFeedback(Feedback feedback){
       
       //feedbackFacade.addFeedback(feedback);
    }
    
}
