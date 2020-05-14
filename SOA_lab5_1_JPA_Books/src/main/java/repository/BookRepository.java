package repository;

import model.Book;

import java.util.List;

public interface BookRepository {
    Book create(Book book);

    Book get(Long isbn);

    List<Book> getAll();

    Book update(Book book);

    void delete(Book book);
}
