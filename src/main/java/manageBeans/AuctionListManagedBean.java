/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import entities.Auction;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author andre
 */
@Named(value = "auctionListManagedBean")
@SessionScoped
public class AuctionListManagedBean implements Serializable {

    @EJB
    AuctionFacade auction;
    
    /**
     * Creates a new instance of AuctionListManagedBean
     */
    public AuctionListManagedBean() {
    }
    
    public List<Auction> getAuctions() {
        return auction.findAll();
    }
    
}
