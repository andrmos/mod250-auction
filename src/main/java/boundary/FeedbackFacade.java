/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Auction;
import entities.AuctionUser;
import entities.Feedback;
import java.util.LinkedList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author oleeskild
 */
@Stateless
public class FeedbackFacade extends AbstractFacade<Feedback> {

    @PersistenceContext(unitName = "no.mod250_mod250_auction_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FeedbackFacade() {
        super(Feedback.class);
    }
    
    /**
     * Adds a new feedback to the database
     * @param feedback 
     *          feedback to be added
     */
    public void createFeedback(Feedback feedback){
        em.persist(feedback);
    }
    
    /**
     * Finds all the feedbacks connected to a seller
     * @param user
     *          to check
     * @return feedbackList
     *          list of feedbacks for a user
     */
    public LinkedList getAllFeedbacksByUser(AuctionUser user){
        LinkedList<Feedback> feedbackList = new LinkedList<Feedback>();
        LinkedList<Feedback> fullList = getAllFeedbacks();
        for(int i = 0; i < fullList.size(); i++){
            if(fullList.get(i).getAuction().getUser().equals(user)){
                feedbackList.add(fullList.get(i));
            }
        }
        return feedbackList;
    }
    
    
    /**
     * Gets all existing feedbacks
     * @return 
     *          list of feedbacks
     */
    public LinkedList getAllFeedbacks(){
        LinkedList<Feedback> feedbacks = new LinkedList<Feedback>();
        feedbacks.addAll(this.findAll());
        return feedbacks;
    }
    
    /**
     * Checks if a given auction have an existing feedback
     * @param auction 
     *          the auction to check
     * @return boolean
     *          true if feedbacks exists, false else
     */
    public boolean checkForExistingFeedback(Auction auction){
        LinkedList<Feedback> feedbacks = getAllFeedbacks();
        if(feedbacks.size() < 1){ return false; }
        for(int i = 0; i < feedbacks.size(); i++){
            //if a feedback is connected to @auction
            if(feedbacks.get(i).getAuction().equals(auction)){
                return true; //This auction have a feedback
            }  
        }
        return false; //Auction has no feedback
    }
    
    /**
     * Gets the feedback for an auction. Returns the feedback
     * if it has any, else returns null
     * @param auction
     *          auction to check
     * @return feedback/null
     *          feedback if it has, else null
     */
    public Feedback getFeedbackFromAuction(Auction auction){
       LinkedList<Feedback> feedbacks = getAllFeedbacks();
        for(int i = 0; i < feedbacks.size(); i++){
            //if a feedback is connected to @auction
            if(feedbacks.get(i).getAuction().equals(auction)){
                return feedbacks.get(i);
            }  
        }
        return null;
    }
    
    /**
     * Sets new sellers rating
     * @param user
     *          user to set new rating
     * @param newRating 
     *          the rating to add to 
     */
    public double calculateNewSellersRating(AuctionUser user, double newRating){
        LinkedList<Feedback> feedbacks = this.getAllFeedbacksByUser(user);
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
            return total;
        }else{ //if this is the first rating
            return newRating;
        }       
    }
}
