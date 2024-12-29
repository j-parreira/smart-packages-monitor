package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Warehouse;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import org.hibernate.Hibernate;

import java.util.List;

@Stateless
public class WarehouseBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String name) {
        Query query = entityManager.createQuery("SELECT COUNT(w.name) FROM Warehouse w WHERE w.name = :name", Long.class);
        query.setParameter("name", name);
        return (Long) query.getSingleResult() > 0L;
    }

    public Warehouse create(String name, String location) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        if (exists(name)) {
            throw new MyEntityExistsException("Warehouse already exists");
        }
        try {
            Warehouse warehouse = new Warehouse(name, location);
            entityManager.persist(warehouse);
            entityManager.flush();
            return warehouse;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Warehouse> findAll() {
        return entityManager.createNamedQuery("getAllWarehouses", Warehouse.class).getResultList();
    }

    public Warehouse find(long id) throws MyEntityNotFoundException {
        var warehouse = entityManager.find(Warehouse.class, id);
        if (warehouse == null) {
            throw new MyEntityNotFoundException("Warehouse not found");
        }
        return warehouse;
    }

    public Warehouse findByName(String name) throws MyEntityNotFoundException {
        var query = entityManager.createNamedQuery("getWarehouseByName", Warehouse.class);
        query.setParameter("name", name);
        List<Warehouse> warehouses = query.getResultList();
        return warehouses.get(0);
    }

    public Warehouse findWithEmployees(Long id) throws MyEntityNotFoundException {
        var warehouse = this.find(id);
        Hibernate.initialize(warehouse.getEmployees());
        return warehouse;
    }

    public Warehouse findWithStocks(Long id) throws MyEntityNotFoundException {
        var warehouse = this.find(id);
        Hibernate.initialize(warehouse.getStocks());
        return warehouse;
    }

    public Warehouse update(long id, String name, String location) throws MyEntityNotFoundException, MyConstraintViolationException {
        if (!exists(name)) {
            throw new MyEntityNotFoundException("Warehouse not found");
        }
        try {
            var warehouse = find(id);
            warehouse.setName(name);
            warehouse.setLocation(location);
            return entityManager.merge(warehouse);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var warehouse = find(id);
            if (!entityManager.contains(warehouse)) {
                warehouse = entityManager.merge(warehouse);
            }
            entityManager.remove(warehouse);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
}
