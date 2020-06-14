package web.jsf;


import mdb.topic.IJMSService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class PublisherBean implements Serializable {

    private static final Logger logger = Logger.getLogger(PublisherBean.class.getName());

    @EJB(lookup="java:global/ear_exploded/JMSService!mdb.topic.IJMSService")
    private IJMSService JMSService;

    private List<String> subjectsList;
    private String subject;
    private String specificUser;
    private String message;

    PublisherBean() throws NamingException {
        InitialContext initialContext = new InitialContext();
        NamingEnumeration children = initialContext.list("");

        while(children.hasMore()) {
            NameClassPair ncPair = (NameClassPair)children.next();
            System.out.print(ncPair.getName() + " (type ");
            System.out.println(ncPair.getClassName() + ")");
            System.out.println("test");
        }
/*        JMSService = InitialContext.doLookup("java:global/JMS-service-impl-1.0-SNAPSHOT/JMSService!mdb.topic.IJMSService");*/
    }

    public String getSpecificUser() {
        return specificUser;
    }

    public void setSpecificUser(String specificUser) {
        this.specificUser = specificUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getSubjectsList() {
        if (subjectsList == null) {
            try {
                subjectsList = JMSService.getSubjects();
            } catch (Exception e){}
        }
        return subjectsList;
    }

    public void setSubjectsList(List<String> subjectsList) {
        this.subjectsList = subjectsList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void registerSubject() {
        JMSService.addSubject(subject);
        subjectsList = null;
        subject = null;
    }

    public void sendMessage(){
        if(specificUser == null || specificUser.equals("")){
            sendToAll(subject);
        }
    }

    public void sendToAll(String subject){
        JMSService.sendMessage(subject, message);
    }

    public String delete() {

        String message;

        try {
            JMSService.removeSubject(subject);
            message = "Entry deleted";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred", e);
            message = "Error occurred on deleting this entry.";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));

        subjectsList = null;

        return null;
    }
}
