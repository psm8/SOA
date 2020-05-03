package domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;
    @OneToOne
    @JoinColumn(name = "catalog_id", nullable = false)
    private Catalog catalog;
    @OneToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_rental", nullable = false)
    private Date dateOfRental;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_return")
    private Date dateOfReturn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Book catalof) {
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

}
