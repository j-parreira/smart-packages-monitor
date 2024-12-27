package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class EmployeeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String email) {
        Query query = entityManager.createQuery("SELECT COUNT(e.id) FROM Employee e WHERE e.email = :email", Long.class);
        query.setParameter("email", email);
        return (Long)query.getSingleResult() > 0L;
    }
}
