package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Product;
import logistics.enums.ProductType;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import org.hibernate.Hibernate;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String name) {
        Query query = entityManager.createQuery("SELECT COUNT(p.id) FROM Product p WHERE p.name = :name", Long.class);
        query.setParameter("name", name);
        return (Long)query.getSingleResult() > 0L;
    }

    public Product create(String name, ProductType type) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        if (exists(name)) {
            throw new MyEntityExistsException("Product with that name already exists");
        }
        try {
            Product product = new Product(name, type);
            entityManager.persist(product);
            entityManager.flush();
            return product;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Product> findAll() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(long id) throws MyEntityNotFoundException {
        var product = entityManager.find(Product.class, id);
        if (product == null) {
            throw new MyEntityNotFoundException("Product not found");
        }
        return product;
    }

    public long getTotalStock(long id) throws MyEntityNotFoundException {
        var product = find(id);
        var query = entityManager.createNamedQuery("getProductTotalStock", Long.class);
        query.setParameter("product", product);
        return (Long)query.getSingleResult() == null ? 0 : (Long)query.getSingleResult();
    }

    public Product findWithOrders(long id) throws MyEntityNotFoundException {
        var product = this.find(id);
        Hibernate.initialize(product.getOrders());
        return product;
    }

    public Product findWithVolumes(long id) throws MyEntityNotFoundException {
        var product = this.find(id);
        Hibernate.initialize(product.getVolumes());
        return product;
    }

    public Product findWithStocks(long id) throws MyEntityNotFoundException {
        var product = this.find(id);
        Hibernate.initialize(product.getStocks());
        return product;
    }

    public Product update(long id, String name, ProductType type) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var product = find(id);
            product.setName(name);
            product.setType(type);
            entityManager.merge(product);
            entityManager.flush();
            return product;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            Product product = find(id);
            if(getTotalStock(id)>0) {
                throw new MyConstraintViolationException("Product is still in stock");
            }
            if (!entityManager.contains(product)) {
                product = entityManager.merge(product);
            }
            entityManager.remove(product);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
}
