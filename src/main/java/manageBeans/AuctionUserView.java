/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import boundary.AuctionUserFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

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
    
    public int getAllUsers(){
        return 12;
    }
    
}
