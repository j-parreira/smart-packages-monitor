package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import logistics.entities.Customer;

@Stateless
public class CustomerBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void createCustomer(String name) {
        // Create a new customer
        var customer = new Customer(name);
        entityManager.persist(customer);
    }
}
