package beans;

import model.Book;
import repository.BookRepository;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class StartupBean {
    private final BookRepository bookRepository;

    @Inject
    public StartupBean(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public StartupBean() {
        this.bookRepository = null;
    }

    @PostConstruct
    private void startup() {

        for (int i = 0; i < 50; i++) {
            bookRepository.create(
                            new Book(getRandomIsbn(), getRandomName("title"),
                                    getRandomName("name"), getRandomName("surname"),
                                    getRandomYear(), getRandomPrice()));
        }
    }

    private String getRandomName(String name){
        return name + (int) (5 * Math.random());
    }

    private Long getRandomIsbn(){
        return 1000000000000L + (long) (899999999999L * Math.random());
    }

    private Integer getRandomYear(){
        return (int) (1800 + (220 * Math.random()));
    }

    private Double getRandomPrice(){
        return ((double) Math.round(100* (100 * Math.random())))/100.0;
    }

}
