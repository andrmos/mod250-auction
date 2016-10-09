/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author andre
 */
@Named(value = "addAuctionMB")
@RequestScoped
public class AddAuctionMB implements Serializable {
    
    @EJB
    AuctionFacade auctionFacade;
    
    private String title;
    private String description;
    private int price;
    private int duration;

    /**
     * Creates a new instance of AddAuctionMB
     */
    public AddAuctionMB() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public void addAuction() {
        System.out.println("title: " + title);
    }
    
    
    
}
