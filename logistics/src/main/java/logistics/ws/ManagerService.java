package logistics.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import logistics.dtos.ManagerDTO;
import logistics.dtos.WarehouseDTO;
import logistics.ejbs.ManagerBean;
import logistics.entities.Manager;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Authenticated;

@Path("managers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class ManagerService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private ManagerBean managerBean;

    // GET /api/managers/{id}
    @GET
    @Path("{id}")
    @RolesAllowed({"Manager"})
    public Response getManager(@PathParam("id") long id) throws MyEntityNotFoundException {
        var manager = managerBean.find(id);
        return Response.ok(ManagerDTO.fromEntity(manager)).build();
    }
}
