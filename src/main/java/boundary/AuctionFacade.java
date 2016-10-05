/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import entities.Auction;
import enums.Category;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
        List<Auction> tempList= new ArrayList<>();
        for(int i=0;i<findAll().size();i++){
            if(findAll().get(i).getProduct().getCategory()==category){
                tempList.add(findAll().get(i));
            }
        }
        return tempList;
    }
    
    public List<Auction> getAuctionsByKeyword(String keyword){
        List<Auction> tempList= new ArrayList<>();
        for(int i=0;i<findAll().size();i++){
            if(findAll().get(i).getProduct().getProductName().contains(keyword)
                || findAll().get(i).getProduct().getDescription().
                        contains(keyword)){                
                tempList.add(findAll().get(i));
            }
        }
        return tempList;
    }
    
   public List<Auction> getAuctionsByKeywordAndCategory(String keyword, Category
           category){
       List<Auction> tempList= getAuctionByCategory(category);
       for(int i=0; i<tempList.size(); i++){
           if((!tempList.get(i).getProduct().getProductName().contains(keyword))||
                   (!tempList.get(i).getProduct().getDescription().contains(keyword))){
               tempList.remove(i);
           }
       }
       return tempList;
   }
}
