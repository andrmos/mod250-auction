/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import entities.Auction;
import org.joda.time.DateTime;

/**
 *
 * @author Andreas
 */
public class AuctionSupport {
    public static boolean isAuctionFinished(Auction auction){           
       return auction.isPublished() && (new DateTime(auction.getStartTime()).
               plusSeconds(auction.getDuration().intValue()).compareTo(new DateTime())<0);
   }
}
