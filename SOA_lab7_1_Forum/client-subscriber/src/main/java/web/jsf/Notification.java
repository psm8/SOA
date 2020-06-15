package web.jsf;


import mdb.topic.IJMSService;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public class Notification implements Runnable{
    IJMSService JMSService;
    String subject;
    String user;

    public Notification(String subject, String user) {
        this.subject = subject;
        this.user = user;
    }

    @Override
    public void run() {
        String msg;
            while(true) {
                try {
                    try {
                        msg = JMSService.getNotification(subject, user);
                    } catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                        return;
                    }
                } catch (Exception e){
                    return;
                }
                print(msg);
            }
    }

    private void print(String msg){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("new notification:" + msg));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}
