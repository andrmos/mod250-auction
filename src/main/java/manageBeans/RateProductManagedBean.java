/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageBeans;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author DidrikKvanvik
 */
@Named(value = "rateProductManagedBean")
@RequestScoped
public class RateProductManagedBean {

    private Double rating;
    private String comment;
    
    /**
     * Creates a new instance of RateProductManagedBean
     */
    public RateProductManagedBean() {
    }
    
    public Double getRating() {
        return rating;
    }

    public void setStars(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
