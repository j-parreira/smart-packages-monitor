package logistics.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import logistics.dtos.EmployeeDTO;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Authenticated;
import logistics.ejbs.WarehouseBean;
import logistics.dtos.WarehouseDTO;
import logistics.dtos.StockDTO;

@Path("warehouses")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class WarehouseService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private WarehouseBean warehouseBean;

    // GET /api/warehouses/
    @GET
    @Path("/")
    @RolesAllowed({"Manager", "Employee", "Customer"})
    public Response getAllWarehouses() {
        return Response.ok(WarehouseDTO.fromEntity(warehouseBean.findAll())).build();
    }

    // GET /api/warehouses/{id}
    @GET
    @Path("{id}")
    @RolesAllowed({"Manager", "Employee"})
    public Response getWarehouse(@PathParam("id") long id) throws MyEntityNotFoundException {
        var warehouse = warehouseBean.find(id);
        return Response.ok(WarehouseDTO.fromEntity(warehouse)).build();
    }
}
