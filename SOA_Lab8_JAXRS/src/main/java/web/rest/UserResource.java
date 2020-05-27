package web.rest;

import model.User;
import repository.CRUDRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@RequestScoped
public class UserResource {
    @Inject
    private CRUDRepository<User> UserCRUDRepository;

    public UserResource(){
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response get(@PathParam("id") final Integer id) {
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
    public Response create(final User User) {

        if(User == null)  {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity("User content not found")
                    .build();
        }
        try{
            User entity = UserCRUDRepository.create(User);
            return Response.ok(entity.getId()).build();
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
    public Response update(@PathParam("id") final Integer id, final User user) {
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
    public Response delete(@PathParam("id") final Integer id) {
        if(id == null) {
            return Response.serverError().entity("ID cannot be blank").build();
        }
        User entity = UserCRUDRepository.get(User.class ,id);
        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }
        try{
            UserCRUDRepository.delete(User.class, id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(400)
                .entity("Request failed" + e.getMessage())
                .build();
        }
    }
}
