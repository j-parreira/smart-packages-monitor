package logistics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import logistics.dtos.*;
import logistics.ejbs.VolumeBean;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Authenticated;

import java.util.LinkedList;
import java.util.List;

@Path("volumes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class VolumeService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private VolumeBean volumeBean;

    // GET /api/volumes/
    @GET
    @Path("/")
    public Response getAllVolumes() {
        return Response.ok(VolumeDTO.fromEntity(volumeBean.findAll())).build();
    }

    // GET /api/volumes/{id}
    @GET
    @Path("{id}")
    public Response getVolume(@PathParam("id") long id) throws MyEntityNotFoundException {
        var volume = volumeBean.find(id);
        return Response.ok(VolumeDTO.fromEntity(volume)).build();
    }

    // GET /api/volumes/{id}/sensors
    @GET
    @Path("{id}/sensors")
    public Response getVolumeSensors(@PathParam("id") long id) throws MyEntityNotFoundException {
        var volume = volumeBean.findWithSensors(id);
        var volumeDTO = VolumeDTO.fromEntity(volume);
        volumeDTO.setSensors(SensorDTO.fromEntity(volume.getSensors()));
        return Response.ok(volumeDTO).build();
    }

    // POST /api/volumes/
    @POST
    @Path("/")
    public Response createVolume(VolumeDTO volumeDTO) throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        List<Long> sensorIds = new LinkedList<>();
        for (SensorDTO sensorDTO : volumeDTO.getSensors()) {
            sensorIds.add(sensorDTO.getId());
        }
        var volume = volumeBean.create(
                volumeDTO.getType(),
                volumeDTO.getVolumeNumber(),
                volumeDTO.getProductId(),
                sensorIds,
                volumeDTO.getDispatchedByEmployeeId(),
                volumeDTO.getStatus(),
                volumeDTO.getOrderId()
        );
        return Response.ok(VolumeDTO.fromEntity(volume)).build();
    }

    // PUT /api/volumes/{id}
    @PUT
    @Path("{id}")
    public Response updateVolume(@PathParam("id") long id, VolumeDTO volumeDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        var volume = volumeBean.update(
                id,
                volumeDTO.getStatus(),
                volumeDTO.getArrivedAt()
        );
        return Response.ok(VolumeDTO.fromEntity(volume)).build();
    }

    // DELETE /api/volumes/{id}
    @DELETE
    @Path("{id}")
    public Response deleteVolume(@PathParam("id") long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        volumeBean.delete(id);
        return Response.noContent().build();
    }
}
