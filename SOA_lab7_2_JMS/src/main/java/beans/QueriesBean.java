package beans;

import domain.*;
import repository.CRUDRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class QueriesBean implements Serializable {
    @Inject
    private CRUDRepository<Rental> rentalRepository;

    private List<Reader> readersList;
    private List<Author> authorsList;

    public List<Reader> getReadersList() {
        return readersList;
    }

    public void setReadersList(List<Reader> readersList) {
        this.readersList = readersList;
    }

    public List<Author> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(List<Author> authorsList) {
        this.authorsList = authorsList;
    }

    public String getAllReadersByAuthor(String name){
        List<Rental> rentals = rentalRepository.joinAndGetWithCriteriaQuery(Rental.class,
                Arrays.asList("reader","catalog","book","author"),
                Arrays.asList("catalog","book","author"), "name", name);
        Set<Reader> readers = new LinkedHashSet<>();
        for(Rental rental: rentals){
            readers.add(rental.getReader());
        }
        this.setReadersList(new ArrayList<>(readers));
        return "queryResults.xhtml?faces-redirect=true";
    }

    public String getAllReadersByBook(String name){
        List<Rental> rentals = rentalRepository.joinAndGetWithCriteriaQuery(Rental.class,
                Arrays.asList("reader","catalog","book","author"),
                Arrays.asList("catalog","book"), "title", name);
        Set<Reader> readers = new LinkedHashSet<>();
        for(Rental rental: rentals){
            readers.add(rental.getReader());
        }
        this.readersList = new ArrayList<>(readers);
        return "queryResults.xhtml?faces-redirect=true";
    }

    public String getAllAuthorsByReader(String name){
        List<Rental> rentals = rentalRepository.joinAndGetWithCriteriaQuery(Rental.class,
                Arrays.asList("reader","catalog","book","author"),
                Arrays.asList("reader"), "surname", name);
        Set<Author> authors = new LinkedHashSet<>();
        for(Rental rental: rentals){
            authors.add(rental.getCatalog().getBook().getAuthor());
        }

        this.authorsList = new ArrayList<>(authors);
        return "queryResultsAuthors.xhtml?faces-redirect=true";
    }

    public String getMostReadAuthor(){
        return "";
    }

}
