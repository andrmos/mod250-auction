/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Auction;
import entities.AuctionUser;
import java.util.HashSet;
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
     * Retrieves the current session user
     * @return user
     *          null if the user is not logged in
     * 
     */
    public AuctionUser getAuctionUser(){
        
       //finds the username of the logged in user
        String username = "";
        try{
            username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        }catch(NullPointerException e){
            return null;
        }

        LinkedList<AuctionUser> users = new LinkedList<AuctionUser>();
        users.addAll(this.findAll());
        
        //Checks and finds user object equal to the given username
        for(AuctionUser user : users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null; //should not happen
    }
    
    public int getSellerRating(AuctionUser seller){
        return (int) seller.getSellers_rating();
    }
    
    public String getProfileName(AuctionUser seller){
        try{
        if(seller.getContactinfo().getName()!=null){
            return seller.getContactinfo().getName();
        }  
        return "No username";
        }
        catch(Exception e){
        return "no username";
        }
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
        LinkedList<Integer> list = new LinkedList<Integer>();
        HashSet<Auction> auctionList = new HashSet<Auction>();
        Auction auction;
        DateTime nowDate = new DateTime();
        DateTime dateTime = new DateTime();
       
        AuctionUser user = getAuctionUser();
        
        if(user != null){
           if(user.getRole().equals("customer")){
                list.addAll(em.createQuery( //query to retrieve all auctions with bids
                    "SELECT b.auction.id FROM Bid as b WHERE b.auctionUser.id =" + user.getId()
                ).getResultList());
            
            }else if(user.getRole().equals("seller")){
                list.addAll(em.createQuery( //query to retrieve all auctions
                 "SELECT a.id FROM Auction as a WHERE a.user.id =" + user.getId()
                ).getResultList());
            }
        }else{
            list.addAll(em.createQuery( //query to retrieve all auctions
                 "SELECT a.id FROM Auction as a WHERE a.user.id =" + user.getId()
            ).getResultList());
        }
        
        //Adding winning auctions to the winningList
        for(int i = 0; i < list.size(); i++){
           auction = em.find(Auction.class, list.get(i));
           dateTime = new DateTime(auction.getStartTime());
           dateTime = dateTime.plusSeconds(auction.getDuration().intValue());
           
           if(isOver){ //if auction is done
                if(dateTime.compareTo(nowDate) <= 0){
                    auctionList.add(auction);
                }
           }else{ //if auction is still ongoing
               if(dateTime.compareTo(nowDate) > 0){
                    auctionList.add(auction);
                }
           }
        }
        
        LinkedList<Auction> listOfAuctions = new LinkedList<Auction>();
        listOfAuctions.addAll(auctionList); 
        return listOfAuctions;
    }
    
    public void setNewRating(AuctionUser user, double rating){
        user.setSellers_rating((int) rating);
        this.edit(user);
    }    
    
 }
