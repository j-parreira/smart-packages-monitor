package logistics.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ReadingBean {
    @PersistenceContext
    private EntityManager entityManager;
}
