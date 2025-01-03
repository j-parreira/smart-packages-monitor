package logistics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Employee;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Hasher;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Stateless
public class EmployeeBean {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;
    @EJB
    private WarehouseBean warehouseBean;

    public boolean exists(String email) {
        Query query = entityManager.createQuery("SELECT COUNT(e.email) FROM Employee e WHERE e.email = :email", Long.class);
        query.setParameter("email", email);
        return (Long) query.getSingleResult() > 0L;
    }

    public Employee create(String name, String email, String password, Long warehouseId) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        if (exists(email)) {
            throw new MyEntityExistsException("Employee with that email already exists");
        }
        try {
            var warehouse = warehouseBean.find(warehouseId);
            Employee employee = new Employee(name, email, hasher.hash(password), warehouse);
            warehouse.addEmployee(employee);
            entityManager.persist(employee);
            entityManager.flush();
            return employee;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Employee> findAll() {
        return entityManager.createNamedQuery("getAllEmployees", Employee.class).getResultList();
    }

    public Employee find(long id) throws MyEntityNotFoundException {
        var employee = entityManager.find(Employee.class, id);
        if (employee == null || employee.getClass() != Employee.class) {
            throw new MyEntityNotFoundException("Employee not found");
        }
        return employee;
    }

    public Employee findWithVolumes(long id) throws MyEntityNotFoundException {
        var employee = find(id);
        Hibernate.initialize(employee.getVolumes());
        return employee;
    }

    public Employee update(Long id, String name, String email, String password, Long warehouseId) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var warehouse = warehouseBean.find(warehouseId);
            Employee employee = find(id);
            if (!email.isBlank()) {
                if (exists(email) && !Objects.equals(employee.getEmail(), email)) {
                    throw new MyConstraintViolationException("Account with that email already exists");
                }
                employee.setEmail(email);

            }if (employee.getWarehouse() != warehouse) {
                employee.getWarehouse().removeEmployee(employee);
                warehouse.addEmployee(employee);
            }
            if (!name.isBlank()) {
                employee.setName(name);
            }

            if (!password.isBlank()) {
                employee.setPassword(hasher.hash(password));
            }
            entityManager.merge(employee);
            entityManager.flush();
            return employee;
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
