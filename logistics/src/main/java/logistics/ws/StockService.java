package logistics.ws;

import jakarta.annotation.security.RolesAllowed;
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

    // POST /api/stocks/
    @POST
    @Path("/")
    @RolesAllowed({"Manager", "Employee"})
    public Response createStock(StockDTO stockDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        var stock = stockBean.create(
                stockDTO.getProductId(),
                stockDTO.getWarehouseId(),
                stockDTO.getQuantity()
        );
        return Response.status(Response.Status.CREATED)
                .entity(StockDTO.fromEntity(stock))
                .build();
    }
}
