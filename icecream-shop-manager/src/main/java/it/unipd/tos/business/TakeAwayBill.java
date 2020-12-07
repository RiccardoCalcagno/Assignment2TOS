////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;
import java.util.List;
import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;



import java.time.LocalTime;
import it.unipd.tos.model.Order;



public interface TakeAwayBill {

    LocalTime getCurrentTime();
    void setCurrentTime(LocalTime t);

    double getOrderPrice(List<MenuItem> itemsOrdered, User user)
            throws TakeAwayBillException;
    
    List<Order> getListaOrdini();
    
    void addOrder(List<MenuItem> itemsOrdered, User user);
    
    void regalaOrdini();
}