package logistics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @EJB
    private WarehouseBean warehouseBean;

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

        // Create Managers

        // Create Employees

        // Create Customers

        // Create Products

        // Create Stocks

        // Create Orders

        // Create Volumes

    }
}
