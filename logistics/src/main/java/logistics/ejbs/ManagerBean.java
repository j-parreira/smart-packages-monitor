package logistics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Manager;
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
    @EJB
    private WarehouseBean warehouseBean;

    public boolean exists(String email) {
        Query query = entityManager.createQuery("SELECT COUNT(m.email) FROM Manager m WHERE m.email = :email", Long.class);
        query.setParameter("email", email);
        return (Long) query.getSingleResult() > 0L;
    }

    public Manager create(String name, String email, String password, Long warehouseId, String office) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        if (exists(email)) {
            throw new MyEntityExistsException("Manager with that email already exists");
        }
        try {
            var warehouse = warehouseBean.find(warehouseId);
            Manager manager = new Manager(name, email, hasher.hash(password), warehouse, office);
            warehouse.addEmployee(manager);
            entityManager.persist(manager);
            entityManager.flush();
            return manager;
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

    public Manager update(Long id, String name, String password, Long warehouseId, String office) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var warehouse = warehouseBean.find(warehouseId);
            Manager manager = find(id);
            if (manager.getWarehouse() != warehouse) {
                manager.getWarehouse().removeEmployee(manager);
                warehouse.addEmployee(manager);
            }
            manager.setName(name);
            manager.setPassword(hasher.hash(password));
            manager.setWarehouse(warehouse);
            manager.setOffice(office);
            entityManager.merge(manager);
            entityManager.flush();
            return manager;
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
