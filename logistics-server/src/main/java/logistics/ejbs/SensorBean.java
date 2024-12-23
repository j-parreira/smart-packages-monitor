package logistics.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;
}
