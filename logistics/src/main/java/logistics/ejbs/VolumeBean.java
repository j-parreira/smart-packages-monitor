package logistics.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;
}
