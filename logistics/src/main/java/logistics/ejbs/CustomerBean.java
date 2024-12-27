package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import logistics.entities.Customer;

import java.util.List;

@Stateless
public class CustomerBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String email) {
        Query query = entityManager.createQuery("SELECT COUNT(c.id) FROM Customer c WHERE c.email = :email", Long.class);
        query.setParameter("email", email);
        return (Long)query.getSingleResult() > 0L;
    }
}
