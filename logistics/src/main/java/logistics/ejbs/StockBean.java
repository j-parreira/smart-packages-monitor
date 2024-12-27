package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class StockBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(Long productId, Long warehouseId) {
        Query query = entityManager.createQuery("SELECT COUNT(s.id) FROM Stock s WHERE s.product.id = :productId AND s.warehouse.id = :warehouseId", Long.class);
        query.setParameter("productId", productId);
        query.setParameter("warehouseId", warehouseId);
        return (Long)query.getSingleResult() > 0L;
    }
}
