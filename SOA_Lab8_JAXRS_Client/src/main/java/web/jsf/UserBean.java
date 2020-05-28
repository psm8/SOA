package web.jsf;

import domain.MovieEntity;
import domain.UserEntity;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import web.rest.client.MovieClient;
import web.rest.client.UserClient;

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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("userBean")
@ViewScoped
public class UserBean implements Converter, Serializable{

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UserBean.class.getName());

    private List<UserEntity> userList;

    private UserEntity user;

    @Inject
    private UserClient userClient;

    @Inject
    private MovieClient movieClient;

    private DualListModel<MovieEntity> usermovie;
    private List<String> transferedUsermovieIDs;
    private List<String> removedUsermovieIDs;

    public String saveOrUpdate() {

        String pageName = checkPageName();

        String message;

        try {

            if (pageName.equals("userCreate.xhtml")) {
                user = userClient.saveUser(user);
                message = "Entry saved";
            } else {
                user = userClient.updateUser(user);
                message = "Entry updated";
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "Your changes could not be saved because an error occurred.";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }

        userList = null;

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return null;
    }

    public String delete() {

        String message;

        try {
            userClient.deleteUser(user);
            message = "Entry deleted";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "Error occurred on deleting this entry.";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));

        userList = null;

        return null;
    }

    public DualListModel<MovieEntity> getUsermovie() {
        return usermovie;
    }

    public void setUsermovie(DualListModel<MovieEntity> movies) {
        int oldSize = this.usermovie.getSource().size() + this.usermovie.getTarget().size();
        int newSize = movies.getSource().size() + movies.getTarget().size();
        if (oldSize > 0 && newSize == 0) {
            // Workaroud: Do not let overwrite with empty List.
            return;
        }
        this.usermovie = movies;
    }

    public List<MovieEntity> getFullUsermovieList() {
        List<MovieEntity> allList = new ArrayList<>();
        allList.addAll(usermovie.getSource());
        allList.addAll(usermovie.getTarget());
        return allList;
    }

    public void onUsermovieDialog(UserEntity user) {
        // Prepare the usermovie PickList
        this.user = user;
        List<MovieEntity> availableMoviesFromDB = userClient
                .getUser(this.user.getId()).getMovies();
        this.usermovie = new DualListModel<>(availableMoviesFromDB, this.user.getMovies());

        transferedUsermovieIDs = new ArrayList<>();
        removedUsermovieIDs = new ArrayList<>();
    }

    public void onUsermoviePickListTransfer(TransferEvent event) {
        // If a usermovie is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((MovieEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedUsermovieIDs.add(id);
                removedUsermovieIDs.remove(id);
            } else if (event.isRemove()) {
                removedUsermovieIDs.add(id);
                transferedUsermovieIDs.remove(id);
            }
        }

    }

    public void onUsermovieSubmit() {
        // Now we save the changes of the PickList to the database.
        try {

            List<MovieEntity> availableMoviesFromDB = userClient.getUser(this.user.getId()).getMovies();
			List<MovieEntity> usermovieToRemove = new ArrayList<>();

            for (MovieEntity movie : this.user.getMovies()) {
                if (removedUsermovieIDs.contains(movie.getId().toString())) {
                    usermovieToRemove.add(movie);
                }
            }
            
            for (MovieEntity movie : usermovieToRemove) {
                this.user.getMovies().remove(movie);;
            }

            for (MovieEntity movie : availableMoviesFromDB) {
                if (transferedUsermovieIDs.contains(movie.getId().toString())) {
                    this.user.getMovies().add(movie);
                }
            }

            this.user = userClient.updateUser(this.user);

            FacesMessage facesMessage = new FacesMessage("Changes saved.");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        } catch (PersistenceException e) {
            FacesMessage facesMessage = new FacesMessage(
                    "Your changes could not be saved because an error occurred.");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
    }

    public UserEntity getUser() {
        // Need to check for null, because some strange behaviour of f:viewParam
        // Sometimes it is setting to null
        if (this.user == null) {
            this.user = new UserEntity();
        }
        return this.user;
    }

    public void setUser(UserEntity user) {
        if (user != null) {
            this.user = user;
        }
    }

    public List<UserEntity> getUserList() {
        if (userList == null) {
            userList = userClient.getAllUsers();
        }
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }

    public String checkPageName(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public UserEntity getAsObject(FacesContext context, UIComponent component,
                                  String value) {

        if (value == null || value.isEmpty() || component == null) {
            return null;
        }
        if (userList == null) {
            return userClient.getUser(Integer.valueOf(value));
        } else {
            return userList.get(Integer.valueOf(value));
        }
    }


    @Override
    public String getAsString(FacesContext context, UIComponent component,
                              Object value) {

        if (value == null || !(value instanceof UserEntity)) {
            logger.log(Level.WARNING, "Can not convert value: {0}", value);
            return "";
        }

        Integer id = ((UserEntity) value).getId();
        return id != null ? id.toString() : null;
    }

}
