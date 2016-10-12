/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entityListner.MenuChangeListener;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author oleeskild
 */
@EntityListeners(MenuChangeListener.class)
@Entity
@Table(name="AUCTION")
public class Auction implements Serializable, Comparable<Auction> {

    private static final long serialVersionUID = 1L;
    
    @OneToOne
    @JoinColumn(name="BID_ID", referencedColumnName="BID_ID")
    protected Bid bid;
    private Long duration;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="AUCTION_ID")
    private Long id;
    private double initPrice;
    // True if auction is published and visible to users
    private boolean published;
    
    @OneToOne
    @JoinColumn(name="PRODUCT_ID", referencedColumnName="PRODUCT_ID")
    protected Product product;
    @Temporal(TemporalType.DATE)    
    private Date startTime;
    
    @OneToOne
    @JoinColumn(name="AUCTION_USER_ID", referencedColumnName="AUCTION_USER_ID")
    protected AuctionUser user;
    
    public Auction(){
        
    }
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auction)) {
            return false;
        }
        Auction other = (Auction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public double getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(double initPrice) {
        this.initPrice = initPrice;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public AuctionUser getUser() {
        return user;
    }
    
    public void setUser(AuctionUser user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "entities.Auction[ id=" + id + " ]";
    }

    @Override
    public int compareTo(Auction o) {
        Date othertime,thisTime;
        try{
            othertime =new Date(o.getStartTime().getTime());            
            othertime.setTime(othertime.getTime()+(o.getDuration()*1000));
        }
        catch(Exception e){
            return 0;
        }
        try{
            thisTime =new Date(getStartTime().getTime());            
            thisTime.setTime(thisTime.getTime()+(getDuration()*1000));        
        }
        catch(Exception e){
            return 0;
        }   
        return thisTime.compareTo(othertime);
    }    
}
