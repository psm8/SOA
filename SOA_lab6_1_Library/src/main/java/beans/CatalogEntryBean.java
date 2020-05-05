package beans;

import domain.Author;
import domain.Book;
import domain.Catalog;
import domain.Category;
import repository.CRUDRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

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
    private Integer id;
    private Long isbn;
    private String title;
    private String author;
    private String category;
    private Boolean isRented;

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

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Boolean getIsRented() { return isRented; }

    public void setIsRented(Boolean rented) { isRented = rented; }

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

            catalogRepository.create(catalog);
        } catch (Exception e){ return "addCatalogEntry.xhtml?faces-redirect=true";}

        return "catalog.xhtml?faces-redirect=true";
    }

    public Catalog getCatalogEntry() {
        return catalogRepository.get(Category.class, isbn);
    }

    public String updateCatalogEntryRedirect(Catalog catalogEntry) {
        CatalogEntryBean editRecord = new CatalogEntryBean();

        /* Setting The Particular Student Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        editRecord.setId(catalogEntry.getId());
        editRecord.setIsbn(catalogEntry.getBook().getIsbn());
        editRecord.setTitle(catalogEntry.getBook().getTitle());
        editRecord.setAuthor(catalogEntry.getBook().getAuthor().getName());
        editRecord.setCategory(catalogEntry.getBook().getCategory().getName());
        editRecord.setIsRented(catalogEntry.getIsRented());
        sessionMapObj.put("editRecordObj", editRecord);

        return "/updateCatalogEntry.xhtml?faces-redirect=true";
    }

    public String updateCatalogEntry(CatalogEntryBean catalogEntry){
        try {
            Catalog catalog = new Catalog();
            Book book = new Book();
            Author author = new Author();
            Category category = new Category();

            category.setName(catalogEntry.getCategory());

            author.setName(catalogEntry.getAuthor());

            book.setIsbn(catalogEntry.getIsbn());
            book.setTitle(catalogEntry.getTitle());
            book.setAuthor(author);
            book.setCategory(category);

            catalog.setBook(book);
            catalog.setIsRented(false);

            catalogRepository.update(catalog);
        } catch (Exception e){ return "updateCatalogEntry.xhtml?faces-redirect=true";}

        return "catalog.xhtml?faces-redirect=true";
    }

    public String deleteCatalogEntry(Catalog obj) {
        catalogRepository.delete(Category.class, obj);
        return "catalog.xhtml?faces-redirect=true";
    }




}
