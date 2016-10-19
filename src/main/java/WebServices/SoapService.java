/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import boundary.AuctionFacade;
import boundary.BidFacade;
import entities.Auction;
import entities.AuctionUser;
import entities.Bid;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import org.glassfish.gmbal.logex.Message;

/**
 *
 * @author DidrikKvanvik
 */

@Stateless
@WebService(serviceName = "SoapService")
@SOAPBinding(style = Style.RPC)
public class SoapService {
    
    @EJB
    AuctionFacade auctionFacade;
           
    @WebMethod(operationName = "activeAucitons")
    public String getMyActiveAuctions(){
        List<Auction> auctions = auctionFacade.getActiveAuctions();
        //StringBuilder sb = new StringBuilder();
        //for(int i = 0; i < auctions.size(); i++){
        //    sb.append(auctions.get(i).toString());
        //}
        //System.out.println(sb.toString());
        return "HELLO";
    }

}