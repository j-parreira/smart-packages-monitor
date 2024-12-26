package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = "getAllCustomers", query = "SELECT c FROM Customer c ORDER BY c.name"),})
public class Customer extends Person {
    @NotBlank
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public Customer() {
        orders = new LinkedList<>();
    }

    public Customer(String name, String email, String password, String address) {
        super(name, email, password);
        this.address = address;
        orders = new LinkedList<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }
}




