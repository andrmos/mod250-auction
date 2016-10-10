/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import boundary.AuctionFacade;
import boundary.AuctionUserFacade;
import entities.Auction;
import entities.AuctionUser;
import entities.Bid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

/**
 *
 * @author Andreas
 */
public class AuctionSupport {
    public static boolean isAuctionFinished(Auction auction){           
       return auction.isPublished() && (new DateTime(auction.getStartTime()).
               plusSeconds(auction.getDuration().intValue()).compareTo(new DateTime())<0);
   }
    
   public static int secondsToAuctionIsFinished(Auction auction){
      DateTime finsihedDate=new DateTime(auction.getStartTime()).
               plusSeconds(auction.getDuration().intValue());
      DateTime now=new DateTime();
      if(finsihedDate.isAfter(now)){
      return Seconds.secondsBetween(finsihedDate, now).getSeconds();      
      }
      else if(auction.getStartTime()==null){
          return 0;
      }
      return 0;
   }
   
   public static List<Auction> sortAuctionsBasedOnTime(List<Auction> auctions) {
       Collections.sort(auctions);
       return auctions;
   }
   
   public static double getCurrentPrice(Auction auction){
        Bid bid = auction.getBid();
        if(bid == null){
          return auction.getInitPrice();
        }else{
            return bid.getAmount();
        }
   }
}
