////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import java.util.List;

import it.unipd.tos.business.exception.OrdineNullo;

import java.time.LocalTime;

public class Order {

    User user;
    
    List<MenuItem> itemsOrdered; 
    
    LocalTime orderTime;
    
    double finalPrice;
    
    public Order(User user_, 
            List<MenuItem> itemsOrdered_, 
            LocalTime orderTime_,
            double finalPrice_) throws OrdineNullo{
    
        if((itemsOrdered_ == null) || (itemsOrdered_.isEmpty())) {
            throw new OrdineNullo();
        }
        user = user_;
        itemsOrdered = itemsOrdered_; 
        orderTime = orderTime_;
        finalPrice = finalPrice_;
    }
 
    
    public User getUser() {
        return user;
    }
    
    public List<MenuItem> getItemsOrdered(){
        return itemsOrdered;
    }
    
    public LocalTime getOrderTime() {
        return orderTime;
    }
    
    public double getFinalPrice() {
        return finalPrice; 
    }
    
    public void setFinalPrice(double finalPrice_) {
         finalPrice = finalPrice_;
    }
}