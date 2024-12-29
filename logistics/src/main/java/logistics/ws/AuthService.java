package logistics.ws;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import logistics.dtos.AuthDTO;
import logistics.ejbs.UserBean;
import logistics.security.TokenIssuer;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;
    @EJB
    private UserBean userBean;

    @POST
    @Path("/login")
    public Response authenticate(@Valid AuthDTO auth) {
        if (userBean.canLogin(auth.getEmail(), auth.getPassword())) {
            String token = issuer.issue(auth.getEmail());
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    // logout

    // refresh token

    // password recovery

    // customer registration

    // user info

}
