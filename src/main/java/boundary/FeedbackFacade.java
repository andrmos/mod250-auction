/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Auction;
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
    
    public void createFeedback(Feedback feedback){
        em.persist(feedback);
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
        for(int i = 0; i < feedbacks.size(); i++){
            if(auction.getId().equals(feedbacks.get(i).getId())){
                return true; //This auction have a feedback
            }  
        }
        return false; //there is no feedback
    }
}
