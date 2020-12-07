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

import java.time.LocalTime;
import java.util.ArrayList;
import it.unipd.tos.model.Order;
import java.util.Random;
import it.unipd.tos.business.exception.OrdiniInsufficientiPerRegali;

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


    /*
    * SESTO REQUISITO: Regalo a 10 ragazzi puntuali
    */

    private LocalTime currentTime;

    private List<Order> listaOrdini;

    public TakeAwayBill_implementation() {

        currentTime = LocalTime.of(0, 0, 0);
        listaOrdini = new ArrayList<Order>();
    }

    @Override
    public LocalTime getCurrentTime() {
        return currentTime;
    }

    @Override
    public void setCurrentTime(LocalTime t) {
        currentTime = t;
    }

    @Override
    public List<Order> getListaOrdini() {
        return listaOrdini;
    }

    @Override
    public void addOrder(List<MenuItem> itemsOrdered, User user) {

        Order o = new Order(user, itemsOrdered, currentTime, getOrderPrice(itemsOrdered, user));

        listaOrdini.add(o);
    }

    private boolean isValidoPerRegalo(Order ordine, List<Order> ordiniPapabili) {
        boolean valido = true;

        for (Order o : ordiniPapabili) {
            if (o.getUser().getName() == ordine.getUser().getName()) {
                if (o.getUser().getSurname() == ordine.getUser().getSurname()) {
                    valido = false;
                }
            }
        }
        if ((ordine.getUser().getAge() >= 18) || (ordine.getOrderTime().isAfter(LocalTime.of(19, 0, 0)))
                || (ordine.getOrderTime().isBefore(LocalTime.of(18, 0, 0)))) {
            valido = false;
        }

        return valido;
    }

    @Override
    public void regalaOrdini() {

        List<Order> ordiniPapabili = new ArrayList<Order>();

        for (Order ordine : listaOrdini) {

            if (isValidoPerRegalo(ordine, ordiniPapabili)) {
                ordiniPapabili.add(ordine);
            }
        }
        int x = 0;
        if (ordiniPapabili.size() > 9) {
            Random randomizer = new Random();

            for (int i = 0; i < 10; i++) {

                x = randomizer.nextInt(ordiniPapabili.size());

                Order selezionato = ordiniPapabili.remove(x);

                selezionato.setFinalPrice(0);
            }
        } else {

            throw (new OrdiniInsufficientiPerRegali());
        }
    }

}

