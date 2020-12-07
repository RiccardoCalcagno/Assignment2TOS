////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;
import it.unipd.tos.model.ItemType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

import it.unipd.tos.business.exception.OrdineNullo;

import org.junit.Before;


public class TakeAwayBillTest{

    User u1;
    List<MenuItem> lista;
    TakeAwayBill_implementation bill; 
    
    // Uso notazione [<parola>]SPDG per dire [<parola>]Senza_perdita_di_generalità


    @Before
    public void setup(){    
// per processo d'integrazione posso assumere corrette le classi User e MenuItem
        u1 = new User("NomeSPDG","CognomeSPDG",21);
        lista = new  ArrayList<MenuItem>();
        bill = new TakeAwayBill_implementation();
    }
    
    /*
     * PRIMO REQUISITO
     */
    
    @Test(expected=OrdineNullo.class)
    public void testGetOrderPrice_listaNulla(){

        bill.getOrderPrice(null, u1);
    }
    @Test(expected=OrdineNullo.class)
    public void testGetOrderPrice_listaVuota(){

        bill.getOrderPrice(lista, u1);
    }
    
    @Test
    public void testPrezzoOrdine_casoGenerale(){

        lista.add(new MenuItem("NomeSPDG", ItemType.Budino, 3D));
        lista.add(new MenuItem("NomeSPDG", ItemType.Gelato, 4D));
        lista.add(new MenuItem("NomeSPDG", ItemType.Bevanda, 4.5D));
                
        assertEquals(11.5D,bill.getOrderPrice(lista, u1),0.000001);
    }

    
    /*
     * SECONDO REQUISITO
     */
    

    @Test
    public void testScontoSePiùDi5Gelati_conPiùDi5Gelati() {
        //il caso con meno di 6 gelati viene preso in considerazione dal caso Generale
        
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 4D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 4.5D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 3D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 2D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 3D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 3.5D));
                
        assertEquals(19D,bill.getOrderPrice(lista, u1),0.000001);
    }
    @Test
    public void testScontoSePiùDi5Gelati_conPiùDi5GelatiEUnAltroTipoMenoCostosoDelResto() {
        
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Budino, 1D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 4D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 4.5D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 3D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 2D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 3D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 3.5D));
                
        assertNotEquals(20.5D,bill.getOrderPrice(lista, u1),0.000001);
    }
    
    
    /*
     * TERZO REQUISITO
     */
    
    @Test
    public void testScontoSeImportoDiGelatiEBudiniSupera50_superaI50() {
  //il caso con meno di 50€ di spesa di gelati e budini viene preso in considerazione dal caso Generale
        
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Budino, 25D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 10D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Budino, 17D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Bevanda, 15D));
       
        assertEquals(60.3D, bill.getOrderPrice(lista,u1), 0.000001);
    }
    @Test
    public void testScontoSeImportoDiGelatiEBudiniSupera50_inferioreA50MaOrdineTotaleSuperioreA50() {
        
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 10D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Budino, 20D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Bevanda, 25D));
       
        assertNotEquals(49.5D, bill.getOrderPrice(lista,u1), 0.000001);    
        //asserisci che non sia uguale al prezzo scontato del 10%
    }
    @Test
    public void testScontoPiùDi5GelatiEImportoOrdinazioniGelatiBudiniSuperioreA50() {
        //Ho interpretato il requisito in questa maniera: si calcola il totale e questo
        //è il punto di partenza per entrambe le condizioni dei possibili sconti, quindi 
        // prima applico lo sconto dei "5 Gelati" poi anche quello dei 50€
        
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Budino, 7D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 10D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 10D));
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 10D));    
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 5D));     
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 5D));     
        lista.add(new MenuItem("NomeSenzaPerditaDiGeneralità", ItemType.Gelato, 4D));
        
        assertEquals(44.1D, bill.getOrderPrice(lista,u1), 0.000001);
    }

}