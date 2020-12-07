////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.Order;
import it.unipd.tos.model.User;
import it.unipd.tos.model.ItemType;
import it.unipd.tos.business.exception.OrdiniInsufficientiPerRegali;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;

import org.junit.Before;


public class TkAwyBllSestoRequisitoTest {
    
    TakeAwayBill_implementation bill;
    
    List<Order> ordini;
    
    List<MenuItem>itemsCampione;
    User utenteCampione;
    
    final String[] nomiCampione = new String[]{"Mirco", "Mauro", "John", "Rossana",
            "Freddy", "Chris", "Natan", "Owen", "Giorgio", "Funny", "Chiara"};
    
    
    @Before
    public void setup(){    
        ordini = new ArrayList<Order>();
        
        itemsCampione = new ArrayList<MenuItem>();
        itemsCampione.add(new MenuItem("NomeSPDG", ItemType.Budino, 3D));
        itemsCampione.add(new MenuItem("NomeSPDG", ItemType.Gelato, 4D));
        itemsCampione.add(new MenuItem("NomeSPDG", ItemType.Bevanda, 4.5D));
        utenteCampione = new User("Riccardo","Calcagno", 17);
        
        bill = new TakeAwayBill_implementation();
    }
    
    @Test
    public void testStatusAllaCreazione() {
        
        assertTrue(LocalTime.of(0,0,0)==bill.getCurrentTime() && bill.getListaOrdini().size()==0);
    }
    @Test
    public void testSetEGetCurrentTime() {
        
        bill.setCurrentTime(LocalTime.of(12, 23));
        assertEquals(LocalTime.of(12, 23), bill.getCurrentTime());
    }
    @Test
    public void testAddEGetListaOrdini() {
        bill.setCurrentTime(LocalTime.of(15,0));
        
        bill.addOrder(itemsCampione, utenteCampione);
        Order validatore = new Order(utenteCampione, itemsCampione, bill.getCurrentTime(),
                                        bill.getOrderPrice(itemsCampione, utenteCampione));
        
        boolean b=true;
        b=b&&(bill.getListaOrdini().size()==1);
        Order onTest= bill.getListaOrdini().get(0);
        b=b&&(onTest.getFinalPrice()==validatore.getFinalPrice());
        b=b&&(onTest.getItemsOrdered()==validatore.getItemsOrdered());
        b=b&&(onTest.getOrderTime()==validatore.getOrderTime());
        
        assertTrue(b);
    }
    
    
    
    @Test(expected=OrdiniInsufficientiPerRegali.class)
    public void testDonazioneRegali_ConMenoDi10Ordini() {
        
        bill.setCurrentTime(LocalTime.of(18,30));
        
        for(int i=0; i<5; i++) {
            utenteCampione = new User(nomiCampione[i],"CognomeSPDG", 17);
            bill.addOrder(itemsCampione, utenteCampione);
        }
        bill.regalaOrdini();
    }
    
    @Test(expected=OrdiniInsufficientiPerRegali.class)
    public void testDonazioneRegali_InsufficientiOrdiniConPersoneDiverse() {
        
        bill.setCurrentTime(LocalTime.of(18,30));

        bill.addOrder(itemsCampione, utenteCampione);
        bill.addOrder(itemsCampione, utenteCampione);
        bill.addOrder(itemsCampione, utenteCampione);
        
        for(int i=3; i<11; i++) {
            utenteCampione = new User(nomiCampione[i],"CognomeSPDG", 17);
            bill.addOrder(itemsCampione, utenteCampione);
            
        }
        bill.regalaOrdini();
    }
    @Test(expected=OrdiniInsufficientiPerRegali.class)
    public void testDonazioneRegali_InsufficientiOrdiniPerEta() {
        
        // Aggiungo un Ragazzo addeguato alle condizioni:
        bill.setCurrentTime(LocalTime.of(18,30));
        utenteCampione = new User(nomiCampione[1],"RagazzoInOrario", 17);
        bill.addOrder(itemsCampione, utenteCampione);    
        
        //Il resto è composto da persone non idone, tutte con nomi differenti
        for(int i=1; i<11; i++) {
            utenteCampione = new User(nomiCampione[i],"CognomeSPDG", 21);
            bill.addOrder(itemsCampione, utenteCampione);
        }
        
        bill.regalaOrdini();
    }
    @Test(expected=OrdiniInsufficientiPerRegali.class)
    public void testDonazioneRegali_InsufficientiOrdiniPerOrarioTardivo() {
        
        // Aggiungo un Ragazzo addeguato alle condizioni:
        bill.setCurrentTime(LocalTime.of(18,30));
        utenteCampione = new User(nomiCampione[1],"RagazzoInOrario", 17);
        bill.addOrder(itemsCampione, utenteCampione);    
        
        //Il resto è composto da persone non idone, tutte con nomi differenti
        bill.setCurrentTime(LocalTime.of(20,00));
        for(int i=1; i<11; i++) {
            utenteCampione = new User(nomiCampione[i],"CognomeSPDG", 17);
            bill.addOrder(itemsCampione, utenteCampione);
        }
        
        bill.regalaOrdini();
    }
    @Test(expected=OrdiniInsufficientiPerRegali.class)
    public void testDonazioneRegali_InsufficientiOrdiniPerOrarioAnticipatorio() {
        
        // Aggiungo un Ragazzo addeguato alle condizioni:
        bill.setCurrentTime(LocalTime.of(18,30));
        utenteCampione = new User(nomiCampione[1],"RagazzoInOrario", 17);
        bill.addOrder(itemsCampione, utenteCampione);    
        
        //Il resto è composto da persone non idone, tutte con nomi differenti
        bill.setCurrentTime(LocalTime.of(12,00));
        for(int i=1; i<11; i++) {
            utenteCampione = new User(nomiCampione[i],"CognomeSPDG", 17);
            bill.addOrder(itemsCampione, utenteCampione);
        }
        
        bill.regalaOrdini();
    }
    @Test
    public void testDonazioneRegali_InGradoDiDonareA10Ragazzi() {
        
        //Per un insieme che contiene la più alta variabilità di Ordini ..
        // Inserisco: Persona troppo vecchia
        bill.setCurrentTime(LocalTime.of(18,30));
        utenteCampione = new User("Vecchio","CognomeSPDG", 70);
        bill.addOrder(itemsCampione, utenteCampione);
        //Inserisco: Persone che hanno fatto l'ordine in un momento sbagliato
        bill.setCurrentTime(LocalTime.of(17,0));
        utenteCampione = new User("Anticipatore","CognomeSPDG", 17);
        bill.addOrder(itemsCampione, utenteCampione);
        bill.setCurrentTime(LocalTime.of(20,0));
        utenteCampione = new User("Ritardatario","CognomeSPDG", 17);
        bill.addOrder(itemsCampione, utenteCampione);
        // .. Ma che ha anche il minimo numero di ordini necessario a passare la prova
        for(int i=0; i<11; i++) {
            bill.setCurrentTime(LocalTime.of(18,30));
            utenteCampione = new User(nomiCampione[i],"CognomeSPDG", 17);
            bill.addOrder(itemsCampione, utenteCampione);
        }

        bill.regalaOrdini();
        
        int numAzzerati = 0;
        for (Order o : bill.getListaOrdini()) {
            if(o.getFinalPrice() == 0) {
                numAzzerati++;
            }
        }
        assertEquals(10, numAzzerati);
    }

}