package logistics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import logistics.entities.*;
import logistics.enums.ProductType;

import java.util.List;
import java.util.logging.Logger;


@Startup
@Singleton
public class ConfigBean {
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @EJB
    private WarehouseBean warehouseBean;

    @EJB
    private ManagerBean managerBean;

    @EJB
    private EmployeeBean employeeBean;

    @EJB
    private CustomerBean customerBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private StockBean stockBean;

    @PostConstruct
    public void populateDB() {

        // Create Warehouses
        try {
            warehouseBean.create("Warehouse 1", "Location 1");
            warehouseBean.create("Warehouse 2", "Location 2");
            warehouseBean.create("Warehouse 3", "Location 3");
        } catch (Exception e) {
            System.err.println("Some exception happened while creating warehouse");
            logger.severe(e.getMessage());
        }
        List<Warehouse> warehouses = warehouseBean.findAll();

        // Create Managers
        try {
            managerBean.create("Manager 1", "m1@mail.pt", "123", warehouses.get(0), "Office 1");
            managerBean.create("Manager 2", "m2@mail.pt", "123", warehouses.get(1), "Office 2");
            managerBean.create("Manager 3", "m3@mail.pt", "123", warehouses.get(2), "Office 3");
        } catch (Exception e) {
            System.err.println("Some exception happened while creating managers");
            logger.severe(e.getMessage());
        }
        List<Manager> managers = managerBean.findAll();

        // Create Employees
        try {
            employeeBean.create("Employee 1", "e1@mail.pt", "123", warehouses.get(0));
            employeeBean.create("Employee 2", "e2@mail.pt", "123", warehouses.get(0));
            employeeBean.create("Employee 3", "e3@mail.pt", "123", warehouses.get(1));
            employeeBean.create("Employee 4", "e4@mail.pt", "123", warehouses.get(1));
            employeeBean.create("Employee 5", "e5@mail.pt", "123", warehouses.get(2));
            employeeBean.create("Employee 6", "e6@mail.pt", "123", warehouses.get(2));
        } catch (Exception e) {
            System.err.println("Some exception happened while creating employees");
            logger.severe(e.getMessage());
        }
        List<Employee> employees = employeeBean.findAll();

        // Create Customers
        try {
            customerBean.create("Customer 1", "c1@mail.pt", "123", "Rua Carlos I");
            customerBean.create("Customer 2", "c2@mail.pt", "123", "Rua Carlos II");
            customerBean.create("Customer 3", "c3@mail.pt", "123", "Rua Carlos III");
            customerBean.create("Customer 4", "c4@mail.pt", "123", "Rua Carlos IV");
            customerBean.create("Customer 5", "c5@mail.pt", "123", "Rua Carlos V");
            customerBean.create("Customer 6", "c6@mail.pt", "123", "Rua Carlos VI");
        } catch (Exception e) {
            System.err.println("Some exception happened while creating customers");
            logger.severe(e.getMessage());
        }
        List<Customer> customers = customerBean.findAll();

        // Create Products
        try {
            productBean.create("Product 1", ProductType.ELECTRONICS);
            productBean.create("Product 2", ProductType.ELECTRONICS);
            productBean.create("Product 3", ProductType.FROZEN_FOOD);
            productBean.create("Product 4", ProductType.FROZEN_FOOD);
            productBean.create("Product 5", ProductType.FRUITS);
            productBean.create("Product 6", ProductType.FRUITS);
            productBean.create("Product 7", ProductType.CARBONATED_DRINKS);
            productBean.create("Product 8", ProductType.CARBONATED_DRINKS);
        } catch (Exception e) {
            System.err.println("Some exception happened while creating products");
            logger.severe(e.getMessage());
        }
        List<Product> products = productBean.findAll();

        // Create Stocks
        try {
            stockBean.create(products.get(0), warehouses.get(0), 15L);
            stockBean.create(products.get(0), warehouses.get(1), 20L);
            stockBean.create(products.get(1), warehouses.get(1), 5L);
            stockBean.create(products.get(1), warehouses.get(2), 10L);
            stockBean.create(products.get(2), warehouses.get(0), 15L);
            stockBean.create(products.get(2), warehouses.get(2), 20L);
            stockBean.create(products.get(3), warehouses.get(1), 5L);
            stockBean.create(products.get(3), warehouses.get(2), 10L);
            stockBean.create(products.get(4), warehouses.get(0), 15L);
            stockBean.create(products.get(4), warehouses.get(1), 20L);
            stockBean.create(products.get(5), warehouses.get(0), 5L);
            stockBean.create(products.get(5), warehouses.get(1), 10L);
            stockBean.create(products.get(6), warehouses.get(2), 15L);
            stockBean.create(products.get(6), warehouses.get(0), 20L);
            stockBean.create(products.get(7), warehouses.get(1), 5L);
            stockBean.create(products.get(7), warehouses.get(2), 10L);
        } catch (Exception e) {
            System.err.println("Some exception happened while creating stocks");
            logger.severe(e.getMessage());
        }
        List<Stock> stocks = stockBean.findAll();

        // Create Orders

        // Create Volumes

    }
}
