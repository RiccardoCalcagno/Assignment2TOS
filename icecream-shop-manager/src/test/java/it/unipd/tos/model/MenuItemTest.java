////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;


public class MenuItemTest {

    MenuItem item;

    @Before
    public void setup(){
        item = new MenuItem("Nome Esempio", ItemType.Budino,3D);
    }


    @Test
    public void testRitornoNome() {
        assertEquals("Nome Esempio", item.getName());
    }
    
    @Test
    public void testRitornoPrezzo() {
        assertEquals(3D, item.getPrice(), 0);
    }
    
    
    @Test
    public void testRitornoTipoItemDiUnBudino_èBudino() {
        MenuItem budino = new MenuItem("", ItemType.Budino, 3D);
        assertEquals(ItemType.Budino, budino.getType());
    }
    @Test
    public void testRitornoTipoItemDiUnGelato_èGelato() {
        MenuItem gelato = new MenuItem("", ItemType.Gelato, 3D);
        assertEquals(ItemType.Gelato, gelato.getType());
    }
    @Test
    public void testRitornoTipoItemDiUnaBevanda_èBevanda() {
        MenuItem bevanda = new MenuItem("", ItemType.Bevanda, 3D);
        assertEquals(ItemType.Bevanda, bevanda.getType());
    }
}
