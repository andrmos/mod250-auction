/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Auction;
import entities.AuctionUser;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.joda.time.DateTime;

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
     * Method to retrieve auction_user_id
     * @return id
     *         auction_user_id
     *      
     */
    public int getAuctionUserId(){
        String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        int id = em.createQuery(
                "SELECT A.id FROM AuctionUser AS A WHERE A.username = '" + username + "'"
        ).setMaxResults(1).getFirstResult();
        return id;
    }
    
    /**
     * Method returns all the winning auctions of a given customer.
     * @return winningList
     *          LinkedList of winning auctions
     * 
     */
    public List getAllWinningAuctions(){
        return getAuctions(true);
    }
    
    /**
     * Method returns all the current auctions of a given customer
     * @return auctionList
     *          LinkedList of current bids on auctions
     */
    public List getAllCurrentBids(){
       return getAuctions(false);
    }
    
    
    public List getAuctions(boolean isOver){
        int id = getAuctionUserId();
        LinkedList<Integer> list = new LinkedList<Integer>();
        LinkedList<Auction> auctionList = new LinkedList<Auction>();
        Auction auction;
        DateTime nowDate = new DateTime();
        DateTime dateTime = new DateTime();
        
        
        AuctionUser user = em.find(AuctionUser.class, Long.valueOf(id));
        
        list.addAll(em.createQuery( //query to retrieve all bids
                "SELECT b.auction.id FROM Bid as b WHERE b.auctionUser.id = 78" //user.getId()
        ).getResultList());
        
        
        //Adding winning auctions to the winningList
        for(int i = 0; i < list.size(); i++){
           auction = em.find(Auction.class, list.get(i));
           dateTime = new DateTime(auction.getStartTime());
           dateTime.plusSeconds(auction.getDuration().intValue());
           
           if(isOver){
                if(dateTime.compareTo(nowDate) < 0){
                    auctionList.add(auction);
                }
           }else{
               if(dateTime.compareTo(nowDate) > 0){
                    auctionList.add(auction);
                }
           }
        }
        return auctionList;
    
    }
    
}
