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
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

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
    /**
     * Creates a new instance of AuctionListManagedBean
     */
    public AuctionListManagedBean() {
    }
    
    public List<Auction> getAuctions() {
        return auction.findAll();
    }
    
    public void createAuction() {
        System.out.println("heymama");
        
        Long kk= 123L;
    
        Product product = new Product();
        product.setCategory(Category.GUNS);
        product.setPicturePath("/resources/images/car.png");
        product.setProductName("Sexy necklace");
        product.setDescription("Sweet baby");
        //productFacade.create(product);
        Auction auction1 = new Auction();
        auction1.setDuration(3600L);
        auction1.setProduct(product);
        
        AuctionUser user = new AuctionUser();
        user.setPassword("a4279eae47aaa7417da62434795a011ccb0ec870f7f56646d181b5500a892a9a");
        user.setUsername("seller");
        user.setRole("seller");    
        //auctionUser.create(user);
        auction1.setUser(user);
        auction1.setDuration(123L);
        //auction.create(auction1);
        
    }
    
    //public List<Auction> getAuctionsByCategory(int category) {
        
    //}
    
}
