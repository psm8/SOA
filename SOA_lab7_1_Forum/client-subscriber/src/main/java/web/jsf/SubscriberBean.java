package web.jsf;


import mdb.topic.IJMSService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class SubscriberBean implements Serializable {


/*    @EJB(lookup="java:global/ear-1.0-SNAPSHOT/JMSService!mdb.topic.IJMSService")*/
    private IJMSService JMSService;

    private String user;
    private String subject;
    private Map<String, List<String>> subjectsMap;
    private List<String> messages;

    SubscriberBean() throws NamingException {
        InitialContext initialContext = new InitialContext();
        NamingEnumeration children = initialContext.list("");

        while(children.hasMore()) {
            NameClassPair ncPair = (NameClassPair)children.next();
            System.out.print(ncPair.getName() + " (type ");
            System.out.println(ncPair.getClassName() + ")");
        }
        /*JMSService = InitialContext.doLookup("java:global/ear-1.0-SNAPSHOT/lib/JMSService!mdb.topic.IJMSService");*/
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, List<String>> getSubjectsMap() {
        try {
            subjectsMap = JMSService.getSubjectsSubscribers();
        } catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("couldn't get subject map"));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
        return subjectsMap;
    }

    public void setSubjectsMap(Map<String, List<String>> subjectsMap) {
        this.subjectsMap = subjectsMap;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void loadMessages(){
        this.messages = null /*JMSService.getSubjects()*/;
    }

    public void subscribe(){
        String message = "subscription successful";
        try {
            JMSService.subscribe(subject, user);
        } catch(Exception e){
            message = "subscription failed";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public void unsubscribe(){
        String message = "unsubscription successful";
        try {
            JMSService.unsubscribe(subject, user);
        } catch(Exception e){
            message = "unsubscription failed";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}
