package logistics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import logistics.dtos.ReadingDTO;
import logistics.dtos.SensorDTO;
import logistics.ejbs.ReadingBean;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import logistics.security.Authenticated;

@Path("readings")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class ReadingService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private ReadingBean readingBean;

    // GET /api/readings/
    @GET
    @Path("/")
    public Response getAllReadings() {
        return Response.ok(ReadingDTO.fromEntity(readingBean.findAll())).build();
    }

    // GET /api/readings/{id}
    @GET
    @Path("{id}")
    public Response getReading(@PathParam("id") long id) throws MyEntityNotFoundException {
        var reading = readingBean.find(id);
        return Response.ok(ReadingDTO.fromEntity(reading)).build();
    }

    // GET /api/readings/sensor/{id}
    @GET
    @Path("sensor/{id}")
    public Response getSensorReadings(@PathParam("id") long id) throws MyEntityNotFoundException {
        return Response.ok(ReadingDTO.fromEntity(readingBean.findBySensor(id))).build();
    }

    // POST /api/readings/
    @POST
    @Path("/")
    public Response createReading(ReadingDTO readingDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        var reading = readingBean.create(
                SensorDTO.toEntity(readingDTO.getSensor()),
                readingDTO.getValueOne(),
                readingDTO.getValueTwo()
        );
        return Response.ok(ReadingDTO.fromEntity(reading)).build();
    }
}
