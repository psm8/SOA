import javax.ejb.Remote;
import javax.jms.MessageListener;

@Remote
public interface IConsumerMessageListener extends MessageListener{
}
