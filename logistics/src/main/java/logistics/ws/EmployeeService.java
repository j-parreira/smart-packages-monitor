package logistics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import logistics.dtos.EmployeeDTO;
import logistics.dtos.VolumeDTO;
import logistics.dtos.WarehouseDTO;
import logistics.ejbs.EmployeeBean;
import logistics.entities.Employee;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Authenticated;

@Path("employees")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class EmployeeService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private EmployeeBean employeeBean;

    // GET /api/employees/
    @GET
    @Path("/")
    public Response getAllEmployees() {
        return Response.ok(EmployeeDTO.fromEntity(employeeBean.findAll())).build();
    }

    // GET /api/employees/{id}
    @GET
    @Path("{id}")
    public Response getEmployee(@PathParam("id") long id) throws MyEntityNotFoundException {
        var employee = employeeBean.find(id);
        return Response.ok(EmployeeDTO.fromEntity(employee)).build();
    }

    // GET /api/employees/{id}/volumes
    @GET
    @Path("{id}/volumes")
    public Response getEmployeeVolumes(@PathParam("id") long id) throws MyEntityNotFoundException {
        var employee = employeeBean.findWithVolumes(id);
        EmployeeDTO employeeDTO = EmployeeDTO.fromEntity(employee);
        employeeDTO.setVolumes(VolumeDTO.fromEntity(employee.getVolumes()));
        return Response.ok(employeeDTO).build();
    }

    // POST /api/employees/
    @POST
    @Path("/")
    public Response createEmployee(EmployeeDTO employeeDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        var employee = employeeBean.create(
                employeeDTO.getName(),
                employeeDTO.getEmail(),
                employeeDTO.getPassword(),
                employeeDTO.getWarehouseId()
        );
        return Response.status(Response.Status.CREATED)
                .entity(EmployeeDTO.fromEntity(employee))
                .build();
    }

    // PUT /api/employees/{id}
    @PUT
    @Path("{id}")
    public Response updateEmployee(@PathParam("id") long id, EmployeeDTO employeeDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        var employee = employeeBean.update(
                id,
                employeeDTO.getName(),
                employeeDTO.getEmail(),
                employeeDTO.getPassword(),
                employeeDTO.getWarehouseId()
        );
        return Response.ok(EmployeeDTO.fromEntity(employee)).build();
    }

    // DELETE /api/employees/{id}
    @DELETE
    @Path("{id}")
    public Response deleteEmployee(@PathParam("id") long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        employeeBean.delete(id);
        return Response.noContent().build();
    }
}
