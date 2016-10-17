/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import entities.Auction;
import entities.AuctionUser;
import entities.Bid;
import entities.Product;
import java.util.HashMap;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author andre
 */
public class JsonConverter {
    
    public static JsonObject toJson(Auction auction) {
        
        /* Auction:
            long    id
            long    duration
            double  price
            boolean published
            Date    startTime
            Bid     bid
            Product product
            User    user
        
          Product:
           
        
        */
        
        
        Product product = auction.getProduct();
        Bid bid = auction.getBid();
        AuctionUser user = auction.getUser();
        
        /* TODO
            - check if bid is null
            - add all needed fields
        
        */
        JsonBuilderFactory factory = Json.createBuilderFactory(new HashMap<String, String>());
        JsonObject value = factory.createObjectBuilder()
                .add("id", auction.getId())
                .add("current_price", auction.getInitPrice())
                .add("start_date", auction.getStartTime().toString())
                .add("duration", auction.getDuration())
                .add("product", factory.createObjectBuilder()
                    .add("id", product.getId())
                    .add("product_name", product.getProductName())
                    .add("description", product.getDescription())
                    .add("category", product.getCategory().toString())
                    .add("picture_path", product.getPicturePath()))
                .add("bid", factory.createObjectBuilder()
                    .add("id", bid.getId())
                    .add("amount", bid.getAmount())
                    .add("bid_date", bid.getBidDate().toString()))
                .add("seller", factory.createObjectBuilder()
                    .add("id", user.getId())
                    .add("username", user.getUsername())
                    .add("rating", user.getSellers_rating()))
                .build();
        
        return value;
    }
}
