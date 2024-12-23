package logistics.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ManagerBean {
    @PersistenceContext
    private EntityManager entityManager;
}
