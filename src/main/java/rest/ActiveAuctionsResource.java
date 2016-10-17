/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import boundary.AuctionFacade;
import entities.Auction;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import support.JsonConverter;

/**
 * REST Web Service
 *
 * @author andre
 */
@Path("auctions/active")
public class ActiveAuctionsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HelloWorld
     */
    public ActiveAuctionsResource() {
    }
    
    @EJB
    private AuctionFacade auctionFacade;

    /**
     * Returns current active auctions
     * 
     * Accessed from: http://localhost:8080/mod250_auction/webresources/auctions/active
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getActiveAuctions() {
        List<Auction> auctionList = auctionFacade.getActiveAuctions();
        
        System.out.println("Got rest hit at /webresources/auctions/active");
        return JsonConverter.toJson(auctionList.get(3)).toString();
    }

    /**
     * PUT method for updating or creating an instance of HelloWorld
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public void putText(String content) {
        // TODO
    }
}
