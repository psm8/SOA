import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

@Remote(IPayments.class)
@Stateless
public class Payments implements IPayments {
    public Payments() {
    }
}
