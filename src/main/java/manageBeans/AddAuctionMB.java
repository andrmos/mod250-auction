/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;

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
    private String selectedCategory;
    private boolean published;

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

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
    
     public List<SelectItem> getSelectedCategories(){
        List selectedCategories=new ArrayList();
        selectedCategories.add(new SelectItem("0", "Clothing"));
        selectedCategories.add(new SelectItem("1", "Electronics"));
        selectedCategories.add(new SelectItem("2", "White Wear"));
        selectedCategories.add(new SelectItem("3", "Jewlerry"));        
        selectedCategories.add(new SelectItem("4", "Guns"));        
        return selectedCategories;
    }
    
    public void addAuction() {
        
        System.out.println("test");
        
    }
    
    
    
}
