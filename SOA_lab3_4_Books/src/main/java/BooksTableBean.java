import model.Book;
import model.enumeration.BookType;
import model.enumeration.Currency;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named
@ViewScoped
public class BooksTableBean implements Serializable {

    private static final Random RANDOM = new Random();
    private List<Book> books = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 10; i++) {
            books.add(new Book(i, "Title" + i, "Author" + i, BookType.randomBookType(), RANDOM.nextInt(90) + 10, Currency.randomCurrency(), RANDOM.nextInt(900) + 100));
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

