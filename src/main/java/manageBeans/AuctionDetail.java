/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import boundary.AuctionUserFacade;
import boundary.BidFacade;
import boundary.FeedbackFacade;
import entities.Auction;
import entities.AuctionUser;
import entities.Bid;
import entities.ContactInfo;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import jms.SendMessage;

/**
 *
 * @author oleeskild
 */
@Named(value = "auctionDetail")
@ViewScoped
public class AuctionDetail extends UIInput implements Serializable {

    @EJB
    private AuctionFacade auctionFacade;
    @EJB
    private AuctionUserFacade auctionUserFacade;
    @EJB
    private BidFacade bidFacade;
    @EJB
    private FeedbackFacade feedbackFacade;
   
    private Bid bid;
    private Auction auction;
    private int auctionId;

    @PostConstruct
    public void initialize(){
        this.bid = new Bid();
        this.bid.setAmount(0);
    }
    
    public AuctionFacade getAuctionFacade() {
        return auctionFacade;
    }

    public void setAuctionFacade(AuctionFacade auctionFacade) {
        this.auctionFacade = auctionFacade;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
        this.auction = auctionFacade.find(Long.valueOf(auctionId));
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    /**
     * Creates a new instance of AuctionDetail
     */
    public AuctionDetail() {
    }
    
    public String getProductTitle(){
        return this.auction.getProduct().getProductName();
    }
    
    public String getProductImage(){
        return this.auction.getProduct().getPicturePath();
    }
    
    public String getProductDesciption(){
        return this.auction.getProduct().getDescription();
    }
    
    /**
     * Gets the auctions current price
     * 
     * @return bid amount
     */
    public double getCurrentPrice(){
        Bid bid = this.auction.getBid();
        if(bid == null){
            return this.auction.getInitPrice();
        }else{
            return bid.getAmount();
        }
    }
    
    /**
     * Gets the auction owners name if the user has registered contact 
     * information, else returns username as name
     * 
     * @return string
     *          username/fullname
     */
    public String getAuctionOwnerName(){
        ContactInfo contactInfo = this.auction.getUser().getContactinfo();
        if(contactInfo == null){
            return this.auction.getUser().getUsername();
        }else{
            return contactInfo.getName();
        }               
    }
    
    /**
     * Gets the sellers rating. Converting the rating from double to int
     * 
     * @return sellers_rating
     */
    public int getSellerRating(){
        return (int)this.auction.getUser().getSellers_rating();
    }  
    
    /**
     * Saves bid changes
     */
    public void saveBid(){
        Bid currentBid = this.auction.getBid();
        if(currentBid != null){
            if(this.bid.getAmount() <= currentBid.getAmount()){
                return;
            }
        }
        bid.setBidDate(new Date());
        bid.setAuction(this.auction);
        AuctionUser user = auctionUserFacade.getAuctionUser();
        if(user != null){
            bid.setAuctionUser(user);
        }
        bidFacade.create(bid);
        this.auction.setBid(bid);
        this.auctionFacade.edit(auction);
        
        if(currentBid != null){
            this.bidFacade.remove(currentBid);
        }
        
        SendMessage sm = new SendMessage();
        
        String productName = this.auction.getProduct().getProductName();
        String username = this.bid.getAuctionUser().getUsername();
        String auctionId = this.auction.getId().toString();
        sm.sendMessage(productName + ":" + username + ":" + auctionId);
        
        //reload page
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try{
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI() + "?auctionId=" + this.auction.getId());
        } catch(Exception e){
            
        }
    }
    
    /**
     * Checks if a user is logged in
     * @return boolean
     *          true if logged in, else false
     */
    public boolean isLoggedIn(){
        return this.auctionUserFacade.getAuctionUser() != null; 
    }
    
    /**
     * Gets the remaining time left of an auction
     * 
     * @return int
     *          timeleft
     */
    public int getSecondsLeft(){
        if(this.auction.isPublished()){
            return auctionFacade.getTimeLeftInSeconds("" + this.auction.getId());
        }
        return 0;
    }

    /**
     * Gets the number of days left of an auction
     * 
     * @return int
     *          number of days
     */
    public int getDaysLeft(){
        if(this.auction.isPublished()){
            return auctionFacade.getNumberOfDaysUntilDeadline("" + this.auction.getId());
        }
        return 0;
    }
    
    /**
     * Checks if an auction is active. 
     * It's active if the timeleft is larger than zero
     * @return boolean
     *          true if active, else false
     */
    public boolean isActive(){
       boolean isActive = (this.auctionFacade.getTimeLeftInSeconds(""+this.auction.getId()) >0);// && this.auction.isPublished() ;
       return isActive;
    }
    
    /**
     * Gets the rating from a feedback for an auction
     * @param auctionID
     *         auction
     * @return rating
     *         an auctions rating
     *          
     */
    public int getRatingFromAuction(long auctionID){
        auction = auctionFacade.find(auctionID);
        double rating = feedbackFacade.getFeedbackFromAuction(auction).getRating();
        return (int) rating;
    }
    
    /**
     * Gets the comment from a feedback for an auction
     * @param auctionID
     *          auction
     * @return String
     *          the comment
     */
    public String getCommentFromAuction(long auctionID){
        auction = auctionFacade.find(auctionID);
        return feedbackFacade.getFeedbackFromAuction(auction).getComment();
    }
    
}
