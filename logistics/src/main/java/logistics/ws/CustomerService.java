package logistics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import logistics.dtos.CustomerDTO;
import logistics.ejbs.CustomerBean;

import java.util.List;

@Path("/customers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CustomerService {
    @EJB
    private CustomerBean customerBean;

    @GET
    @Path("/")
    public List<CustomerDTO> getAllCustomers() {
        return CustomerDTO.from(customerBean.getAllCustomers());
    }
}
