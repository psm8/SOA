package beans;

import domain.Catalog;
import repository.CRUDRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

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

    public List<Catalog> getCatalogsListFromDB() {
        return catalogRepository.getAll(Catalog.class);
    }


}
