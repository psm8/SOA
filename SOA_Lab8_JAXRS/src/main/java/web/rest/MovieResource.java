package web.rest;

import model.Movie;
import model.User;
import repository.CRUDRepository;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/movie")
@RequestScoped
public class MovieResource {

    @Inject
    private CRUDRepository<Movie> movieCRUDRepository;

    @Inject
    private CRUDRepository<User> userCRUDRepository;

    public MovieResource(){
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Movie> entities = movieCRUDRepository.getAll(Movie.class);
        if(entities == null || entities.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        }

        return Response.ok(entities).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("title") final String title) {
        if(title == null) {
            return getAll();
        }
        List<Movie> entities = movieCRUDRepository.getByField(Movie.class , "title", title);
        if(entities == null || entities.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for title: " + title).build();
        }

        return Response.ok(entities).build();
    }

    @GET
    @Produces("text/uri-list")
    public Response getAllAsUriList(){
        List<Movie> entities = movieCRUDRepository.getAll(Movie.class);
        if(entities == null || entities.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        }

        return Response.ok(entities.stream().map(Movie::getUrl).collect(Collectors.toList())).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAllAsTextPlain(){
        List<Movie> entities = movieCRUDRepository.getAll(Movie.class);
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
        Movie entity = movieCRUDRepository.get(Movie.class ,id);
        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }

        return Response.ok(entity).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(final Movie movie) {
        if(movie == null)  {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity("Movie content not found")
                    .build();
        }
        try {
            Movie entity = movieCRUDRepository.create(movie);
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

        if(movie.getTitle() != null){ entity.setTitle(movie.getTitle());}
        if(movie.getUrl() != null){ entity.setUrl(movie.getUrl());}

        try{
            movieCRUDRepository.update(entity);
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
        Movie entity = movieCRUDRepository.get(Movie.class ,id);
        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }
        try{
            List<User> users = userCRUDRepository.getAll(User.class);
            for (User user : users){
                Set<Movie> userMovies = user.getMovies();
                userMovies.remove(entity);
                userCRUDRepository.update(user);
            }
            movieCRUDRepository.delete(Movie.class, entity);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(400)
                    .entity("Request failed" + e.getMessage())
                    .build();
        }
    }
}
