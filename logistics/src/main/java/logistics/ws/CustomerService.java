package logistics.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import logistics.dtos.CustomerDTO;
import logistics.dtos.OrderDTO;
import logistics.ejbs.CustomerBean;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Authenticated;

import java.util.List;

@Path("customers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class CustomerService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private CustomerBean customerBean;

    // GET /api/customers/
    @GET
    @Path("/")
    public Response getAllCustomers() {
        return Response.ok(CustomerDTO.fromEntity(customerBean.findAll())).build();
    }

    // GET /api/customers/{id}
    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") long id) throws MyEntityNotFoundException {
        var customer = customerBean.find(id);
        return Response.ok(CustomerDTO.fromEntity(customer)).build();
    }

    // GET /api/customers/{id}/orders
    @GET
    @Path("{id}/orders")
    public Response getCustomerOrders(@PathParam("id") long id) throws MyEntityNotFoundException {
        var customer = customerBean.findWithOrders(id);
        CustomerDTO customerDTO = CustomerDTO.fromEntity(customer);
        customerDTO.setOrders(OrderDTO.fromEntity(customer.getOrders()));
        return Response.ok(customerDTO).build();
    }

    // POST /api/customers/
    @POST
    @Path("/")
    public Response createCustomer(@Valid CustomerDTO customerDTO) throws MyConstraintViolationException, MyEntityExistsException, MyEntityNotFoundException {
        customerBean.create(
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getPassword(),
                customerDTO.getAddress()
        );
        var customer = customerBean.findByEmail(customerDTO.getEmail());
        return Response.status(Response.Status.CREATED)
                .entity(CustomerDTO.fromEntity(customer))
                .build();
    }

    // PUT /api/customers/{id}
    @PUT
    @Path("{id}")
    public Response updateCustomer(@PathParam("id") long id, @Valid CustomerDTO customerDTO) throws MyConstraintViolationException, MyEntityNotFoundException {
        var customer = customerBean.find(id);
        customerBean.update(
                id,
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getPassword(),
                customerDTO.getAddress()
        );
        return Response.ok(CustomerDTO.fromEntity(customer)).build();
    }

    // DELETE /api/customers/{id}
    @DELETE
    @Path("{id}")
    public Response deleteCustomer(@PathParam("id") long id) throws MyEntityNotFoundException {
        customerBean.delete(id);
        return Response.noContent().build();
    }
}
