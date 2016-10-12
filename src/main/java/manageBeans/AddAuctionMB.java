/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import boundary.AuctionUserFacade;
import boundary.ProductFacade;
import entities.Auction;
import entities.Product;
import enums.Category;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import org.joda.time.DateTime;
import org.joda.time.Duration;

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
    
    @EJB
    AuctionUserFacade userFacade;
    
    private String title;
    private String description;
    private double price;
    private long duration;
    private int selectedCategory;
    private String picturePath;
    private Date endDate;
    private Date endTime;

    /**
     * Creates a new instance of AddAuctionMB
     */
    public AddAuctionMB() {
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(int selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
    
    /**
     * Gets list of selected categories
     * 
     * @return selectedCategories 
     *          list of categories
     */
    public List<SelectItem> getSelectedCategories(){
       List selectedCategories=new ArrayList();
       selectedCategories.add(new SelectItem("0", "Clothing"));
       selectedCategories.add(new SelectItem("1", "Electronics"));
       selectedCategories.add(new SelectItem("2", "White Ware"));
       selectedCategories.add(new SelectItem("3", "Jewlerry"));        
       selectedCategories.add(new SelectItem("4", "Guns"));        
       return selectedCategories;
    }
    
    /**
     * Adds an auction to the system
     * @return redirected xhtml page
     */
    public String addAuction() {
        Product product = createProduct();
        Auction auction = createAuction(product);
        productFacade.create(product);
        auctionFacade.create(auction);
        
        // Redirect to profile
        return "/SellerViews/sellerProfile.xhtml";
    }
    
    /**
     * Creates a new product
     * @return product
     *          the created product
     */ 
    private Product createProduct() {
        Product product = new Product();
        product.setCategory(Category.fromInt(selectedCategory));
        product.setDescription(description);
        product.setPicturePath(picturePath);
        product.setProductName(title);
        return product;
    }
    
    /**
     * Creates an auction with a product
     * @param product
     *          product to add to an auction
     * @return auction
     *          the new created auction
     */
    private Auction createAuction(Product product) {
        DateTime currentTime = new DateTime();
        DateTime temp = new DateTime(endDate);
        DateTime end = new DateTime(temp.getYear(), temp.getMonthOfYear(), temp.getDayOfMonth(), endTime.getHours(), endTime.getMinutes());      
        
        // Calculate duration from start to end date
        Duration duration = new Duration(currentTime, end);
        long durationSeconds = duration.getStandardSeconds();
        
        Auction auction = new Auction();
        auction.setDuration(durationSeconds);
        auction.setInitPrice(price);
        // All auctions are published. TODO remove field from db
        auction.setPublished(true); 
        auction.setStartTime(currentTime.toDate());
        auction.setProduct(product);
        auction.setUser(userFacade.getAuctionUser());
        return auction;
    }
    
}
