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
    
    private AuctionUser user;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuctionUserFacade() {
        super(AuctionUser.class);
    }
    
    /**
     * Finds the current user
     * @return user
     */
    public AuctionUser getCurrentUser(){
        long id = this.getAuctionUserId();
        user = this.find(id);
        return user;
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
     * Method returns all the finished auctions.
     * @return winningList
     *          LinkedList of finished auctions
     * 
     */
    public List getFinishedAuctions(){
        return getAuctions(true);
    }
    
    /**
     * Method returns all the current ongoing auctions
     * @return auctionList
     *          LinkedList of current bids on auctions
     */
    public List getCurrentAuctions(){
       return getAuctions(false);
    }
         
    
    /**
     * Method returns a list of auctions. If '@isOver' is true and the user
     * is a customer, it returns a list of auctions the customer has won, else if
     * false it returns auctions ongoing the customer has bid on. 
     * If '@isOver' is true and user is seller, method returns a list of finished
     * auctions published by the seller, else a list of ongoing auctions. 
     * 
     * @param isOver
     *        boolean variable to check if auction is finished
     * @return 
     *      LinkedList of auctions
     */
    public List getAuctions(boolean isOver){
        int id = getAuctionUserId();
        LinkedList<Integer> list = new LinkedList<Integer>();
        LinkedList<Auction> auctionList = new LinkedList<Auction>();
        Auction auction;
        DateTime nowDate = new DateTime();
        DateTime dateTime = new DateTime();
       
        AuctionUser user = em.find(AuctionUser.class, Long.valueOf(id));
        
        if(user != null){
           if(user.getRole().equals("customer")){
                list.addAll(em.createQuery( //query to retrieve all auctions with bids
                    "SELECT b.auction.id FROM Bid as b WHERE b.auctionUser.id = 78" //user.getId()
                ).getResultList());
            
            } 
        }else{
            list.addAll(em.createQuery( //query to retrieve all auctions
                 "SELECT a.id FROM Auction as a WHERE a.user.id = 2" //+ user.getId()
            ).getResultList());
        }
        
        //Adding winning auctions to the winningList
        for(int i = 0; i < list.size(); i++){
           auction = em.find(Auction.class, list.get(i));
           dateTime = new DateTime(auction.getStartTime());
           dateTime.plusSeconds(auction.getDuration().intValue());
           
           if(isOver){ //if auction is done
                if(dateTime.compareTo(nowDate) < 0){
                    auctionList.add(auction);
                }
           }else{ //if auction is still ongoing
               if(dateTime.compareTo(nowDate) > 0){
                    auctionList.add(auction);
                }
           }
        }
        return auctionList; 
    }
 }
