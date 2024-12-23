package logistics.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;
}
