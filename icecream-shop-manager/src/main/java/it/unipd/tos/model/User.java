////////////////////////////////////////////////////////////////////
// Riccardo Calcagno 1193479
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

public class User {

    String name;
    
    String surname;
    
    Integer age;
    
    public User(String name_, 
            String surname_, 
            Integer age_) {
        name = name_;
        surname = surname_;
        age = age_;
    }
    
    public String getName() {
        return name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public int getAge() {
        return age;
    }
}
