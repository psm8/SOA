package web.jsf;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class PublisherBean {

    @EJB(lookup="java:global/client-publisher-1.0-SNAPSHOT/JMSService!IJMSService")
    private /*IJMSService*/ Object JMSService;

    private List<String> topicList;

    public List<String> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<String> topicList) {
        this.topicList = topicList;
    }
}
