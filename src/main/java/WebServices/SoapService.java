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
        return "sucess";        
    }
}