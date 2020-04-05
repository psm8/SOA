import javax.ejb.Remote;
import javax.ejb.Stateless;

@Remote(ISeatAvailability.class)
@Stateless
public class SeatAvailability implements ISeatAvailability{
    public SeatAvailability() {
    }
}
