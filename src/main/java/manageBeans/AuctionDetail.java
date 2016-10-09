/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import boundary.AuctionUserFacade;
import boundary.BidFacade;
import entities.Auction;
import entities.AuctionUser;
import entities.Bid;
import entities.ContactInfo;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;

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
   
    private Bid bid;
    private Auction auction;
    private int auctionId;

    @PostConstruct
    public void initialize(){
        System.out.println("INITIALISERER");
        this.bid = new Bid();
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
    
    public double getCurrentPrice(){
        Bid bid = this.auction.getBid();
        if(bid == null){
            return this.auction.getInitPrice();
        }else{
            return bid.getAmount();
        }
    }
    
    public String getAuctionOwnerName(){
        ContactInfo contactInfo = this.auction.getUser().getContactinfo();
        if(contactInfo == null){
            return this.auction.getUser().getUsername();
        }else{
            return contactInfo.getName();
        }               
    }
    
    public int getSellerRating(){
        return (int)this.auction.getUser().getSellers_rating();
    }
    
    public void saveBid(){
        if(this.bid.getAmount() <= this.auction.getBid().getAmount()){
            return;
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
        
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try{
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI() + "?auctionId=" + this.auction.getId());
        } catch(Exception e){
            
        }
    }
    
    public boolean isLoggedIn(){
        return this.auctionUserFacade.getAuctionUser() != null; 
    }
    
}
