package beans;

import domain.Author;
import domain.Book;
import domain.Catalog;
import domain.Category;
import repository.CRUDRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class CatalogEntryBean {
    @Inject
    private CRUDRepository<Catalog> catalogRepository;
    @Inject
    private CRUDRepository<Book> bookRepository;
    @Inject
    private CRUDRepository<Author> authorRepository;
    @Inject
    private CRUDRepository<Category> categoryRepository;
    private Long isbn;
    private String title;
    private String author;
    private String category;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String createCatalogEntry() {
        try {
            Catalog catalog = new Catalog();
            Book book = new Book();
            Author author = new Author();
            Category category = new Category();

            category.setName(this.category);

            author.setName(this.author);

            book.setIsbn(this.isbn);
            book.setTitle(this.title);
            book.setAuthor(author);
            book.setCategory(category);

            catalog.setBook(book);
            catalog.setIsRented(false);

            authorRepository.create(author);
            bookRepository.create(book);
            catalogRepository.create(catalog);
            categoryRepository.create(category);
        } catch (Exception e){ return e.getMessage();}

        return "success";
    }

/*    public String getCatalogEntry(int studentId) {
        return CRUDRepository.editStudentRecordInDB(studentId);
    }

    public String updateCatalogEntry(StudentBean updateStudentObj) {
        return CRUDRepository.updateStudentDetailsInDB(updateStudentObj);
    }

    public String deleteCatalogEntry(int studentId) {
        return CRUDRepository.deleteStudentRecordInDB(studentId);
    }*/
}
