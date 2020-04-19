import model.Book;
import model.enumeration.BookType;
import model.enumeration.Currency;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;

import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named
@ViewScoped
public class BooksTableBean implements Serializable {

    private List<Book> books = new ArrayList<>();

    private List<Book> filteredBooks;

    private List<Book> selectedBooks;

    @PostConstruct
    public void init() {
        Random random  = new Random();
        for (int i = 1; i <= 20; i++) {
            books.add(new Book(i, "Title" + random.nextInt(50), "Author" + random.nextInt(50), BookType.randomBookType(), random.nextInt(90) + 10, Currency.randomCurrency(), random.nextInt(900) + 100));
        }
    }

    public BookType[] getBookTypes() {
        return model.enumeration.BookType.values();
    }

    public Currency[] geCurrencies() {
        return model.enumeration.Currency.values();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getFilteredBooks() {
        return filteredBooks;
    }

    public void setFilteredBooks(List<Book> filteredBooks) {
        this.filteredBooks = filteredBooks;
    }

    public List<Book> getSelectedBooks() {
        return selectedBooks;
    }

    public void setSelectedBooks(List<Book> selectedBooks) {
        this.selectedBooks = selectedBooks;
    }

/*    public void getMaxPrice(){
        double maxPrice = Collections.max(this.books, Comparator.comparing(b -> b));
        return

    }*/
}

