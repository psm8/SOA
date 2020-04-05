import javax.ejb.Remote;
import java.util.List;

@Remote
public interface ISeatManagement {
    public List getSeatList();
    public int getSeatPrice();
    public void buyTicket();
}
