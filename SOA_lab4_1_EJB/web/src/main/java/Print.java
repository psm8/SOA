import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;


@Named
@SessionScoped
public class Print implements Serializable {
    @EJB
    ISeatManagement seat;

    public String print(){
        return String.valueOf(seat.getSeatPrice());
    }

}
