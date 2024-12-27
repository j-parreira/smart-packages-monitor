package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String name) {
        Query query = entityManager.createQuery("SELECT COUNT(p.id) FROM Product p WHERE p.name = :name", Long.class);
        query.setParameter("name", name);
        return (Long)query.getSingleResult() > 0L;
    }
}
