package si.rso.skupina10.api.v1.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

    @GET
    public Response getUser(Integer id) {
        String resp = "Dela";
        return Response.ok(resp).build();
    }
}
