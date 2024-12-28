package logistics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllCustomers",
                query = "SELECT c FROM Customer c ORDER BY c.name"
        ),
        @NamedQuery(
                name = "getCustomerByEmail",
                query = "SELECT c FROM Customer c WHERE c.email = :email"
        )
})
public class Customer extends User {
    @NotNull
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return Objects.equals(address, customer.address) && Objects.equals(orders, customer.orders);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(address);
        result = 31 * result + Objects.hashCode(orders);
        return result;
    }
}




