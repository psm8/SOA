import javax.ejb.Remote;
import javax.ejb.Stateless;

@Remote(ISeatManagement.class)
@Stateless
public class SeatAvailability {
}
