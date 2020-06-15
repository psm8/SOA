package mdb.topic;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Remote
public interface IJMSService {
    void subscribe(String subject, String user) throws Exception;
    void unsubscribe(String subject, String user) throws Exception;
    List<String> getMessages(String subject, String user) throws Exception;
    Map<String, List<String>> getSubjectsSubscribers();
}
