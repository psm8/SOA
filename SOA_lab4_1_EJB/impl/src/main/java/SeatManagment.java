import javax.ejb.Remote;
import javax.ejb.Singleton;
import java.util.List;

@Remote(ISeatManagement.class)
@Singleton
public class SeatManagment implements ISeatManagement{
    @Override
    public List getSeatList() {
        return null;
    }

    @Override
    public int getSeatPrice() {
        return 0;
    }

    @Override
    public void buyTicket() {

    }
}

