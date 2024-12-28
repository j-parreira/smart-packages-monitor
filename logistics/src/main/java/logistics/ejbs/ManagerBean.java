package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Manager;
import logistics.entities.Warehouse;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Hasher;

import java.util.List;

@Stateless
public class ManagerBean {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(m.id) FROM Manager m WHERE m.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public void create(String name, String email, String password, Warehouse warehouse, String office) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        if (exists(findByEmail(email).getId())) {
            throw new MyEntityExistsException("Manager already exists");
        }
        try {
            Manager manager = new Manager(name, email, hasher.hash(password), warehouse, office);
            entityManager.persist(manager);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Manager> findAll() {
        return entityManager.createNamedQuery("getAllManagers", Manager.class).getResultList();
    }

    public Manager find(long id) throws MyEntityNotFoundException {
        var manager = entityManager.find(Manager.class, id);
        if (manager == null) {
            throw new MyEntityNotFoundException("Manager not found");
        }
        return manager;
    }

    public Manager findByEmail(String email) throws MyEntityNotFoundException {
        var query = entityManager.createNamedQuery("getManagerByEmail", Manager.class);
        query.setParameter("email", email);
        List<Manager> managers = query.getResultList();
        if (managers.isEmpty()) {
            throw new MyEntityNotFoundException("Manager not found");
        }
        return managers.get(0);
    }

    public void update(Long id, String name, String email, String password, Warehouse warehouse, String office) throws MyEntityNotFoundException, MyConstraintViolationException {
        if (!exists(id)) {
            throw new MyEntityNotFoundException("Manager not found");
        }
        try {
            Manager manager = find(id);
            manager.setName(name);
            //manager.setEmail(email);
            manager.setPassword(hasher.hash(password));
            manager.setWarehouse(warehouse);
            manager.setOffice(office);
            entityManager.merge(manager);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(Long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            Manager manager = find(id);
            if (!entityManager.contains(manager)) {
                manager = entityManager.merge(manager);
            }
            entityManager.remove(manager);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
}
