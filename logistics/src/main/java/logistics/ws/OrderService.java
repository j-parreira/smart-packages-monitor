package logistics.ws;

import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import logistics.dtos.CustomerDTO;
import logistics.dtos.OrderDTO;
import logistics.dtos.VolumeDTO;
import logistics.dtos.ProductDTO;
import logistics.enums.OrderStatus;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Authenticated;
import logistics.ejbs.OrderBean;

@Path("orders")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class OrderService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private OrderBean orderBean;

    // GET /api/orders/
    @GET
    @Path("/")
    public Response getAllOrders() {
        return Response.ok(OrderDTO.fromEntity(orderBean.findAll())).build();
    }

    // GET /api/orders/{id}
    @GET
    @Path("{id}")
    public Response getOrder(@PathParam("id") long id) throws MyEntityNotFoundException {
        var order = orderBean.find(id);
        return Response.ok(OrderDTO.fromEntity(order)).build();
    }

    // GET /api/orders/{id}/products
    @GET
    @Path("{id}/products")
    public Response getOrderProducts(@PathParam("id") long id) throws MyEntityNotFoundException {
        var order = orderBean.findWithProducts(id);
        OrderDTO orderDTO = OrderDTO.fromEntity(order);
        orderDTO.setProducts(ProductDTO.fromEntity(order.getProducts()));
        return Response.ok(orderDTO).build();
    }

    // GET /api/orders/{id}/volumes
    @GET
    @Path("{id}/volumes")
    public Response getOrderVolumes(@PathParam("id") long id) throws MyEntityNotFoundException {
        var order = orderBean.findWithVolumes(id);
        OrderDTO orderDTO = OrderDTO.fromEntity(order);
        orderDTO.setVolumes(VolumeDTO.fromEntity(order.getVolumes()));
        return Response.ok(orderDTO).build();
    }

    // GET /api/orders/{orderStatus}
    @GET
    @Path("{orderStatus}")
    public Response getOrdersByStatus(@PathParam("orderStatus") OrderStatus orderStatus) {
        return Response.ok(OrderDTO.fromEntity(orderBean.findByStatus(orderStatus))).build();
    }

    // POST /api/orders/
    @POST
    @Path("/")
    public Response createOrder(OrderDTO orderDTO) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        var order = orderBean.create(
                CustomerDTO.toEntity(orderDTO.getCustomer()),
                ProductDTO.toEntity(orderDTO.getProducts()),
                orderDTO.getPaymentType()
        );
        return Response.status(Response.Status.CREATED)
            .entity(OrderDTO.fromEntity(order))
            .build();
    }

    // PUT /api/orders/{id}
    @PUT
    @Path("{id}")
    public Response updateOrder(@PathParam("id") long id, @Valid OrderDTO orderDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        var order = orderBean.update(
            id,
            VolumeDTO.toEntity(orderDTO.getVolumes()),
            orderDTO.getStatus()
        );
        return Response.ok(OrderDTO.fromEntity(order)).build();
    }

    // DELETE /api/orders/{id}
    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        orderBean.delete(id);
        return Response.noContent().build();
    }

}
