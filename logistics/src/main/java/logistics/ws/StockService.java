package logistics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import logistics.dtos.StockDTO;
import logistics.ejbs.StockBean;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Authenticated;

@Path("stocks")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class StockService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private StockBean stockBean;

    // GET /api/stocks/
    @GET
    @Path("/")
    public Response getAllStocks() {
        return Response.ok(StockDTO.fromEntity(stockBean.findAll())).build();
    }

    // GET /api/stocks/{id}
    @GET
    @Path("{id}")
    public Response getStock(@PathParam("id") long id) throws MyEntityNotFoundException {
        var stock = stockBean.find(id);
        return Response.ok(StockDTO.fromEntity(stock)).build();
    }

    // GET /api/stocks/product/{id}
    @GET
    @Path("product/{id}")
    public Response getStockByProduct(@PathParam("id") long id) throws MyEntityNotFoundException {
        return Response.ok(StockDTO.fromEntity(stockBean.findByProduct(id))).build();
    }

    // GET /api/stocks/warehouse/{id}
    @GET
    @Path("warehouse/{id}")
    public Response getStockByWarehouse(@PathParam("id") long id) throws MyEntityNotFoundException {
        return Response.ok(StockDTO.fromEntity(stockBean.findByWarehouse(id))).build();
    }

    // POST /api/stocks/
    @POST
    @Path("/")
    public Response createStock(StockDTO stockDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        var stock = stockBean.create(
                stockDTO.getProduct(),
                stockDTO.getWarehouse(),
                stockDTO.getQuantity()
        );
        return Response.status(Response.Status.CREATED)
                .entity(StockDTO.fromEntity(stock))
                .build();
    }

    // PUT /api/stocks/{id}
    @PUT
    @Path("{id}")
    public Response updateStock(@PathParam("id") long id, long quantity) throws MyEntityNotFoundException, MyConstraintViolationException {
        var stock = stockBean.update(
                id,
                quantity
        );
        return Response.ok(StockDTO.fromEntity(stock)).build();
    }

    // DELETE /api/stocks/{id}
    @DELETE
    @Path("{id}")
    public Response deleteStock(@PathParam("id") long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        stockBean.delete(id);
        return Response.noContent().build();
    }
}
