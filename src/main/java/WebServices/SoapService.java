/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import boundary.AuctionFacade;
import entities.Auction;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import entities.Bid;
import java.util.Objects;




/**
 *
 * @author DidrikKvanvik
 */


@WebService(serviceName = "SoapService")

public class SoapService {
    
    @EJB
    AuctionFacade auctionFacade;
           
    @WebMethod(operationName = "activeAucitons")
    public List<Auction> getMyActiveAuctions(){
       return auctionFacade.getActiveAuctions();
        
    }
    
    @WebMethod(operationName="setBid")
    public String setBid(Bid bid){        
        try{
            List<Auction> templist=auctionFacade.getActiveAuctions();
            for(int i=0; i<templist.size();i++){
                if(Objects.equals(bid.getAuction().getId(), templist.get(i).getId())){
                    if(bid.getAmount()>templist.get(i).getBid().getAmount()&&
                            bid.getAmount()>templist.get(i).getInitPrice()){
                        templist.get(i).setBid(bid);
                        return "Customer " + bid.getAuctionUser().getUsername() + "'s bid "
                                + "has been successfully placed for product " + bid.getAuction().getProduct();
                    }   
                    else
                        return "The bid for product " + bid.getAuction().getProduct() + " has not been placed"
                                + "for Customer " + bid.getAuctionUser();
                }                
            }            
            return "failure unknown";
        }
        catch(Exception e){
         return "failure exception: "+e.getMessage();
        }        
    }
}