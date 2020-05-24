package beans;

import model.Movie;
import model.User;
import repository.CRUDRepository;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
@Startup
public class StartupBean {
    private final CRUDRepository<User> userCRUDRepository;
    private final CRUDRepository<Movie> movieCRUDRepository;
    private List<Movie> movies;

    @Inject
    public StartupBean(CRUDRepository<User> userCRUDRepository, CRUDRepository<Movie> movieCRUDRepository) {
        this.userCRUDRepository = userCRUDRepository;
        this.movieCRUDRepository = movieCRUDRepository;
        this.movies = null;
    }

    public StartupBean() {
        this.userCRUDRepository = null;
        this.movieCRUDRepository = null;
        this.movies = null;
    }

    @PostConstruct
    private void startup() throws IOException{

        Set<Movie> moviesSet = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            moviesSet.add(movieCRUDRepository.create(new Movie(getRandomName("title"), getRandomName("uri"))));
        }

        movies = new ArrayList<>(moviesSet);

        for (int i = 0; i < 50; i++) {
            userCRUDRepository.create(
                            new User(getRandomName("name"), getRandomAge(),
                                    getRandomAvatar(), getRandomMovies()));
        }
    }

    private String getRandomName(String name){
        return name + (int) (5 * Math.random());
    }

    private Integer getRandomAge(){
        return (int) (18 + (100 * Math.random()));
    }

    private byte[] getRandomAvatar() throws IOException{
        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("config/fake-data/avatar"
                        + (int) (1 + 5 * Math.random())+".png");

        return inputStream.readAllBytes();
    }

    private List<Movie> getRandomMovies(){
        List<Movie> randomMovies = new ArrayList<>();
        for (int i = 0; i < (int) (10 * Math.random()); i++) {
            randomMovies.add(movies.get((int) (movies.size() * Math.random())));
        }

        return randomMovies;
    }

}
