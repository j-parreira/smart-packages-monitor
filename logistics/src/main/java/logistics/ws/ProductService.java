package logistics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import logistics.dtos.OrderDTO;
import logistics.dtos.ProductDTO;
import logistics.dtos.StockDTO;
import logistics.dtos.VolumeDTO;
import logistics.ejbs.ProductBean;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Authenticated;

@Path("products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private ProductBean productBean;

    // GET /api/products/
    @GET
    @Path("/")
    public Response getAllProducts() {
        return Response.ok(ProductDTO.fromEntity(productBean.findAll())).build();
    }

    // GET /api/products/{id}
    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id") long id) throws MyEntityNotFoundException {
        var product = productBean.find(id);
        return Response.ok(ProductDTO.fromEntity(product)).build();
    }

    // GET /api/products/{id}/totalstock
    @GET
    @Path("{id}/totalstock")
    public Response getProductTotalStock(@PathParam("id") long id) throws MyEntityNotFoundException {
        var totalStock = productBean.getTotalStock(id);
        return Response.ok(totalStock).build();
    }

    // GET /api/products/{id}/orders
    @GET
    @Path("{id}/orders")
    public Response getProductOrders(@PathParam("id") long id) throws MyEntityNotFoundException {
        var product = productBean.findWithOrders(id);
        ProductDTO productDTO = ProductDTO.fromEntity(product);
        productDTO.setOrders(OrderDTO.fromEntity(product.getOrders()));
        return Response.ok(productDTO).build();
    }

    // GET /api/products/{id}/volumes
    @GET
    @Path("{id}/volumes")
    public Response getProductVolumes(@PathParam("id") long id) throws MyEntityNotFoundException {
        var product = productBean.findWithVolumes(id);
        ProductDTO productDTO = ProductDTO.fromEntity(product);
        productDTO.setVolumes(VolumeDTO.fromEntity(product.getVolumes()));
        return Response.ok(productDTO).build();
    }

    // GET /api/products/{id}/stocks
    @GET
    @Path("{id}/stocks")
    public Response getProductStocks(@PathParam("id") long id) throws MyEntityNotFoundException {
        var product = productBean.findWithStocks(id);
        ProductDTO productDTO = ProductDTO.fromEntity(product);
        productDTO.setStocks(StockDTO.fromEntity(product.getStocks()));
        return Response.ok(productDTO).build();
    }

    // POST /api/products/
    @POST
    @Path("/")
    public Response createProduct(ProductDTO productDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        var product = productBean.create(
                productDTO.getName(),
                productDTO.getType()
        );
        return Response.status(Response.Status.CREATED)
                .entity(ProductDTO.fromEntity(product))
                .build();
    }

    // PUT /api/products/{id}
    @PUT
    @Path("{id}")
    public Response updateProduct(@PathParam("id") long id, ProductDTO productDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        var product = productBean.update(
                id,
                productDTO.getName(),
                productDTO.getType()
        );
        return Response.ok(ProductDTO.fromEntity(product)).build();
    }

    // DELETE /api/products/{id}
    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam("id") long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        productBean.delete(id);
        return Response.noContent().build();
    }
}
