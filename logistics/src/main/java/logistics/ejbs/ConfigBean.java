package logistics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import logistics.entities.*;
import logistics.enums.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


@Startup
@Singleton
public class ConfigBean {
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    private Random random = new Random();

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
            managerBean.create("Noah Johnson", "m1@mail.pt", "123", warehouses.get(0).getId(), "Office 1");
            managerBean.create("Emma Carter", "m2@mail.pt", "123", warehouses.get(1).getId(), "Office 2");
            managerBean.create("Liam Martinez", "m3@mail.pt", "123", warehouses.get(2).getId(), "Office 3");
        } catch (Exception e) {
            System.err.println("Some exception happened while creating managers");
            logger.severe(e.getMessage());
        }
        List<Manager> managers = managerBean.findAll();

        // Create Employees
        try {
            employeeBean.create("Noah Johnson", "e1@mail.pt", "123", warehouses.get(0).getId());
            employeeBean.create("Emma Carter", "e2@mail.pt", "123", warehouses.get(1).getId());
            employeeBean.create("Liam Martinez", "e3@mail.pt", "123", warehouses.get(2).getId());
            employeeBean.create("Olivia Brown", "e4@mail.pt", "123", warehouses.get(0).getId());
            employeeBean.create("Mason Harris", "e5@mail.pt", "123", warehouses.get(1).getId());
            employeeBean.create("Sophia Moore", "e6@mail.pt", "123", warehouses.get(2).getId());
            employeeBean.create("Elijah Lee", "e7@mail.pt", "123", warehouses.get(0).getId());
            employeeBean.create("Isabella White", "e8@mail.pt", "123", warehouses.get(1).getId());
            employeeBean.create("Lucas Garcia", "e9@mail.pt", "123", warehouses.get(2).getId());
            employeeBean.create("Mia Taylor", "e10@mail.pt", "123", warehouses.get(0).getId());
            employeeBean.create("James Wilson", "e11@mail.pt", "123", warehouses.get(1).getId());
            employeeBean.create("Amelia Thomas", "e12@mail.pt", "123", warehouses.get(2).getId());
            employeeBean.create("Ethan Jackson", "e13@mail.pt", "123", warehouses.get(0).getId());
            employeeBean.create("Charlotte Adams", "e14@mail.pt", "123", warehouses.get(1).getId());
            employeeBean.create("Alexander King", "e15@mail.pt", "123", warehouses.get(2).getId());
        } catch (Exception e) {
            System.err.println("Some exception happened while creating employees");
            logger.severe(e.getMessage());
        }
        List<Employee> employees = employeeBean.findAll();

        // Create Customers
        try {
            customerBean.create("John Doe", "c1@mail.pt", "123", "123 Elm St");
            customerBean.create("Jane Smith", "c2@mail.pt", "123", "456 Oak St");
            customerBean.create("Michael Johnson", "c3@mail.pt", "123", "789 Pine St");
            customerBean.create("Emily Davis", "c4@mail.pt", "123", "101 Maple St");
            customerBean.create("David Wilson", "c5@mail.pt", "123", "112 Birch St");
            customerBean.create("Olivia Brown", "c6@mail.pt", "123", "131 Cedar St");
            customerBean.create("Liam Garcia", "c7@mail.pt", "123", "415 Redwood St");
            customerBean.create("Sophia Martinez", "c8@mail.pt", "123", "161 Willow St");
            customerBean.create("James Taylor", "c9@mail.pt", "123", "718 Ash St");
            customerBean.create("Ava Anderson", "c10@mail.pt", "123", "920 Spruce St");
            customerBean.create("William Thomas", "c11@mail.pt", "123", "234 Oakwood St");
            customerBean.create("Isabella Moore", "c12@mail.pt", "123", "876 Pinehurst St");
            customerBean.create("Ethan Jackson", "c13@mail.pt", "123", "432 Maplewood St");
            customerBean.create("Mia White", "c14@mail.pt", "123", "654 Cherry St");
            customerBean.create("Benjamin Harris", "c15@mail.pt", "123", "987 Birchwood St");
        } catch (Exception e) {
            System.err.println("Some exception happened while creating customers");
            logger.severe(e.getMessage());
        }
        List<Customer> customers = customerBean.findAll();

        // Create Products
        try {
            productBean.create("TV LED ABC 49", ProductType.ELECTRONICS);
            productBean.create("TV LED XPT 65", ProductType.ELECTRONICS);
            productBean.create("Smartphone Model Z", ProductType.ELECTRONICS);
            productBean.create("Laptop ABC Pro 15", ProductType.ELECTRONICS);
            productBean.create("Bluetooth Speaker XYZ", ProductType.ELECTRONICS);
            productBean.create("ABC Ice Cream Box", ProductType.FROZEN_FOOD);
            productBean.create("XPT Frozen Shrimp", ProductType.FROZEN_FOOD);
            productBean.create("Chocolate Cake Frozen", ProductType.FROZEN_FOOD);
            productBean.create("Frozen Pizza Large", ProductType.FROZEN_FOOD);
            productBean.create("1Kg Apple", ProductType.FRUITS);
            productBean.create("1Kg Orange", ProductType.FRUITS);
            productBean.create("1Kg Banana", ProductType.FRUITS);
            productBean.create("1Kg Grapes", ProductType.FRUITS);
            productBean.create("Orange Juice Pack (6x)", ProductType.CARBONATED_DRINKS);
            productBean.create("Cola Pack (6x)", ProductType.CARBONATED_DRINKS);
        } catch (Exception e) {
            System.err.println("Some exception happened while creating products");
            logger.severe(e.getMessage());
        }
        List<Product> products = productBean.findAll();

        // Create Stocks
        try {
            for (Product product : products) {
                stockBean.create(product.getId(), warehouses.get(0).getId(), random.nextLong(5, 10));
                stockBean.create(product.getId(), warehouses.get(1).getId(), random.nextLong(5, 10));
                stockBean.create(product.getId(), warehouses.get(2).getId(), random.nextLong(5, 10));
            }
        } catch (Exception e) {
            System.err.println("Some exception happened while creating stocks");
            logger.severe(e.getMessage());
        }
        List<Stock> stocks = stockBean.findAll();

        // Create Orders
        try {
            for (Customer customer : customers) {
                List<Long> productIds = new LinkedList<>();
                for (Product product : products) {
                    if (random.nextBoolean()) productIds.add(product.getId());
                }
                orderBean.create(customer.getId(), productIds, PaymentType.values()[random.nextInt(3)]);
            }

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
            volumeBean.create(VolumeType.WOODEN_CRATE, products.get(1).getId(), sensorIds, employees.get(0).getId(), VolumeStatus.DISPATCHED, orders.get(0).getId());
            volumeBean.create(VolumeType.WOODEN_CRATE, products.get(0).getId(), sensorIds, employees.get(1).getId(), VolumeStatus.DISPATCHED, orders.get(1).getId());
            volumeBean.create(VolumeType.COOLER_BOX, products.get(2).getId(), sensorIds, employees.get(2).getId(), VolumeStatus.DISPATCHED, orders.get(2).getId());
            volumeBean.create(VolumeType.CARDBOARD_BOX, products.get(3).getId(), sensorIds, employees.get(3).getId(), VolumeStatus.DISPATCHED, orders.get(3).getId());
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
