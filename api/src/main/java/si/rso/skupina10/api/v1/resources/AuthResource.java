package si.rso.skupina10.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.rso.skupina10.dtos.UserDto;
import si.rso.skupina10.services.AuthBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

    private Logger log = Logger.getLogger(AuthResource.class.getName());

    @Context
    protected UriInfo uriInfo;

    @Inject
    private AuthBean authBean;

    @Operation(description = "Get all users.", summary = "Get all users")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of users data",
                    content = @Content(schema = @Schema(implementation = UserDto.class, type = SchemaType.ARRAY))
                    //,headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getUsers() {
        log.info("getUsers called.");
        List<UserDto> orders = authBean.getUsers(uriInfo);

        log.info("getUsers output: " + orders.toString());
        return Response.status(Response.Status.OK).entity(orders).build();
    }

    @GET
    @Path("{id}")
    @Operation(description = "Get info about user with id.", summary = "User info")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User info",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ), @APIResponse(responseCode = "404", description = "User not found")
    })
    public Response getUserById(@PathParam("id") Integer id) {
        log.info("getUserById called with id " + id);
        UserDto userDto = authBean.getUser(id);
        if (userDto != null) {
            log.info("getuserById was successful " + userDto);
            return Response.ok(userDto).build();
        } else {
            log.severe("Did not find user with this id (" + id + ").");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Operation(description = "Add new user", summary = "Add new user")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "New user added", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @APIResponse(responseCode = "400", description = "Error adding new user")
    })
    public Response addNewUser(@RequestBody(
            description = "User DTO object",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = UserDto.class)
            )
    ) UserDto userDto) {
        log.info("addNewUser " + userDto);
        UserDto newOrder = authBean.addUser(userDto);
        if (newOrder == null) {
            log.severe("Could not add new user.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        log.info("User created.");
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{id}")
    @Operation(description = "Delete user", summary = "Delete user by id")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "User deleted"),
            @APIResponse(responseCode = "400", description = "Error deleting user")
    })
    public Response deleteUser(@PathParam("id") Integer id) {
        log.info("deleteUser called with id (" + id + ")");
        boolean deleted = authBean.removeUser(id);
        if (deleted) {
            log.info("User deleted id (" + id + ")");
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        log.severe("Could not delete user with id (" + id + ")");
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
