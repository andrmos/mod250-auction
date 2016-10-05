/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionUserFacade;
import entities.Auction;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 *
 * @author oleeskild
 */
@Named(value = "auctionUserView")
@SessionScoped
public class AuctionUserView implements Serializable {

    
     @EJB
    private AuctionUserFacade userFacade;
    
    /**
     * Creates a new instance of AuctionUserView
     */
    public AuctionUserView() {
    }
    
    public int getNumber(){
        return 12;
    }
    
    public ArrayList<Auction> getFinishedAuctions(){
        ArrayList<Auction> arr = new ArrayList<Auction>();
        arr.addAll(this.userFacade.getFinishedAuctions());
        return arr;
    }
    
    public ArrayList<Auction> getCurrentAuctions(){
        ArrayList<Auction> arr = new ArrayList<Auction>();
        arr.addAll(this.userFacade.getCurrentAuctions());
        return arr;
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/mod250_auction/index.xhtml";
    }
    
}
