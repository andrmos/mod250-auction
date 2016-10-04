/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Auction;
import entities.AuctionUser;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.Interval;

/**
 *
 * @author oleeskild
 */
@Stateless
public class AuctionUserFacade extends AbstractFacade<AuctionUser> {

    @PersistenceContext(unitName = "no.mod250_mod250_auction_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuctionUserFacade() {
        super(AuctionUser.class);
    }
    
    /**
     * Method returns all the winning auctions of a given customer.
     * @param auctionUser
     *          customer
     * @return winningList
     *          LinkedList of winning auctions
     * 
     */
    public List getAllWinningAuctions(AuctionUser auctionUser){
        LinkedList<Integer> list = new LinkedList<Integer>();
        LinkedList<Auction> winningList = new LinkedList<Auction>();
        Auction auction;
        DateTime nowDate = new DateTime();
        DateTime dateTime = new DateTime();
        
        list.addAll(em.createQuery( //query to retrieve all bids of a customer
                "SELECT a.auction_id FROM auction AS a, auction_user AS au, bid AS b"
                        + " WHERE a.auction_id = b.auction_id AND b.auction_user_id = " + 
                        auctionUser.getId() + " AND a.bid_id = b.bid_id").getResultList());
        
        //Adding winning auctions to the winningList
        for(int i = 0; i < list.size(); i++){
           auction = em.find(Auction.class, list.get(i));
           dateTime = new DateTime(auction.getStartTime());
           dateTime.plusSeconds(auction.getDuration().intValue());
           
           if(dateTime.compareTo(nowDate) < 0){
               winningList.add(auction);
           }
       
        }
        return winningList;
    }
}
