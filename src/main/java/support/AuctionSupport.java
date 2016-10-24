/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import entities.Auction;
import entities.Bid;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

/**
 *
 * @author Andreas
 */
public class AuctionSupport {
    
    /**
     * Checks if an auction is finished
     * @param auction
     *          auction to check
     * @return boolean
     *          true if finished, else false
     */
    public static boolean isAuctionFinished(Auction auction){           
       return auction.isPublished() && (new DateTime(auction.getStartTime()).
               plusSeconds(auction.getDuration().intValue()).compareTo(new DateTime())<0);
    }
    
    /**
     * Returns number of seconds left until an auction is finished
     * @param auction
     *          auction to check
     * @return int
     *          number of seconds left
     */
    public static int secondsToAuctionIsFinished(Auction auction){
        if(auction.getStartTime()==null){
            return 0;
        }
        else if(auction.getStartTime()!=null){           
            Calendar timout= (Calendar) auction.getStartTime().clone();
            timout.add(Calendar.SECOND, Math.toIntExact(auction.getDuration()));
            
            if (timout.after(Calendar.getInstance())) {
                return Math.toIntExact((timout.getTimeInMillis()-Calendar.getInstance().getTimeInMillis())/1000);
            }
        }            
        return 0;
    }
    
    /**
     * Sorts a list of auctions based on the time left
     * @param auctions
     *          list of auctions
     * @return auctions
     *          sorted aucitons list
     */
    public static List<Auction> sortAuctionsBasedOnTime(List<Auction> auctions) {
        Collections.sort(auctions);       
        return auctions;
    }
   
    /**
     * Returns the current price of an auction, that is the highest bid if
     * it has one, else the initialized price of the auction
     * @param auction
     *          auction to get the price of
     * @return price
     *          the price of the auction
     */
    public static double getCurrentPrice(Auction auction){
        Bid bid = auction.getBid();
        if(bid == null){
            return auction.getInitPrice();
        }else{
            return bid.getAmount();
        }
    }
}
