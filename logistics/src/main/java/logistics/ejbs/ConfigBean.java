package logistics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import logistics.entities.*;
import logistics.enums.*;

import java.util.LinkedList;
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

    @EJB
    private OrderBean orderBean;

    @EJB
    private VolumeBean volumeBean;

    @EJB
    private SensorBean sensorBean;

    @EJB
    private ReadingBean readingBean;

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
            managerBean.create("Manager 1", "m1@mail.pt", "123", warehouses.get(0).getId(), "Office 1");
            managerBean.create("Manager 2", "m2@mail.pt", "123", warehouses.get(1).getId(), "Office 2");
            managerBean.create("Manager 3", "m3@mail.pt", "123", warehouses.get(2).getId(), "Office 3");
        } catch (Exception e) {
            System.err.println("Some exception happened while creating managers");
            logger.severe(e.getMessage());
        }
        List<Manager> managers = managerBean.findAll();

        // Create Employees
        try {
            employeeBean.create("Employee 1", "e1@mail.pt", "123", warehouses.get(0).getId());
            employeeBean.create("Employee 2", "e2@mail.pt", "123", warehouses.get(0).getId());
            employeeBean.create("Employee 3", "e3@mail.pt", "123", warehouses.get(1).getId());
            employeeBean.create("Employee 4", "e4@mail.pt", "123", warehouses.get(1).getId());
            employeeBean.create("Employee 5", "e5@mail.pt", "123", warehouses.get(2).getId());
            employeeBean.create("Employee 6", "e6@mail.pt", "123", warehouses.get(2).getId());
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
            stockBean.create(products.get(0).getId(), warehouses.get(0).getId(), 15L);
            stockBean.create(products.get(0).getId(), warehouses.get(1).getId(), 20L);
            stockBean.create(products.get(1).getId(), warehouses.get(1).getId(), 5L);
            stockBean.create(products.get(1).getId(), warehouses.get(2).getId(), 10L);
            stockBean.create(products.get(2).getId(), warehouses.get(0).getId(), 15L);
            stockBean.create(products.get(2).getId(), warehouses.get(2).getId(), 20L);
            stockBean.create(products.get(3).getId(), warehouses.get(1).getId(), 5L);
            stockBean.create(products.get(3).getId(), warehouses.get(2).getId(), 10L);
            stockBean.create(products.get(4).getId(), warehouses.get(0).getId(), 15L);
            stockBean.create(products.get(4).getId(), warehouses.get(1).getId(), 20L);
            stockBean.create(products.get(5).getId(), warehouses.get(0).getId(), 5L);
            stockBean.create(products.get(5).getId(), warehouses.get(1).getId(), 10L);
            stockBean.create(products.get(6).getId(), warehouses.get(2).getId(), 15L);
            stockBean.create(products.get(6).getId(), warehouses.get(0).getId(), 20L);
            stockBean.create(products.get(7).getId(), warehouses.get(1).getId(), 5L);
            stockBean.create(products.get(7).getId(), warehouses.get(2).getId(), 10L);
        } catch (Exception e) {
            System.err.println("Some exception happened while creating stocks");
            logger.severe(e.getMessage());
        }
        List<Stock> stocks = stockBean.findAll();

        List<Long> productIds = new LinkedList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        // Create Orders
        try {
            orderBean.create(customers.get(0).getId(), productIds, PaymentType.MBWAY);
            orderBean.create(customers.get(1).getId(), productIds, PaymentType.MB);
            orderBean.create(customers.get(2).getId(), productIds, PaymentType.PAYPAL);
            orderBean.create(customers.get(3).getId(), productIds, PaymentType.MBWAY);
            orderBean.create(customers.get(4).getId(), productIds, PaymentType.MBWAY);
            orderBean.create(customers.get(5).getId(), productIds, PaymentType.MBWAY);
        } catch (Exception e) {
            System.err.println("Some exception happened while creating orders");
            logger.severe(e.getMessage());
        }
        List<Order> orders = orderBean.findAll();

        // Create Sensors
        try {
            sensorBean.create(SensorType.TEMPERATURE, true, 30, 10, 1000);
            sensorBean.create(SensorType.ACCELERATION, true, 80, 20, 1000);
            sensorBean.create(SensorType.HUMIDITY, true, 50, 30, 1000);
            sensorBean.create(SensorType.GPS, true, 0, 0, 1000);
        } catch (Exception e) {
            System.err.println("Some exception happened while creating sensors");
            logger.severe(e.getMessage());
        }
        List<Sensor> sensors = sensorBean.findAll();
        List<Long> sensorIds = new LinkedList<>();
        for (Sensor sensor : sensors) {
            sensorIds.add(sensor.getId());
        }
        // Create Volumes
        try {
            volumeBean.create(VolumeType.WOODEN_CRATE, 1, products.get(1).getId(), sensorIds, employees.get(0).getId(), VolumeStatus.PROCESSING, orders.get(0).getId());
            volumeBean.create(VolumeType.WOODEN_CRATE, 2, products.get(0).getId(), sensorIds, employees.get(1).getId(), VolumeStatus.PROCESSING, orders.get(1).getId());
            volumeBean.create(VolumeType.WOODEN_CRATE, 3, products.get(2).getId(), sensorIds, employees.get(2).getId(), VolumeStatus.PROCESSING, orders.get(2).getId());
            volumeBean.create(VolumeType.WOODEN_CRATE, 4, products.get(3).getId(), sensorIds, employees.get(3).getId(), VolumeStatus.PROCESSING, orders.get(3).getId());
        } catch (Exception e) {
            System.err.println("Some exception happened while creating volumes");
            logger.severe(e.getMessage());
        }
        List<Volume> volumes = volumeBean.findAll();

        // Create Readings
        try {
            readingBean.create(sensors.get(0).getId(), 25, 10);
            readingBean.create(sensors.get(1).getId(), 75, 20);
            readingBean.create(sensors.get(2).getId(), 45, 30);
            readingBean.create(sensors.get(3).getId(), 0, 0);
        } catch (Exception e) {
            System.err.println("Some exception happened while creating readings");
            logger.severe(e.getMessage());
        }
        List<Reading> readings = readingBean.findAll();
    }
}
