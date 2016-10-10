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
import org.joda.time.DateTime;
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
    
    public List<Auction> getAuctionByCategory(Category category){
        return filterAuctionListByCategory(findAll(), category);        
    }

    private List<Auction> filterAuctionListByCategory(List<Auction> auctionList, Category category) {
        List<Auction> tempList= new ArrayList<>();
        for(int i=0;i<auctionList.size();i++){
            if(auctionList.get(i).getProduct().getCategory()==category){
                tempList.add(auctionList.get(i));
            }
        }
        return tempList;
    }
    
    public List<Auction> getAuctionsByKeyword(String keyword){
        return filterAuctionListByKeywords(findAll(), keyword);
    }
    
   public List<Auction> getAuctionsByKeywordAndCategory(String keyword, Category
           category){       
       return filterAuctionListByKeywords(getAuctionByCategory(category), keyword);
   }

    private List<Auction> filterAuctionListByKeywords(List<Auction> auctionList, String keyword) {
        List<Auction> tempList= new ArrayList<>();
        for(int i=0; i<auctionList.size(); i++){
            if(auctionList.get(i).getProduct().getProductName().contains(keyword)||
                    auctionList.get(i).getProduct().getDescription().contains(keyword)){
                tempList.add(auctionList.get(i));
            }
        }
        return tempList;
    }
   
    public List<Auction> getActiveAuctionsByKeyword(String keyword){
        return filterAuctionListByKeywords(getActiveAuctions(), keyword);
    }
    
    public List<Auction> getActiveAuctionsByCategory(Category category){
        return filterAuctionListByCategory(getActiveAuctions(), category);        
    }
    
     public List<Auction> getActiveAuctionsByKeywordAndCategory(String keyword, Category
           category){       
       return filterAuctionListByKeywords(getActiveAuctionsByCategory(category), keyword);
   }
   
   
   public List<Auction> getActiveAuctions(){
       List<Auction> tempList= new ArrayList<>();
       for(int i=0; i< findAll().size();i++){
           if(findAll().get(i).isPublished()){
               tempList.add(findAll().get(i));
           }
       }
       return tempList;
   }
   
   public List<Auction> getActiveAuctionsBasedOnUserID(String id){    
       List<Auction> tempList= new ArrayList<>();
       for(int i= 0; i<getActiveAuctions().size();i++){
           if(getActiveAuctions().get(i).getUser().getId()==Long.parseLong(id, 10)){
               tempList.add(getActiveAuctions().get(i));
           }
       }
       return tempList;      
   }
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
       return tempList;      
   }   
   
   public int getTimeLeftInSeconds(String id){       
       int numberOfDays=getNumberOfDaysUntilDeadline(id);
       int seocndsLeft=(AuctionSupport.secondsToAuctionIsFinished(find(Long.parseLong(id,10)))-(numberOfDays*86400));      
       return seocndsLeft;       
   }      

    public int getNumberOfDaysUntilDeadline(String id) throws NumberFormatException {
        Double numberOfDays=Math.floor(AuctionSupport.secondsToAuctionIsFinished(find(Long.parseLong(id,10)))/86400);
        return numberOfDays.intValue();
    }
}
