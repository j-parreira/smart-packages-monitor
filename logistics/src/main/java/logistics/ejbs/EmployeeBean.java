package logistics.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class EmployeeBean {
    @PersistenceContext
    private EntityManager entityManager;

}
