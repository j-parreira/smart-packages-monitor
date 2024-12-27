package logistics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {

        // Create Warehouses

        // Create Managers

        // Create Employees

        // Create Customers

        // Create Products

        // Create Stocks

        // Create Orders

        // Create Volumes

    }
}
