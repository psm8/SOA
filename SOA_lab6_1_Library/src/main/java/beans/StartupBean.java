package beans;

import domain.*;
import repository.CRUDRepository;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.stream.Stream;

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
        Stream.of("Kentucky Brunch Brand Stout", "Marshmallow Handjee",
                "Barrel-Aged Abraxas", "Heady Topper",
                "Budweiser", "Coors Light", "PBR").forEach(name ->
                authorRepository.create(new Author(name))
        );
    }

}
