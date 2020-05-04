package beans;

import domain.Catalog;
import domain.Reader;
import domain.Rental;
import repository.CRUDRepository;

import javax.inject.Inject;
import java.util.Date;

public class RentalBean {
    @Inject
    private CRUDRepository<Rental> rentalRepository;
    private Integer id;
    private Catalog catalog;
    private Reader reader;
    private Date dateOfRental;
    private Date dateOfReturn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Date getDateOfRental() {
        return dateOfRental;
    }

    public void setDateOfRental(Date dateOfRental) {
        this.dateOfRental = dateOfRental;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public String rentBookRedirect(){
        return "";
    }

    public String rentBook(){
        return "";
    }

    public String returnBookRedirect(){
        return "";
    }

    public String returnBook(){
        return "";
    }
}
