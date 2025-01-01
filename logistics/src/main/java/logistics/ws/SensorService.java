package logistics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import logistics.dtos.ReadingDTO;
import logistics.dtos.SensorDTO;
import logistics.ejbs.SensorBean;
import logistics.entities.Sensor;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Authenticated;

@Path("sensors")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class SensorService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private SensorBean sensorBean;

    // GET /api/sensors/
    @GET
    @Path("/")
    public Response getAllSensors() {
        return Response.ok(SensorDTO.fromEntity(sensorBean.findAll())).build();
    }

    // GET /api/sensors/{id}
    @GET
    @Path("{id}")
    public Response getSensor(@PathParam("id") long id) throws MyEntityNotFoundException {
        var sensor = sensorBean.find(id);
        return Response.ok(SensorDTO.fromEntity(sensor)).build();
    }

    // GET /api/sensors/{id}/readings
    @GET
    @Path("{id}/readings")
    public Response getSensorReadings(@PathParam("id") long id) throws MyEntityNotFoundException {
        Sensor sensor = sensorBean.findWithReadings(id);
        SensorDTO sensorDTO = SensorDTO.fromEntity(sensor);
        sensorDTO.setReadings(ReadingDTO.fromEntity(sensor.getReadings()));
        return Response.ok(sensorDTO).build();
    }

    // POST /api/sensors/
    @POST
    @Path("/")
    public Response createSensor(SensorDTO sensorDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        var sensor = sensorBean.create(
                sensorDTO.getType(),
                sensorDTO.isActive(),
                sensorDTO.getMaxThreshold(),
                sensorDTO.getMinThreshold(),
                sensorDTO.getTimeInterval()
        );
        return Response.ok(SensorDTO.fromEntity(sensor)).build();
    }

    // PUT /api/sensors/{id}
    @PUT
    @Path("{id}")
    public Response updateSensor(@PathParam("id") long id, SensorDTO sensorDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        var sensor = sensorBean.update(
                id,
                sensorDTO.getVolumeId(),
                sensorDTO.isActive()
        );
        return Response.ok(SensorDTO.fromEntity(sensor)).build();
    }

    // DELETE /api/sensors/{id}
    @DELETE
    @Path("{id}")
    public Response deleteSensor(@PathParam("id") long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        sensorBean.delete(id);
        return Response.noContent().build();
    }
}
