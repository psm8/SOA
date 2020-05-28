package web.rest;


import io.swagger.annotations.*;
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
@Api(tags={"Movie Resource"})
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
    @ApiOperation(value = "Fetch all movies", produces = "application/json, text/uri-list, text/plain")
    @ApiResponses({
            @ApiResponse(code=200, message="Success"),
            @ApiResponse(code=404, message="Not found")
    })
    public Response getAll() {
        List<Movie> entities = movieCRUDRepository.getAll(Movie.class);
        if(entities == null || entities.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        }

        return Response.ok(entities, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces("text/uri-list")
    @ApiOperation(value = "Fetch all movies", produces = "application/json, text/uri-list, text/plain")
    @ApiResponses({
            @ApiResponse(code=200, message="Success"),
            @ApiResponse(code=404, message="Not found")

    })
    public Response getAllAsUriList(){
        List<Movie> entities = movieCRUDRepository.getAll(Movie.class);
        if(entities == null || entities.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        }

        return Response.ok(entities.stream().map(Movie::getUrl).collect(Collectors.toList()), "text/uri-list").build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Fetch all movies", produces = "application/json, text/uri-list, text/plain")
    @ApiResponses({
            @ApiResponse(code=200, message="Success"),
            @ApiResponse(code=404, message="Not found")
    })
    public Response getAllAsTextPlain(){
        List<Movie> entities = movieCRUDRepository.getAll(Movie.class);
        if(entities == null || entities.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found").build();
        }

        return Response.ok(entities, MediaType.TEXT_PLAIN).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch all movies", produces = "application/json, text/uri-list, text/plain")
    @ApiResponses({
            @ApiResponse(code=200, message="Success"),
            @ApiResponse(code=404, message="Not found")
    })
    public Response getAll(@ApiParam @QueryParam("title") final String title) {
        if(title == null) {
            return getAll();
        }
        List<Movie> entities = movieCRUDRepository.getByField(Movie.class , "title", title);
        if(entities == null || entities.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for title: " + title).build();
        }

        return Response.ok(entities, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch a movie")
    @ApiResponses({
            @ApiResponse(code=200, message="Success"),
            @ApiResponse(code=404, message="Not found")
    })
    public Response get(@ApiParam(required=true) @PathParam("id") final Integer id) {
        if(id == null) {
            return Response.serverError().entity("ID cannot be blank").build();
        }
        Movie entity = movieCRUDRepository.get(Movie.class ,id);
        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }

        return Response.ok(entity, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create a new movie")
    @ApiResponses({
            @ApiResponse(code=200, message="Created"),
            @ApiResponse(code=400, message="Request Failed"),
    })
    public Response create(@ApiParam(required=true) final Movie movie) {
        if(movie == null)  {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Movie content not found")
                    .build();
        }
        try {
            Movie entity = movieCRUDRepository.create(movie);
            return Response.ok(entity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Request failed" + e.getMessage())
                    .build();
        }
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Updates existing movie")
    @ApiResponses({
            @ApiResponse(code=200, message="Updated"),
            @ApiResponse(code=400, message="Request failed"),
            @ApiResponse(code=404, message="Not found"),
            @ApiResponse(code=500, message="Server error")
    })
    public Response update(@ApiParam(required=true) @PathParam("id") final Integer id,
                           @ApiParam(required=true) final Movie movie) {
        if(id == null) {
            return Response.serverError().entity("ID cannot be blank").build();
        }

        if(movie == null)  {
            return Response.status(Response.Status.BAD_REQUEST)
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
            return Response.ok(entity, MediaType.APPLICATION_JSON).build();
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
    @ApiOperation(value = "Remove existing movie")
    @ApiResponses({
            @ApiResponse(code=200, message="Removed"),
            @ApiResponse(code=400, message="Request failed"),
            @ApiResponse(code=404, message="Not found"),
            @ApiResponse(code=500, message="Server error")
    })
    public Response delete(@ApiParam(required=true) @PathParam("id") final Integer id) {
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
