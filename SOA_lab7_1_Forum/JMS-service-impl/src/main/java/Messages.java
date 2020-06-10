import java.util.List;
import java.util.Map;

public class Messages implements IMessages{
    Map<String, List<String>> userMessages;

    public Messages(Map<String, List<String>> userMessage) {
        this.userMessages = userMessages;
    }

    public Map<String, List<String>> getUserMessage() {
        return userMessages;
    }

    public void setUserMessage(Map<String, List<String>> userMessages) {
        this.userMessages = userMessages;
    }
}
