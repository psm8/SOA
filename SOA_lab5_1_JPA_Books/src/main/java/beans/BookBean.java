package beans;

import model.Book;
import repository.BookRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Map;

@Named
@RequestScoped
@Transactional
public class BookBean {
    @Inject
    private BookRepository bookRepository;
    private Long isbn;
    private String title;
    private String name;
    private String surname;
    private Integer year;
    private Double price;

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String createCatalogEntry() {
        try {
            Book book = new Book();

            book.setIsbn(this.isbn);
            book.setTitle(this.title);
            book.setName(this.name);
            book.setSurname(this.surname);
            book.setYear(this.year);
            book.setPrice(this.price);

            bookRepository.create(book);
        } catch (Exception e){ return "addBook.xhtml?faces-redirect=true";}

        return "index.xhtml?faces-redirect=true";
    }

    public Book getBook() {
        return bookRepository.get(isbn);
    }

    public String updateBookRedirect(Book book) {
        BookBean editRecord = new BookBean();

        /* Setting The Particular Student Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        editRecord.setIsbn(book.getIsbn());
        editRecord.setTitle(book.getTitle());
        editRecord.setName(book.getName());
        editRecord.setSurname(book.getSurname());
        editRecord.setYear(book.getYear());
        editRecord.setPrice(book.getPrice());
        sessionMapObj.put("editRecordObj", editRecord);

        return "/updateBook.xhtml?faces-redirect=true";
    }

    public String updateBook(BookBean bookBean){
        try {
            Book book = new Book();

            book.setIsbn(bookBean.getIsbn());
            book.setTitle(bookBean.getTitle());
            book.setName(bookBean.getName());
            book.setSurname(bookBean.getSurname());
            book.setYear(bookBean.getYear());
            book.setPrice(bookBean.getPrice());

            bookRepository.update(book);
        } catch (Exception e){ return "updateBook.xhtml?faces-redirect=true";}

        return "index.xhtml?faces-redirect=true";
    }

    public String deleteBook(Book book) {
        bookRepository.delete(book);
        return "index.xhtml?faces-redirect=true";
    }
}
