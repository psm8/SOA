package web.jsf;

import domain.MovieEntity;
import web.rest.client.MovieClient;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("movieBean")
@ViewScoped
public class MovieBean implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(MovieBean.class.getName());

    private List<MovieEntity> movieList;

    private MovieEntity movie;

    @Inject
    private MovieClient movieClient;

    public String saveOrUpdate() {

        String pageName = checkPageName();

        String message;

        try {

            if (pageName.equals("movieCreate.xhtml")) {
                movie = movieClient.saveMovie(movie);
                message = "Entry saved";
            } else {
                movie = movieClient.updateMovie(movie);
                message = "Entry updated";
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "Your changes could not be saved because an error occurred.";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }

        movieList = null;

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return null;
    }

    public String delete() {

        String message;

        try {
            movieClient.deleteMovie(movie);
            message = "Entry deleted";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "Error occurred on deleting this entry.";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
		
		movieList = null;

        return null;
    }

    public MovieEntity getMovie() {
        // Need to check for null, because some strange behaviour of f:viewParam
        // Sometimes it is setting to null
        if (this.movie == null) {
            this.movie = new MovieEntity();
        }
        return this.movie;
    }

    public void setMovie(MovieEntity movie) {
        if (movie != null) {
            this.movie = movie;
        }
    }

    public List<MovieEntity> getMovieList() {
        if (movieList == null) {

            movieList = movieClient.getAllMovies();
        }
        return movieList;
    }

    public void setMovieList(List<MovieEntity> movieList) {
        this.movieList = movieList;
    }

    public String checkPageName(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public MovieEntity getAsObject(FacesContext context, UIComponent component,
                              String value) {

        if (value == null || value.isEmpty() || component == null) {
            return null;
        }
        if (movieList == null) {
            return movieClient.getMovie(Integer.valueOf(value));
        } else {
            return movieList.get(Integer.valueOf(value));
        }
    }


    @Override
    public String getAsString(FacesContext context, UIComponent component,
                              Object value) {

        if (value == null || !(value instanceof MovieEntity)) {
            logger.log(Level.WARNING, "Can not convert value: {0}", value);
            return "";
        }

        Integer id = ((MovieEntity) value).getId();
        return id != null ? id.toString() : null;
    }

}
