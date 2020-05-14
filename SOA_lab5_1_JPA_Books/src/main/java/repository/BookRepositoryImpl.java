package repository;

import model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class BookRepositoryImpl implements BookRepository{
    @PersistenceContext(unitName = "SOA_lab6_1_Library")
    EntityManager em;

    public BookRepositoryImpl() {
    }

    @Override
    public Book create(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book get(Long isbn) {
        return em.find(Book.class, isbn);
    }

    @Override
    public List<Book> getAll() {
        CriteriaQuery<Book> cq = em.getCriteriaBuilder().createQuery(Book.class);
        cq.select(cq.from(Book.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Book update(Book obj) {
        em.merge(obj);
        return obj;
    }

    @Override
    public void delete(Book book) {
        this.em.remove(em.contains(book) ? book : em.merge(book));
    }
}
