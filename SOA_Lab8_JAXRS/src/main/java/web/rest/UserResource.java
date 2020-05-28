package web.rest;

import io.swagger.annotations.*;
import model.User;
import repository.CRUDRepository;
import util.RandomUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Api(tags={"User Resource"})
@RequestScoped
public class UserResource {
    @Inject
    private CRUDRepository<User> UserCRUDRepository;

    public UserResource(){
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch all user")
    @ApiResponses({
            @ApiResponse(code=200, message="Success"),
            @ApiResponse(code=404, message="Not found")
    })
    public Response getAll() {
        List<User> entities = UserCRUDRepository.getAll(User.class);
        if(entities == null || entities.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        }

        return Response.ok(entities).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch a user")
    @ApiResponses({
            @ApiResponse(code=200, message="Success"),
            @ApiResponse(code=404, message="Not found")
    })
    public Response get(@ApiParam(required = true) @PathParam("id") final Integer id) {
        if(id == null) {
            return Response.serverError().entity("ID cannot be blank").build();
        }
        User entity = UserCRUDRepository.get(User.class ,id);
        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }

        return Response.ok(entity).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create a user")
    @ApiResponses({
            @ApiResponse(code=200, message="Success"),
            @ApiResponse(code=400, message="Request failed"),
            @ApiResponse(code=404, message="Not found")
    })
    public Response create(@ApiParam(required = true) final User user) {

        if(user == null)  {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity("User content not found")
                    .build();
        }
        try{
            RandomUtil r = new RandomUtil();
            user.setAvatar(r.getRandomAvatar());
            User entity = UserCRUDRepository.create(user);
            return Response.ok(entity).build();
        } catch (Exception e) {
            return Response.status(400)
                .entity("Request failed" + e.getMessage())
                .build();
    }
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update a user")
    @ApiResponses({
            @ApiResponse(code=200, message="Success"),
            @ApiResponse(code=400, message="Request failed"),
            @ApiResponse(code=404, message="Not found"),
            @ApiResponse(code=500, message="Server error")
    })
    public Response update(@ApiParam(required = true) @PathParam("id") final Integer id, @ApiParam(required = true) final User user) {
        if(id == null) {
            return Response.serverError().entity("ID cannot be blank").build();
        }

        if(user == null)  {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity("User content not found")
                    .build();
        }

        User entity = UserCRUDRepository.get(User.class ,id);

        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }

        if(user.getId() != null){ entity.setId(user.getId());}
        if(user.getName() != null){ entity.setName(user.getName());}
        if(user.getAge() != null){ entity.setAge(user.getAge());}
        if(user.getAvatar() != null){ entity.setAvatar(user.getAvatar());}
        if(user.getMovies() != null){ entity.setMovies(user.getMovies());}
        try{
            UserCRUDRepository.update(entity);
            return Response.ok(entity).build();
        } catch (Exception e) {
            return Response.status(400)
                .entity("Request failed" + e.getMessage())
                .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update a user")
    @ApiResponses({
            @ApiResponse(code=200, message="Success"),
            @ApiResponse(code=400, message="Request failed"),
            @ApiResponse(code=404, message="Not found"),
            @ApiResponse(code=500, message="Server error")
    })
    public Response delete(@ApiParam(required = true) @PathParam("id") final Integer id) {
        if(id == null) {
            return Response.serverError().entity("ID cannot be blank").build();
        }
        User entity = UserCRUDRepository.get(User.class ,id);
        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }
        try{
            UserCRUDRepository.delete(User.class, entity);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(400)
                .entity("Request failed" + e.getMessage())
                .build();
        }
    }
}
