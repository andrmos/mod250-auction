/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import entities.Auction;
import entities.Bid;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.component.UIInput;

/**
 *
 * @author oleeskild
 */
@Named(value = "auctionDetail")
@SessionScoped
public class AuctionDetail extends UIInput implements Serializable {

    @EJB
    private AuctionFacade auctionFacade;
    
    private Auction auction;
    private int auctionId;

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
    
}
