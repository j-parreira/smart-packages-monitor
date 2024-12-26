package logistics.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class StockBean {
    @PersistenceContext
    private EntityManager entityManager;
}
