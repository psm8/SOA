package listeners;


import model.Storage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerMessageListener implements MessageListener {

    static final Logger logger = Logger.getLogger("ServerMessageListener");

    private Storage storage;

    public ServerMessageListener() {
    }

    public ServerMessageListener(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void onMessage(Message msg) {
        try {
            if (msg instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) msg;
                String txt = txtMsg.getText();

                if (msg.getObjectProperty("Operation").equals("Add")) {
                    storage.addSubject(txt);
                    logger.log(Level.INFO,
                            "ServerMessageListener.onMessage: Add: {0}", txt);
                } else if (msg.getObjectProperty("Operation").equals("Remove")) {
                    storage.removeSubject(txt);
                    logger.log(Level.INFO,
                            "ServerMessageListener.onMessage: Remove: {0}", txt);
                }
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "ServerMessageListener.onMessage: Exception: {0}", e.toString());
        }
    }
}
