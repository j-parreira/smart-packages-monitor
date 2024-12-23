package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import logistics.entities.Customer;

@Stateless
public class CustomerBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void createCustomer(String name, String email, String password, String address) {
        Customer customer = new Customer(name, email, password, address);
        entityManager.persist(customer);
    }
}
