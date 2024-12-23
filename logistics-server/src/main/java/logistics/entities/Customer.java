package logistics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    private int id;
    private String name;

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }
}




