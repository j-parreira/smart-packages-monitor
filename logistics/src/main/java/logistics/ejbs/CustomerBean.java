package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Customer;
import logistics.entities.Order;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Hasher;
import org.hibernate.Hibernate;

import java.util.List;

@Stateless
public class CustomerBean {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;

    public boolean exists(String email) {
        Query query = entityManager.createQuery("SELECT COUNT(c.email) FROM User c WHERE c.email = :email", Long.class);
        query.setParameter("email", email);
        return (Long) query.getSingleResult() > 0L;
    }

    public Customer create(String name, String email, String password, String address) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        if (exists(email)) {
            throw new MyEntityExistsException("Account with that email already exists");
        }
        try {
            Customer customer = new Customer(name, email, hasher.hash(password), address);
            entityManager.persist(customer);
            entityManager.flush();
            return customer;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Customer> findAll() {
        return entityManager.createNamedQuery("getAllCustomers", Customer.class).getResultList();
    }

    public Customer find(long id) throws MyEntityNotFoundException {
        var customer = entityManager.find(Customer.class, id);
        if (customer == null) {
            throw new MyEntityNotFoundException("Customer not found");
        }
        return customer;
    }

    public Customer findOrders(long id) throws MyEntityNotFoundException {
        var customer = this.find(id);
        Hibernate.initialize(customer.getOrders());
        return customer;
    }

    public Customer update(Long id, String name, String password, String address) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            Customer customer = find(id);
            customer.setName(name);
            customer.setPassword(hasher.hash(password));
            customer.setAddress(address);
            entityManager.merge(customer);
            entityManager.flush();
            return customer;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(Long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var customer = find(id);
            if (!entityManager.contains(customer)) {
                customer = entityManager.merge(customer);
            }
            entityManager.remove(customer);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
}
