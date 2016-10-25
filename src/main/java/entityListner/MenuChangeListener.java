/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityListner;

import Servlets.Push;
import entities.Auction;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;


/**
 *
 * @author Eier
 */
@Singleton
public class MenuChangeListener {              
    
    @PostPersist
    @PostUpdate
    @PostRemove
    public void onChange(Auction auction) {
        System.out.println("auciotnListnerWorksWeey");
        Push.sendAll("updateMenu");
    
    }
}
