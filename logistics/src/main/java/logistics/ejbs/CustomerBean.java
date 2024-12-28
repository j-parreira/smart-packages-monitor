package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Customer;
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

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(c.id) FROM Customer c WHERE c.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public void create(String name, String email, String password, String address) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        if (exists(findByEmail(name).getId())) {
            throw new MyEntityExistsException("Customer already exists");
        }
        try {
            Customer customer = new Customer(name, email, hasher.hash(password), address);
            entityManager.persist(customer);
            entityManager.flush();
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

    public Customer findByEmail(String email) throws MyEntityNotFoundException {
        var query = entityManager.createNamedQuery("getCustomerByEmail", Customer.class);
        query.setParameter("email", email);
        List<Customer> customers = query.getResultList();
        if (customers.isEmpty()) {
            throw new MyEntityNotFoundException("Customer not found");
        }
        return customers.get(0);
    }

    public Customer findWithOrders(long id) throws MyEntityNotFoundException {
        var customer = this.find(id);
        Hibernate.initialize(customer.getOrders());
        return customer;
    }

    public void update(Long id, String name, String email, String password, String address) throws MyEntityNotFoundException, MyConstraintViolationException {
        if (!exists(id)) {
            throw new MyEntityNotFoundException("Customer not found");
        }
        try {
            Customer customer = find(id);
            customer.setName(name);
            //customer.setEmail(email);
            customer.setPassword(hasher.hash(password));
            customer.setAddress(address);
            entityManager.merge(customer);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(Long id) throws MyEntityNotFoundException {
        var customer = find(id);
        if (!entityManager.contains(customer)) {
            customer = entityManager.merge(customer);
        }
        entityManager.remove(customer);
    }
}
