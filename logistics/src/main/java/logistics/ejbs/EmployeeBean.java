package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Employee;
import logistics.entities.Warehouse;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Hasher;
import org.hibernate.Hibernate;

import java.util.List;

@Stateless
public class EmployeeBean {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(e.id) FROM Employee e WHERE e.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public void create(String name, String email, String password, Warehouse warehouse) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        if (exists(findByEmail(name).getId())) {
            throw new MyEntityExistsException("Employee already exists");
        }
        try {
            Employee employee = new Employee(name, email, hasher.hash(password), warehouse);
            entityManager.persist(employee);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Employee> findAll() {
        return entityManager.createNamedQuery("getAllEmployees", Employee.class).getResultList();
    }

    public Employee find(long id) throws MyEntityNotFoundException {
        var employee = entityManager.find(Employee.class, id);
        if (employee == null) {
            throw new MyEntityNotFoundException("Employee not found");
        }
        return employee;
    }

    public Employee findByEmail(String email) throws MyEntityNotFoundException {
        var query = entityManager.createNamedQuery("getEmployeeByEmail", Employee.class);
        query.setParameter("email", email);
        List<Employee> employees = query.getResultList();
        if (employees.isEmpty()) {
            throw new MyEntityNotFoundException("Employee not found");
        }
        return employees.get(0);
    }

    public Employee findWithVolumes(long id) throws MyEntityNotFoundException {
        var employee = find(id);
        Hibernate.initialize(employee.getVolumes());
        return employee;
    }

    public void update(Long id, String name, String email, String password, Warehouse warehouse) throws MyEntityNotFoundException, MyConstraintViolationException {
        if (!exists(id)) {
            throw new MyEntityNotFoundException("Employee not found");
        }
        try {
            Employee employee = find(id);
            employee.setName(name);
            //employee.setEmail(email);
            employee.setPassword(hasher.hash(password));
            employee.setWarehouse(warehouse);
            entityManager.merge(employee);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(Long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var employee = find(id);
            if (!entityManager.contains(employee)) {
                employee = entityManager.merge(employee);
            }
            entityManager.remove(employee);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

    }
}
