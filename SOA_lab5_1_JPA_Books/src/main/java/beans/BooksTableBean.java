package beans;

import model.Book;
import repository.BookRepository;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class BooksTableBean implements Serializable {

    @Inject
    private BookRepository bookRepository;

    private List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @PostConstruct
    public void init() {
        books = getCatalogsListFromDB();
    }

    private List<Book> getCatalogsListFromDB() {
        return bookRepository.getAll();
    }
}

