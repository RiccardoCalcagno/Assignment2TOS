////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

public class MenuItem {

    String name; 

    ItemType itemType; 
    
    double price; 
    
    public MenuItem( String name_, ItemType itemType_, double price_) {
        name = name_; 
        itemType = itemType_;
        price = price_; 
    }
    
    
    public String getName() {
        return name;
    }
    
    public ItemType getType() {
        return itemType;
    }
    
    public double getPrice() {
        return price; 
    }
    
}