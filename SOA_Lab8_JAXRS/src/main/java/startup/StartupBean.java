package startup;

import model.Movie;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
@Startup
public class StartupBean {
    private final CreateRandomForStartupBean createRandomForStartupBean;
    private List<Movie> movies;

    @Inject
    public StartupBean(CreateRandomForStartupBean createRandomForStartupBean) {
        this.createRandomForStartupBean = createRandomForStartupBean;
        this.movies = null;
    }

    public StartupBean() {
        this.createRandomForStartupBean = null;
        this.movies = null;
    }

    @PostConstruct
    private void startup() {

        Set<Movie> moviesSet = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            moviesSet.add(createRandomForStartupBean.createRandomMovie());
        }

        movies = new ArrayList<>(moviesSet);

        for (int i = 0; i < 50; i++) {
            createRandomForStartupBean.createRandomUser(movies);
        }
    }
}
