package startup;

import model.Movie;
import model.User;
import repository.CRUDRepository;
import util.RandomUtil;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            RandomUtil r = new RandomUtil();
            return movieCRUDRepository.create(new Movie(r.getRandomName("title"), r.getRandomName("uri")));
        } catch (Exception e){}
            return null;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void createRandomUser(List<Movie> movies) {
        try {
            RandomUtil r = new RandomUtil();
            userCRUDRepository.create(new User(r.getRandomName("name"), r.getRandomAge(),
                    r.getRandomAvatar(), getRandomMovies(movies)));
        } catch (Exception e){}
    }

    private Set<Movie> getRandomMovies(List<Movie> movies){
        List<Movie> moviesLocal = new ArrayList<>(movies);
        Set<Movie> randomMovies = new HashSet<>();
        for (int i = 0; i < (int) (10 * Math.random()); i++) {
            int r = (int) (moviesLocal.size() * Math.random());
            randomMovies.add(movieCRUDRepository.get(Movie.class, moviesLocal.get(r).getId()));
            moviesLocal.remove(r);
        }

        return randomMovies;
    }

}
