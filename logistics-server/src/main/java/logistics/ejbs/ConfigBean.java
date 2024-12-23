package logistics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    private CustomerBean customerBean;

    @PostConstruct
    public void populateDB() {
        customerBean.createCustomer("Carlos");
    }
}
