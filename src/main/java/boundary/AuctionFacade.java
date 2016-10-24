/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;


import entities.Auction;
import enums.Category;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import support.AuctionSupport;

/**
 *
 * @author oleeskild
 */
@Stateless
public class AuctionFacade extends AbstractFacade<Auction> {

    @PersistenceContext(unitName = "no.mod250_mod250_auction_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuctionFacade() {
        super(Auction.class);
    }
    
    /**
     * Removes and auction from the database
     * @param auciton 
     *          auction to be removed
     */
    public void removeAuction(Auction auciton){
        em.remove(auciton);
    }
    
    /**
     * Method to check if an auction is finished. Does not check if published.
     * @param auction
     *          auction to check
     * @return boolean 
     *          true is finished, else false
     */
    public boolean isAuctionFinished(Auction auction){
        List<Auction> list = getActiveAuctions();
        if(list.contains(auction)){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Retrieves a list of auctions based on a category
     * @param category
     *          group specification
     * @return list of auctions
     */
    public List<Auction> getAuctionByCategory(Category category){
        return filterAuctionListByCategory(findAll(), category);        
    }

    /**
     * Filters an auction list based by category
     * @param auctionList
     *          list of auctions to be filtered 
     * @param category
     *          category type
     * @return list
     *          sorted auction list based on time
     */
    private List<Auction> filterAuctionListByCategory(List<Auction> auctionList, Category category) {
        List<Auction> tempList= new ArrayList<>();
        for(int i=0;i<auctionList.size();i++){
            if(auctionList.get(i).getProduct().getCategory()==category){
                tempList.add(auctionList.get(i));
            }
        }
        return AuctionSupport.sortAuctionsBasedOnTime(tempList);
    }
    
    /**
     * Gets list of auctions based on a keyword
     * @param keyword
     *          string search word
     * @return list of auctions
     */
    
    public List<Auction> getAuctionsByKeyword(String keyword){
        return filterAuctionListByKeywords(findAll(), keyword);
    }
    
    /**
     * Gets a list of auctions by a keyword and a category
     * @param keyword
     *          string search word
     * @param category
     *          category type
     * @return list of auctions
     */
    public List<Auction> getAuctionsByKeywordAndCategory(String keyword, Category
           category){       
       return filterAuctionListByKeywords(getAuctionByCategory(category), keyword);
    }

    /**
     * Filters a list of auctions by a keyword
     * @param auctionList
     *          list of auctions
     * @param keyword
     *          string search word
     * @return list of auctions
     */
    private List<Auction> filterAuctionListByKeywords(List<Auction> auctionList, String keyword) {
        List<Auction> tempList= new ArrayList<>();
        for(int i=0; i<auctionList.size(); i++){
            if(auctionList.get(i).getProduct().getProductName().contains(keyword)||
                    auctionList.get(i).getProduct().getDescription().contains(keyword)){
                tempList.add(auctionList.get(i));
            }
        }
        return AuctionSupport.sortAuctionsBasedOnTime(tempList);
    }
   
    /**
     * Gets all active auctions by a keyword
     * @param keyword
     *          String search word
     * @return list of auctions
     */
    public List<Auction> getActiveAuctionsByKeyword(String keyword){
        return filterAuctionListByKeywords(getActiveAuctions(), keyword);
    }
    
    /**
     * Gets all active auctions by a category
     * @param category
     *          category type
     * @return list of auctions
     */
    public List<Auction> getActiveAuctionsByCategory(Category category){
        return filterAuctionListByCategory(getActiveAuctions(), category);        
    }
    
    /**
     * Gets active auctions by a keyword and a category
     * @param keyword
     *          string search word
     * @param category
     *          category type
     * @return list of auctions
     */
    public List<Auction> getActiveAuctionsByKeywordAndCategory(String keyword, Category
           category){       
        return filterAuctionListByKeywords(getActiveAuctionsByCategory(category), keyword);
    }
   
    /**
     * Gets all active (ongoing) auctions
     * @return list of auctions
     */
    public List<Auction> getActiveAuctions(){
        List<Auction> tempList= new ArrayList<>();
        for(int i=0; i< findAll().size();i++){
            if(findAll().get(i).isPublished() && !AuctionSupport.isAuctionFinished(findAll().get(i))){
               tempList.add(findAll().get(i));
            }
        }
        return tempList;
    }
   
    /**
     * Gets all active auctions based upon a user's identification
     * @param id
     *          user id
     * @return list of auctions
     */
    public List<Auction> getActiveAuctionsBasedOnUserID(String id){    
        List<Auction> tempList= new ArrayList<>();
        for(int i= 0; i<getActiveAuctions().size();i++){
            if(getActiveAuctions().get(i).getUser().getId()==Long.parseLong(id, 10)){
                tempList.add(getActiveAuctions().get(i));
            }
        }
        return AuctionSupport.sortAuctionsBasedOnTime(tempList);
    }
    
    /**
     * Gets all finished auctions based on upon a user's identification
     * @param id
     *          user id
     * @return list of auctions
     */
    public List<Auction> getFinishedAuctionsBasedOnUserID(String id){    
       /*TODO
       Check if published, and if times up      
       */
       
        List<Auction> tempList= new ArrayList<>();
        for(int i= 0; i<findAll().size();i++){
            if(findAll().get(i).getUser().getId()==Long.parseLong(id, 10) && 
                  AuctionSupport.isAuctionFinished(findAll().get(i))){
                tempList.add(findAll().get(i));
            }
        }
        return AuctionSupport.sortAuctionsBasedOnTime(tempList); 
    }   
   
    /**
     * Gets the timeleft from a id
     * @param id
     * @return seconds left
     */
    public int getTimeLeftInSeconds(String id){       
        int numberOfDays=getNumberOfDaysUntilDeadline(id);
        int secondsLeft=(AuctionSupport.secondsToAuctionIsFinished(find(Long.parseLong(id,10)))-(numberOfDays*86400));
        System.out.println(secondsLeft+"så mange sekunder");
        return secondsLeft;       
    }      

    /**
     * Gets the number of days left until deadline
     * @param id
     * @return numberOfDays 
     *          the number of days left
     * @throws NumberFormatException 
     */
    public int getNumberOfDaysUntilDeadline(String id) throws NumberFormatException {
        Double numberOfDays=Math.floor(AuctionSupport.secondsToAuctionIsFinished(find(Long.parseLong(id,10)))/86400);
        System.out.println(numberOfDays+"så mange dager");
        return  numberOfDays.intValue();
    }
    
    /**
     * gets the sorted version of an auction list
     * @param auctions
     *          auction list to get the sorted version of
     * @return sorted list of auctions
     */
    public List<Auction> getSortedAuctions(List<Auction> auctions){
        return AuctionSupport.sortAuctionsBasedOnTime(auctions);
    }
}
