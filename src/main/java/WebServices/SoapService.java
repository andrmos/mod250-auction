/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import boundary.AuctionFacade;
import boundary.AuctionUserFacade;
import entities.Auction;
import entities.AuctionUser;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import entities.Bid;
import java.util.Calendar;
import java.util.Objects;




/**
 *
 * @author DidrikKvanvik
 */


@WebService(serviceName = "SoapService")

public class SoapService {
    
    @EJB
    AuctionFacade auctionFacade;
    
    @EJB
    AuctionUserFacade auctionUserFacade;
           
    @WebMethod(operationName = "activeAucitons")
    public List<Auction> getMyActiveAuctions(){
       return auctionFacade.getActiveAuctions();
        
    }
    
    @WebMethod(operationName="setBid")
    public String setBid(Double amount, long auctionId, long userID){   
        AuctionUser auctionUser=auctionUserFacade.find(userID);
        Bid bid=new Bid();
        try{            
            Auction auction= auctionFacade.find(auctionId);            
                if(auctionFacade.getActiveAuctions().contains(auction)){
                    if(amount>auction.getBid().getAmount()&&
                            amount>auction.getInitPrice()){
                        bid.setAmount(amount);
                        bid.setAuction(auction);
                        bid.setAuctionUser(auctionUser);
                        bid.setBidDate(Calendar.getInstance().getTime());
                        auction.setBid(bid);
                        auctionFacade.edit(auction);
                        return "Customer " + bid.getAuctionUser().getUsername() + "'s bid "
                                + "has been successfully placed for product " + bid.getAuction().getProduct();
                    }   
                    else
                        return "The bid for product " + bid.getAuction().getProduct() + " has not been placed"
                                + "for Customer " + bid.getAuctionUser();
                }                                        
            return "failure unknown";
        }
        catch(Exception e){
         return "failure exception: "+e.getMessage();
        }        
    }
}