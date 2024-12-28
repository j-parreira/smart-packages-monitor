package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Customer;
import logistics.entities.Order;
import logistics.entities.Product;
import logistics.entities.Volume;
import logistics.enums.OrderStatus;
import logistics.enums.PaymentType;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityNotFoundException;
import org.hibernate.Hibernate;

import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(o.id) FROM Order o WHERE o.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public void create(Customer customer, List<Product> products, PaymentType paymentType) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            Order order = new Order(customer, products, paymentType);
            entityManager.persist(order);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Order> findAll() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

    public List<Order> findByStatus(OrderStatus status) {
        var query = entityManager.createQuery("SELECT o FROM Order o WHERE o.status = :status", Order.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    public Order find(long id) throws MyEntityNotFoundException {
        var order = entityManager.find(Order.class, id);
        if (order == null) {
            throw new MyEntityNotFoundException("Order not found");
        }
        return order;
    }

    public Order findWithVolumes(long id) throws MyEntityNotFoundException {
        var order = this.find(id);
        Hibernate.initialize(order.getVolumes());
        return order;
    }

    public Order findWithProducts(long id) throws MyEntityNotFoundException {
        var order = this.find(id);
        Hibernate.initialize(order.getProducts());
        return order;
    }

    public void update(Long id, List<Volume> volumes, OrderStatus status) throws MyEntityNotFoundException, MyConstraintViolationException {
        if(!exists(id)) {
            throw new MyEntityNotFoundException("Order not found");
        }
        try {
            Order order = find(id);
            order.setVolumes(volumes);
            order.setStatus(status);
            entityManager.merge(order);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(Long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var order = find(id);
            if (!entityManager.contains(order)) {
                order = entityManager.merge(order);
            }
            entityManager.remove(order);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
}
