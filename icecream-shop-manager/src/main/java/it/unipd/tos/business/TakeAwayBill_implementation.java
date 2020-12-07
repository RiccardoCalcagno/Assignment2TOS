////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import it.unipd.tos.business.exception.OrdineNullo;
import it.unipd.tos.business.exception.SuperatoLimite30Item;
import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.ItemType;
import it.unipd.tos.model.User;

import java.util.List;

public class TakeAwayBill_implementation implements TakeAwayBill {
    
    private double sconto5Gelati(double totalPrice, List<MenuItem> itemsOrdered) {
        /*
         * SECONDO REQUISITO: Sconto dei 5 Gelati 
         */

        int numGelati = 0;

        double minPrice = totalPrice + 1D;

        for (MenuItem item : itemsOrdered) {

            if (item.getType() == ItemType.Gelato) {
                numGelati++;
                if (minPrice > item.getPrice()) {
                    minPrice = item.getPrice();
                }
            }
        }

        if (numGelati > 5) {
            totalPrice -= minPrice * 0.5D;
        }
        return totalPrice;
    }
    
    private double scontoPiùDI50GelatiBudini(double totalPrice, List<MenuItem> itemsOrdered) {
        /*
         * TERZO REQUISITO: Più di 50 tra Gelati e Budini
         */

        double gelatiBudiniTotal = 0;

        for (MenuItem item : itemsOrdered) {
            if ((item.getType() == ItemType.Gelato) || (item.getType() == ItemType.Budino)) {
                gelatiBudiniTotal += item.getPrice();
            }
        }

        if (gelatiBudiniTotal > 50D) {
            totalPrice *= 0.9D;
        }
        return totalPrice;
    }

    private void limiteSuperiore(List<MenuItem> itemsOrdered) throws TakeAwayBillException {
        /*
         * QUARTO REQUISITO: Limite Superiore 30 items
         */

        if (itemsOrdered.size() > 30) {
            throw (new SuperatoLimite30Item("Inseriti più di 30 items"));
        }
    }
    
    private double commissioneLimiteInferiore(double totalPrice) {
        /*
         * QUINTO REQUISITO: Commissione se inferiore a 10€
         */

        if (totalPrice < 10D) {
            totalPrice += 0.5D;
        }
        return totalPrice;
    }
    
    
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
        
        
        totalPrice = sconto5Gelati(totalPrice, itemsOrdered);
        
        totalPrice = scontoPiùDI50GelatiBudini(totalPrice, itemsOrdered);
        
        limiteSuperiore(itemsOrdered);
        
        totalPrice = commissioneLimiteInferiore(totalPrice);
        

        return totalPrice;
    }
}