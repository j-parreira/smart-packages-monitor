package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import logistics.entities.Warehouse;

@Stateless
public class WarehouseBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String name) {
        Query query = entityManager.createQuery("SELECT COUNT(w.id) FROM Warehouse w WHERE w.name = :name", Long.class);
        query.setParameter("name", name);
        return (Long)query.getSingleResult() > 0L;
    }



}
