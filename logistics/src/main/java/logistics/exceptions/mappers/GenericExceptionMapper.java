package logistics.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger logger = Logger.getLogger(Exception.class.getCanonicalName());

    @Override
    public Response toResponse(Exception e) {
        String errorMsg = "An unexpected error occurred: " + e.getMessage();
        logger.severe("Unhandled Exception: " + errorMsg);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMsg).build();
    }
}
