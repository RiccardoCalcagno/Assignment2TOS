////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;


public class UserTest {

    User persona;

    @Before
    public void setup(){
        persona= new User("Rohan","Malcovich",21);
    }

    @Test
    public void testRitornoNome() {
        assertEquals("Rohan", persona.getName());
    }
    
    @Test
    public void testRitornoCognome() {
        assertEquals("Malcovich", persona.getSurname());
    }
    
    @Test
    public void testRitornoetà() {
        assertEquals(21, persona.getAge());
    }
}