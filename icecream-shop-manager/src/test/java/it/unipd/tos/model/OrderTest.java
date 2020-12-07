////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

import it.unipd.tos.business.exception.OrdineNullo;

import org.junit.Before;


public class OrderTest{
    
    Order ordine;
    User u1;
    MenuItem item1,item2;
    List<MenuItem> lista;
    LocalTime orario;
    Double prezzo;
    
    
    @Before
    public void setup(){    // per processo d'integrazione posso assumere corrette le classi User e MenuItem
        u1 = new User("Riccardo","Calcagno",21);
        item1 = new MenuItem("Nome1", ItemType.Budino,3D);
        item2 = new MenuItem("Nome2", ItemType.Bevanda,2D);
        lista = new ArrayList<MenuItem>(); lista.add(item1); lista.add(item2);
        orario = LocalTime.of(12,0);
        prezzo = 1D;
        
        ordine = new Order(u1,lista,orario,prezzo);
    }
    
    
    @Test(expected=OrdineNullo.class)
    public void testCreazioneOrdine_listaNulla(){
        ordine = new Order(u1,null,orario,0);
    }
    @Test(expected=OrdineNullo.class)
    public void testCreazioneOrdine_listaVuota(){
        ordine = new Order(u1,new ArrayList<MenuItem>(),orario,0);
    }
    
    
    @Test
    public void testRitornoUtente() {
        assertEquals(u1, ordine.getUser());
    }
    
    @Test
    public void testRitornoListaItem() {
        assertEquals(lista, ordine.getItemsOrdered());
    }
    
    @Test
    public void testRitornoOrarioOrdine() {
        assertEquals(orario, ordine.getOrderTime());
    }
    @Test
    public void testRitornoPrezzoOrdine() {
        assertEquals(prezzo, ordine.getFinalPrice(),0);
    }
    @Test
    public void testSetPrezzoFinale() {
        prezzo = 4D;
        ordine.setFinalPrice(prezzo);
        assertEquals(prezzo, ordine.getFinalPrice(),0);
    }
}
