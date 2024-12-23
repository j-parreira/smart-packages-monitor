package logistics.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class WarehouseBean {
    @PersistenceContext
    private EntityManager entityManager;
}
