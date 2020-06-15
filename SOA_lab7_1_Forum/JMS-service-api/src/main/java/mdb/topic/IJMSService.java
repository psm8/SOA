package mdb.topic;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Remote
public interface IJMSService {
    void subscribe(String subject, String user);
    void unsubscribe(String subject, String user) throws Exception;
    List<String> getMessages(String subject, String user);
    Map<String, List<String>> getSubjectsSubscribers();
}
