package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Product;
import logistics.entities.Stock;
import logistics.entities.Warehouse;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class StockBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(s.id) FROM Stock s WHERE s.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public Stock create(Product product, Warehouse warehouse, Long quantity) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Stock existingStock = findByProductAndWarehouse(product.getId(), warehouse.getId());
        if (existingStock != null) {
            throw new MyEntityExistsException("Stock already exists");
        }
        try {
            Stock stock = new Stock(product, warehouse, quantity);
            entityManager.persist(stock);
            entityManager.flush();
            return stock;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Stock> findAll() {
        return entityManager.createNamedQuery("getAllStocks", Stock.class).getResultList();
    }

    public Stock find(long id) throws MyEntityNotFoundException {
        var stock = entityManager.find(Stock.class, id);
        if (stock == null) {
            throw new MyEntityNotFoundException("Stock not found");
        }
        return stock;
    }

    public Stock findByProductAndWarehouse(long productId, long warehouseId) {
        var query = entityManager.createNamedQuery("getStockByProductAndWarehouse", Stock.class);
        query.setParameter("productId", productId);
        query.setParameter("warehouseId", warehouseId);
        List<Stock> stocks = query.getResultList();
        return stocks.isEmpty() ? null : stocks.get(0);
    }

    public List<Stock> findByProduct(long productId) {
        var query = entityManager.createNamedQuery("getStockByProduct", Stock.class);
        query.setParameter("productId", productId);
        return query.getResultList();
    }

    public List<Stock> findByWarehouse(long warehouseId) {
        var query = entityManager.createNamedQuery("getStockByWarehouse", Stock.class);
        query.setParameter("warehouseId", warehouseId);
        return query.getResultList();
    }

    public Stock update(Long id, Long quantity) throws MyEntityNotFoundException, MyConstraintViolationException {
        if (!exists(id)) {
            throw new MyEntityNotFoundException("Stock not found");
        }
        try {
            Stock stock = find(id);
            stock.setQuantity(quantity);
            entityManager.merge(stock);
            entityManager.flush();
            return stock;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(Long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var stock = find(id);
            if (!entityManager.contains(stock)) {
                stock = entityManager.merge(stock);
            }
            entityManager.remove(stock);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
}
