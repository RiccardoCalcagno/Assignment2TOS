////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import it.unipd.tos.business.exception.OrdineNullo;
import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.ItemType;
import it.unipd.tos.model.User;

import java.util.List;

public class TakeAwayBill_implementation implements TakeAwayBill {

    @Override
    public double getOrderPrice(List<MenuItem> itemsOrdered, User user) throws TakeAwayBillException {

        /*
         * PRIMO REQUISITO: Calcolo del Totale
         */

        if ((itemsOrdered == null) || (itemsOrdered.isEmpty())) {
            throw new OrdineNullo();
        }

        double totalPrice = 0;

        for (MenuItem item : itemsOrdered) {

            totalPrice += item.getPrice();
        }

        return totalPrice;
    }
}