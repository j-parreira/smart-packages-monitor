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
public class WarehouseService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private WarehouseBean warehouseBean;

    // GET /api/warehouses/
    @GET
    @Path("/")
    public Response getAllWarehouses() {
        return Response.ok(WarehouseDTO.fromEntity(warehouseBean.findAll())).build();
    }

    // GET /api/warehouses/{id}
    @GET
    @Path("{id}")
    public Response getWarehouse(@PathParam("id") long id) throws MyEntityNotFoundException {
        var warehouse = warehouseBean.find(id);
        return Response.ok(WarehouseDTO.fromEntity(warehouse)).build();
    }

    // GET /api/warehouses/{id}/employees
    @GET
    @Path("{id}/employees")
    public Response getWarehouseEmployees(@PathParam("id") long id) throws MyEntityNotFoundException {
        var warehouse = warehouseBean.findWithEmployees(id);
        WarehouseDTO warehouseDTO = WarehouseDTO.fromEntity(warehouse);
        warehouseDTO.setEmployees(EmployeeDTO.fromEntity(warehouse.getEmployees()));
        return Response.ok(warehouseDTO).build();
    }

    // GET /api/warehouses/{id}/stocks
    @GET
    @Path("{id}/stocks")
    public Response getWarehouseStocks(@PathParam("id") long id) throws MyEntityNotFoundException {
        var warehouse = warehouseBean.findWithStocks(id);
        WarehouseDTO warehouseDTO = WarehouseDTO.fromEntity(warehouse);
        warehouseDTO.setStocks(StockDTO.fromEntity(warehouse.getStocks()));
        return Response.ok(warehouseDTO).build();
    }

    // POST /api/warehouses/
    @POST
    @Path("/")
    public Response createWarehouse(@Valid WarehouseDTO warehouseDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        var warehouse = warehouseBean.create(
                warehouseDTO.getName(),
                warehouseDTO.getLocation()
        );
        return Response.status(Response.Status.CREATED)
                .entity(WarehouseDTO.fromEntity(warehouse))
                .build();
    }

    // PUT /api/warehouses/{id}
    @PUT
    @Path("{id}")
    public Response updateWarehouse(@PathParam("id") long id,@Valid WarehouseDTO warehouseDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        var warehouse = warehouseBean.update(
                id,
                warehouseDTO.getName(),
                warehouseDTO.getLocation()
        );
        return Response.ok(WarehouseDTO.fromEntity(warehouse)).build();
    }

    // DELETE /api/warehouses/{id}
    @DELETE
    @Path("{id}")
    public Response deleteWarehouse(@PathParam("id") long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        warehouseBean.delete(id);
        return Response.noContent().build();
    }
}
