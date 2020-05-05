package beans;

import domain.*;
import repository.CRUDRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@RequestScoped
public class RentalBean {
    @Inject
    private CRUDRepository<Rental> rentalRepository;
    private Integer id;
    private Catalog catalog;
    private String name;
    private String surname;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String rentBookRedirect(Catalog catalogEntry){
        RentalBean editRecord = new RentalBean();

        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        editRecord.setCatalog(catalogEntry);
        sessionMapObj.put("rentCatalogEntryObj", editRecord);

        return "/rentCatalogEntry.xhtml?faces-redirect=true";
    }

    public String rentBook(RentalBean rentalEntry){
        try {
            Rental rental = new Rental();
            Reader reader = new Reader();

            rentalEntry.getCatalog().setIsRented(true);

            reader.setName(rentalEntry.getName());
            reader.setSurname(rentalEntry.getSurname());

            rental.setCatalog(rentalEntry.getCatalog());
            rental.setReader(reader);
            rental.setDateOfRental(new Date());
            rental.setDateOfReturn(null);

            rentalRepository.create(rental);
        } catch (Exception e){ return "rentCatalog.xhtml?faces-redirect=true";}

        return "catalog.xhtml?faces-redirect=true";
    }

    public String returnBookRedirect(Catalog catalogEntry){
        RentalBean editRecord = new RentalBean();

        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        editRecord.setCatalog(catalogEntry);
        List<Rental> rentals =  rentalRepository.getWithCriteriaQuery(Rental.class, "catalog", catalogEntry);
        List<Date> dates = new ArrayList<>();
        for(Rental rental : rentals){
            dates.add(rental.getDateOfRental());
        }
        Date date = Collections.max(dates);
        List<Rental> rentalsReader =  rentalRepository.getWithCriteriaQuery(Rental.class, "dateOfRental", date);

        editRecord.setDateOfRental(date);
        editRecord.setName(rentalsReader.get(0).getReader().getName());
        editRecord.setSurname(rentalsReader.get(0).getReader().getSurname());

        sessionMapObj.put("returnCatalogEntryObj", editRecord);

        return "/returnCatalogEntry.xhtml?faces-redirect=true";
    }

    public String returnBook(RentalBean rentalEntry){
        try {
            Rental rental = new Rental();
            Reader reader = new Reader();

            rentalEntry.getCatalog().setIsRented(false);

            reader.setName(rentalEntry.getName());
            reader.setSurname(rentalEntry.getSurname());

            rental.setCatalog(rentalEntry.getCatalog());
            rental.setReader(reader);
            rental.setDateOfRental(rentalEntry.getDateOfRental());
            rental.setDateOfReturn(new Date());

            rentalRepository.update(rental);

        } catch (Exception e){ return "returnCatalog.xhtml?faces-redirect=true";}

        return "catalog.xhtml?faces-redirect=true";
    }
}
