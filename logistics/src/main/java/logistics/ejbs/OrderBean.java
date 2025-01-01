package logistics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Order;
import logistics.entities.Product;
import logistics.entities.Volume;
import logistics.enums.OrderStatus;
import logistics.enums.PaymentType;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityNotFoundException;
import org.hibernate.Hibernate;

import java.util.LinkedList;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private CustomerBean customerBean;
    @EJB
    private ProductBean ProductBean;
    @EJB
    private VolumeBean volumeBean;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(o.id) FROM Order o WHERE o.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public Order create(Long customerId, List<Long> productIds, PaymentType paymentType) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            if (productIds.isEmpty()) {
                throw new MyEntityNotFoundException("Order must have at least one product");
            }
            var customer = customerBean.find(customerId);
            List<Product> products = new LinkedList<>();
            for (var productId : productIds) {
                var product = ProductBean.find(productId);
                if (product == null) {
                    throw new MyEntityNotFoundException("Product not found");
                }
                products.add(product);
            }
            Order order = new Order(customer, products, paymentType);
            customer.addOrder(order);
            for (var product : products) {
                product.addOrder(order);
            }
            entityManager.persist(order);
            entityManager.flush();
            return order;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Order> findAll() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
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

    public Order update(Long id, List<Long> volumeIds, OrderStatus status) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            List<Volume> volumes = new LinkedList<>();
            for (var volumeId : volumeIds) {
                var volume = volumeBean.find(volumeId);
                if (volume == null) {
                    throw new MyEntityNotFoundException("Volume not found");
                }
                volumes.add(volume);
            }
            Order order = find(id);
            order.setVolumes(volumes);
            order.setStatus(status);
            entityManager.merge(order);
            entityManager.flush();
            return order;
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
