package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ReadingBean {
    @PersistenceContext
    private EntityManager entityManager;
}
