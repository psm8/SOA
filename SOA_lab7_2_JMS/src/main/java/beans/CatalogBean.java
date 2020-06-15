package beans;

import domain.Catalog;
import domain.Rental;
import repository.CRUDRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@RequestScoped
public class CatalogBean {
    @Inject
    private CRUDRepository<Catalog> catalogRepository;
    private List<Catalog> catalogsList = new ArrayList<>();

    public List<Catalog> getCatalogsList() {
        return catalogsList;
    }

    public void setCatalogsList(List<Catalog> catalogsList) {
        this.catalogsList = catalogsList;
    }

    @PostConstruct
    public void init() {
        catalogsList = getCatalogsListFromDB();
    }

    private List<Catalog> getCatalogsListFromDB() {
        return catalogRepository.getAll(Catalog.class);
    }
}
