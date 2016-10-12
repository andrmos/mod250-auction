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
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author oleeskild
 */
@Named(value = "auctionUserView")
@ViewScoped
public class AuctionUserView implements Serializable {
    
    @EJB
    AuctionUserFacade userFacade;
    @EJB
    FeedbackFacade feedbackFacade;
    
    private AuctionUser user;
    private ArrayList<Auction> finishedAuctions;
    boolean finishedAuctionIsSet;
    private ArrayList<Auction> currentAuctions;
    boolean currentAuctionIsSet;
    
    /**
     * Creates a new instance of AuctionUserView
     */
    public AuctionUserView() {
    }
    
    @PostConstruct
    public void init(){
        this.finishedAuctionIsSet = false;
        this.currentAuctionIsSet = false;
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
     * @return sellers_rating
     */
    public int getSellersRating(){
        user = userFacade.getAuctionUser(); //finds the logged in user
        if(!user.getRole().equals("seller")){
            throw new IllegalArgumentException("Only sellers have rating");
        }
        return (int) Math.round(user.getSellers_rating()); 
    }
     
    public boolean hasFinishedAuctions(){
        if(!this.finishedAuctionIsSet){
            this.finishedAuctions = new ArrayList<Auction>();
            finishedAuctions.addAll(this.userFacade.getFinishedAuctions());
            this.finishedAuctionIsSet = true;
        }
        return finishedAuctions.size() > 0;
    } 
    
    public boolean hasCurrentAuctions(){
        if(!this.currentAuctionIsSet){
            this.currentAuctions = new ArrayList<Auction>();
            this.currentAuctions.addAll(this.userFacade.getCurrentAuctions());
            this.currentAuctionIsSet = true;
        }
        return this.currentAuctions.size() > 0;
    }
    
    /**
     * Finds all finished auctions for a user
     * @return arr
     *          arrayList
     */
    public ArrayList<Auction> getFinishedAuctions(){
        if(!this.finishedAuctionIsSet){
            this.hasFinishedAuctions();
        }
        return this.finishedAuctions;
    }
    
    /**
     * Finds all ongoing (current) auctions for a user
     * @return arraylist
     *          arrayList
     */
    public ArrayList<Auction> getCurrentAuctions(){
        if(!this.currentAuctionIsSet){
            this.hasCurrentAuctions();
        }
        return this.currentAuctions;
    }
}
