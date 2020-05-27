package web.rest.client;

import domain.MovieEntity;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.context.SessionScoped;
import javax.ws.rs.client.Entity;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@SessionScoped
public class MovieClient implements Serializable {
    final static String FULL_PATH = "http://127.0.0.1:8080/SOA_Lab8_JAXRS_war_exploded/api/movie";

    public List<MovieEntity> getAllMovies() {

        final ResteasyClient client = new ResteasyClientBuilder().build();
        final ResteasyWebTarget target = client
                .target(FULL_PATH);
        Response response = target.request().accept("application/json").get();
        List<MovieEntity> movies = Arrays.asList(response.readEntity(MovieEntity[].class));
        response.close();

        return movies;
    }

    public MovieEntity getMovie(int id) {

        final ResteasyClient client = new ResteasyClientBuilder().build();
        final ResteasyWebTarget target = client
                .target(FULL_PATH + "/" + id);
        Response response = target.request().accept("application/json").get();
        MovieEntity movie = response.readEntity(MovieEntity.class);
        response.close();

        return movie;
    }

    public MovieEntity saveMovie(MovieEntity movie) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH);
        Response response = target.request().accept("application/json")
                .post(Entity.entity(movie, "application/json"));
        MovieEntity result = response.readEntity(MovieEntity.class);
        response.close();

        return result;
    }

    public MovieEntity updateMovie(MovieEntity movie) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH + "/" + movie.getId());
        Response response = target.request().accept("application/json")
                .method("PATCH", Entity.entity(movie, "application/json"));
        MovieEntity result = response.readEntity(MovieEntity.class);
        response.close();

        return result;
    }

    public void deleteMovie(MovieEntity movie) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH + "/" + movie.getId());
        Response response = target.request().accept("application/json")
                .delete();
        response.close();
    }
}
