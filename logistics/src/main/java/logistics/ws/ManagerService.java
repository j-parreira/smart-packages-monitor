package logistics.ws;

import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import logistics.dtos.ManagerDTO;
import logistics.ejbs.ManagerBean;
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

    // GET /api/managers/
    @GET
    @Path("/")
    public Response getAllManagers() {
        return Response.ok(ManagerDTO.fromEntity(managerBean.findAll())).build();
    }

    // GET /api/managers/{id}
    @GET
    @Path("{id}")
    public Response getManager(@PathParam("id") long id) throws MyEntityNotFoundException {
        var manager = managerBean.find(id);
        return Response.ok(ManagerDTO.fromEntity(manager)).build();
    }

    // POST /api/managers/
    @POST
    @Path("/")
    public Response createManager(@Valid  ManagerDTO managerDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        managerBean.create(
                managerDTO.getName(),
                managerDTO.getEmail(),
                managerDTO.getPassword(),
                managerDTO.getWarehouse(),
                managerDTO.getOffice()
        );
        var manager = managerBean.findByEmail(managerDTO.getEmail());
        return Response.status(Response.Status.CREATED)
                .entity(ManagerDTO.fromEntity(manager))
                .build();
    }

    // PUT /api/managers/{id}
    @PUT
    @Path("{id}")
    public Response updateManager(@PathParam("id") long id, @Valid ManagerDTO managerDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        var manager = managerBean.find(id);
        managerBean.update(
                id,
                managerDTO.getName(),
                managerDTO.getEmail(),
                managerDTO.getPassword(),
                managerDTO.getWarehouse(),
                managerDTO.getOffice()
        );
        return Response.ok(ManagerDTO.fromEntity(manager)).build();
    }

    // DELETE /api/managers/{id}
    @DELETE
    @Path("{id}")
    public Response deleteManager(@PathParam("id") long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        managerBean.delete(id);
        return Response.noContent().build();
    }
}
