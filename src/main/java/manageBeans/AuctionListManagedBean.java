/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionFacade;
import boundary.AuctionUserFacade;
import boundary.ProductFacade;
import enums.Category;
import entities.Auction;
import entities.AuctionUser;
import entities.Product;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.faces.view.facelets.FaceletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.AssertTrue;
import support.AuctionSupport;

/**
 *
 * @author andre
 */
@Named(value = "auctionListManagedBean")
@SessionScoped
public class AuctionListManagedBean implements Serializable {

    @EJB
    AuctionFacade auction;
    @EJB
    ProductFacade productFacade;
    @EJB
    AuctionUserFacade auctionUser;
    
    private List<Auction> currentListOfAuctions;
    private String selectedCategory;
    private String searchKeyword;
    
    @AssertTrue()
    private Double bid;
    


    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searhKeyword) {
        this.searchKeyword = searhKeyword;
    }

    public String getSelectedCategory() {
        if(selectedCategory==null){
            selectedCategory="value1";
        }
        return selectedCategory;
    }
    
    public void setSelectedCategory(String selectedCategory){
        this.selectedCategory=selectedCategory;
    }
    
    public List<SelectItem> getSelectedCategories(){
        List selectedCategories=new ArrayList();
        selectedCategories.add(new SelectItem("10", "All Items"));
        selectedCategories.add(new SelectItem("0", "Clothing"));
        selectedCategories.add(new SelectItem("1", "Electronics"));
        selectedCategories.add(new SelectItem("2", "White Wear"));
        selectedCategories.add(new SelectItem("3", "Jewlerry"));        
        selectedCategories.add(new SelectItem("4", "Guns"));        
        return selectedCategories;
    }
    
    /**
     * Creates a new instance of AuctionListManagedBean
     */
    public AuctionListManagedBean() {        
        
    }
    
    
    
    private Category getEnumFromSelectedCatagory(){        
        return Category.fromInt(Integer.parseInt(selectedCategory));
    }
    
    public void  search(){       
        if(selectedCategory.equals("10")||selectedCategory.equals("")){
        currentListOfAuctions=auction.getAuctionsByKeyword(searchKeyword);
        }
        else if((!selectedCategory.equals("10")||!selectedCategory.equals(""))
                && searchKeyword==null||searchKeyword.equals("")){
            currentListOfAuctions=auction.getAuctionByCategory(getEnumFromSelectedCatagory());
        }
        else {
            currentListOfAuctions=auction.getAuctionsByKeywordAndCategory
        (searchKeyword, getEnumFromSelectedCatagory());
        }        
    }
    
    
    public List<Auction> getAuctions() {
        if(currentListOfAuctions==null){
            return auction.getSortedAuctions(auction.findAll());
        }
        else{
            return auction.getSortedAuctions(currentListOfAuctions);
        }        
    }
    
   public int getSecondsLeft(String id){
       return auction.getTimeLeftInSeconds(id);
   }
   
   public int getDaysLeft(String id){
       return auction.getNumberOfDaysUntilDeadline(id);
   }
   
   public double getPrice(String auctionId){
       return AuctionSupport.getCurrentPrice(auction.find(Long.valueOf(auctionId)));
   }
    
    public void createAuction() {
       /* System.out.println("heymama");
        
        Long kk= 123L;
    
        Product product = new Product();
        product.setCategory(Category.JEWLERRY);
        product.setPicturePath("/resources/images/car.png");
        product.setProductName("Sexy necklace");
        product.setDescription("Sweet baby");
        productFacade.create(product);
        Auction auction1 = new Auction();
        auction1.setDuration(3600L);
        auction1.setProduct(product);
        
        AuctionUser user = new AuctionUser();
        user.setPassword("a4279eae47aaa7417da62434795a011ccb0ec870f7f56646d181b5500a892a9a");
        user.setUsername("seller");
        user.setRole("seller");    
        auctionUser.create(user);
        auction1.setUser(user);
        auction1.setDuration(123L);
        auction.create(auction1);*/
    }           
}
