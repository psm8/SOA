package web.rest;

import model.Movie;
import repository.CRUDRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/movie")
public class MovieResource {

    @Inject
    private CRUDRepository<Movie> movieCRUDRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Movie> entity = movieCRUDRepository.getAll(Movie.class);
        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        }

        return Response.ok(entity).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") final Integer id) {
        if(id == null) {
            return Response.serverError().entity("ID cannot be blank").build();
        }
        Movie entity = movieCRUDRepository.get(Movie.class ,id);
        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }

        return Response.ok(entity).build();
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@PathParam("id") final Integer id, final Movie movie) {
        if(id == null) {
            return Response.serverError().entity("ID cannot be blank").build();
        }

        if(movie == null)  {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity("Movie content not found")
                    .build();
        }

        Movie entity = movieCRUDRepository.create(movie);

        return Response.ok(entity.getId()).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") final Integer id, final Movie movie) {
        if(id == null) {
            return Response.serverError().entity("ID cannot be blank").build();
        }

        if(movie == null)  {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity("Movie content not found")
                    .build();
        }

        Movie entity = movieCRUDRepository.get(Movie.class ,id);

        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }

        if(movie.getId() != null){ entity.setId(movie.getId());}
        if(movie.getTitle() != null){ entity.setTitle(movie.getTitle());}
        if(movie.getUrl() != null){ entity.setUrl(movie.getUrl());}

        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") final Integer id) {
        if(id == null) {
            return Response.serverError().entity("ID cannot be blank").build();
        }
        Movie entity = movieCRUDRepository.get(Movie.class ,id);
        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }

        movieCRUDRepository.delete(Movie.class, id);

        return Response.ok().build();
    }
}