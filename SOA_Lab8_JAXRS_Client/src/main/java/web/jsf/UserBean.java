package web.jsf;

import domain.MovieEntity;
import domain.UserEntity;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import web.rest.client.MovieClient;
import web.rest.client.UserClient;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("userBean")
@ViewScoped
public class UserBean implements Serializable {

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

    public String persist() {

        String message;

        try {

            if (user.getId() != null) {
                user = userClient.updateUser(user);
                message = "Entry updated";
            } else {
                user = userClient.saveUser(user);
                message = "Entry created";
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

/*    public DualListModel<MovieEntity> getUsermovie() {
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
                .findAvailableUsermovie(this.user);
        this.usermovie = new DualListModel<>(availableMoviesFromDB, this.user.getUsermovie());

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

    }*/

/*    public void onUsermovieSubmit() {
        // Now we save the changes of the PickList to the database.
        try {

            List<MovieEntity> availableMoviesFromDB = userClient.findAvailableUsermovie(this.user);
			List<MovieEntity> usermovieToRemove = new ArrayList<>();

            for (MovieEntity movie : this.user.getUsermovie()) {
                if (removedUsermovieIDs.contains(movie.getId().toString())) {
                    usermovieToRemove.add(movie);
                }
            }
            
            for (MovieEntity movie : usermovieToRemove) {
                this.user.getUsermovie().remove(movie);;
            }

            for (MovieEntity movie : availableMoviesFromDB) {
                if (transferedUsermovieIDs.contains(movie.getId().toString())) {
                    this.user.getUsermovie().add(movie);
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
    }*/

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
            try {
                userList = userClient.getAllUsers();
            } catch (Exception e){}
        }
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }

}
