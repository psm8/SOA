package mdb.topic;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

@Remote
public interface IJMSService {
    void sendMessage(String subject, String txt);
    void subscribe(String subject, String user) throws Exception;
    void unsubscribe(String subject, String user) throws Exception;
    void addSubject(String subject);
    void removeSubject(String subject);
    List<String> getSubjects();
    Map<String, List<String>> getSubjectsSubscribers();
}
