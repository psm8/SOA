package domain;

import javax.persistence.*;

@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @OneToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;
    @Column(name = "date_of_rental", nullable = false)
    private int dateOfRental;
    @Column(name = "date_of_return", nullable = false, length = 13)
    private int dateOfReturn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public int getDateOfRental() {
        return dateOfRental;
    }

    public void setDateOfRental(int dateOfRental) {
        this.dateOfRental = dateOfRental;
    }

    public int getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(int dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

}
