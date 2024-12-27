package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(Long OrderId, int volumeNumber) {
        Query query = entityManager.createQuery("SELECT COUNT(v.id) FROM Volume v WHERE v.order.id = :OrderId AND v.volumeNumber = :volumeNumber", Long.class);
        query.setParameter("OrderId", OrderId);
        query.setParameter("volumeNumber", volumeNumber);
        return (Long)query.getSingleResult() > 0L;
    }
}
