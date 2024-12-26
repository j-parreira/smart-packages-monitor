package logistics.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;
}
