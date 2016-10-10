/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import boundary.ProductFacade;
import entities.Auction;
import entities.Product;
import enums.Category;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

/**
 *
 * @author andre
 */
@Named(value = "addAuctionMB")
@ViewScoped
public class AddAuctionMB implements Serializable {
    
    @EJB
    AuctionFacade auctionFacade;
    
    @EJB
    ProductFacade productFacade;
    
    private String title;
    private String description;
    private int price;
    private long duration;
    private String selectedCategory;
    private boolean published;
    
    private Date startDate;
    private Date endDate;

    /**
     * Creates a new instance of AddAuctionMB
     */
    public AddAuctionMB() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        System.out.println("setstart date " + startDate);
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
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
        selectedCategories.add(new SelectItem("2", "White Ware"));
        selectedCategories.add(new SelectItem("3", "Jewlerry"));        
        selectedCategories.add(new SelectItem("4", "Guns"));        
        return selectedCategories;
    }
    
    public void addAuction() {
        // Create Product: id, category, description, picturepath, productname
        Product product = new Product();
        //product.setCategory(Category.fromString(selectedCategory));
        product.setDescription(description);
        product.setPicturePath("tesplaceholder...");
        product.setProductName(title);
        //productFacade.create(product);
        
        // Create Auction: id, duration, price, published, starttime, prod_id, user_id
        Auction auction = new Auction();
        
        System.out.println("start date: " + startDate);
    }
    
    
    
}
