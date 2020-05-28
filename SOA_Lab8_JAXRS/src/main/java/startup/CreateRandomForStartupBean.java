package startup;

import model.Movie;
import model.User;
import org.apache.commons.io.IOUtils;
import repository.CRUDRepository;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class CreateRandomForStartupBean {

    private final CRUDRepository<User> userCRUDRepository;
    private final CRUDRepository<Movie> movieCRUDRepository;

    @Inject
    public CreateRandomForStartupBean(CRUDRepository<User> userCRUDRepository, CRUDRepository<Movie> movieCRUDRepository) {
        this.userCRUDRepository = userCRUDRepository;
        this.movieCRUDRepository = movieCRUDRepository;
    }

    public CreateRandomForStartupBean() {
        userCRUDRepository = null;
        movieCRUDRepository = null;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public Movie createRandomMovie(){
        try{
        return movieCRUDRepository.create(new Movie(getRandomName("title"), getRandomName("uri")));
        } catch (Exception e){}
        return null;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void createRandomUser(List<Movie> movies) {
        try {
            userCRUDRepository.create(new User(getRandomName("name"), getRandomAge(),
                    getRandomAvatar(), getRandomMovies(movies)));
        } catch (Exception e){}
    }

    private String getRandomName(String name){
        return name + (int) (10 * Math.random());
    }

    private Integer getRandomAge(){
        return (int) (18 + (100 * Math.random()));
    }

    private byte[] getRandomAvatar() throws IOException{
        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("config/fake-data/avatar"
                        + (int) (1 + 5 * Math.random())+".png");

        return IOUtils.toByteArray(inputStream);
    }

    private List<Movie> getRandomMovies(List<Movie> movies){
        List<Movie> moviesLocal = new ArrayList<>(movies);
        List<Movie> randomMovies = new ArrayList<>();
        for (int i = 0; i < (int) (10 * Math.random()); i++) {
            int r = (int) (moviesLocal.size() * Math.random());
            randomMovies.add(movieCRUDRepository.get(Movie.class, moviesLocal.get(r).getId()));
            moviesLocal.remove(r);
        }

        return randomMovies;
    }

}
