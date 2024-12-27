package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class ManagerBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String email) {
        Query query = entityManager.createQuery("SELECT COUNT(m.id) FROM Manager m WHERE m.email = :email", Long.class);
        query.setParameter("email", email);
        return (Long)query.getSingleResult() > 0L;
    }
}
