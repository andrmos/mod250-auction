/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author DidrikKvanvik
 */
@Entity
@Table(name="USER")
public class AuctionUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="USER_ID")
    private Long id;
    private String username;
    private String password;
    private int sellers_rating;
    
    private enum Roles{
        SELLER, BUYER;
    }
    
    @OneToOne
    @JoinColumn(name="CONTACT_INFO_ID", referencedColumnName="CONTACT_INFO_ID")
    protected ContactInfo contactinfo;
    
    public AuctionUser(){
    
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSellers_rating() {
        return sellers_rating;
    }

    public void setSellers_rating(int sellers_rating) {
        this.sellers_rating = sellers_rating;
    }

    public ContactInfo getContactinfo() {
        return contactinfo;
    }

    public void setContactinfo(ContactInfo contactinfo) {
        this.contactinfo = contactinfo;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ id=" + id + " ]";
    }
    
}
