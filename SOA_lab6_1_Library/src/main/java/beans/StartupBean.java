package beans;

import domain.*;
import repository.CRUDRepository;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Date;

@Singleton
@Startup
public class StartupBean {
    private final CRUDRepository<Author> authorRepository;
    private final CRUDRepository<Book> bookRepository;
    private final CRUDRepository<Catalog> catalogRepository;
    private final CRUDRepository<Category> categoryRepository;
    private final CRUDRepository<Reader> readerRepository;
    private final CRUDRepository<Rental> rentalRepository;

    @Inject
    public StartupBean(CRUDRepository<Author> authorRepository, CRUDRepository<Book> bookRepository,
                       CRUDRepository<Catalog> catalogRepository, CRUDRepository<Category> categoryRepository,
                       CRUDRepository<Reader> readerRepository, CRUDRepository<Rental> rentalRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.catalogRepository = catalogRepository;
        this.categoryRepository = categoryRepository;
        this.readerRepository = readerRepository;
        this.rentalRepository = rentalRepository;
    }

    public StartupBean() {
        this.authorRepository = null;
        this.bookRepository = null;
        this.catalogRepository = null;
        this.categoryRepository = null;
        this.readerRepository = null;
        this.rentalRepository = null;
    }

    @PostConstruct
    private void startup() {

        for (int i = 0; i < 50; i++) {
            Date[] dates = getRandomDates();
            rentalRepository.create(new Rental(
                    new Catalog(
                            new Book(getRandomIsbn(), getRandomName("title"),
                                    new Author(getRandomName("author")),
                                    new Category(getRandomName("category"))), getIsRented(dates[1])),
                    new Reader(getRandomName("name"), getRandomName("surname")),
                    dates[0], dates[1]));
        }
    }

    private String getRandomName(String name){
        return name + (int) (5 * Math.random());
    }

    private Long getRandomIsbn(){
        return 1000000000000L + (long) (999999999999L * Math.random());
    }

    private Date[] getRandomDates(){
        long msDateOfRental = 70L * 365 * 24 * 60 * 60 * 1000 + (long)(10L * 365 * 24 * 60 * 60 * 1000 * Math.random());

        if (Math.random() > 0.5){
            long msDateOfReturn = msDateOfRental + (long)(1L * 365 * 24 * 60 * 60 * 1000 * Math.random() + 1);
            return new Date[]{new Date(msDateOfRental), new Date(msDateOfReturn)};
        } else {
            return new Date[]{new Date(msDateOfRental), null};
        }
    }

    private boolean getIsRented(Date d) {
        return (d != null);
    }

}
