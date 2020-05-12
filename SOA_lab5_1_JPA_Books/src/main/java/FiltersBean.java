import java.io.Serializable;
import java.util.Locale;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class FiltersBean implements Serializable {
    private double costMin = 0;
    private double costMax = 100;
    private int pageNoMin = 0;
    private int pageNoMax = 1000;

    public int getPageNoMin() {
        return pageNoMin;
    }

    public void setPageNoMin(int pageNoMin) {
        this.pageNoMin = pageNoMin;
    }

    public int getPageNoMax() {
        return pageNoMax;
    }

    public void setPageNoMax(int pageNoMax) {
        this.pageNoMax = pageNoMax;
    }

    public double getCostMin() {
        return costMin;
    }

    public void setCostMin(double costMin) {
        this.costMin = costMin;
    }

    public double getCostMax() {
        return costMax;
    }

    public void setCostMax(double costMax) {
        this.costMax = costMax;
    }

    public boolean filterByPrice(Object value, Object filter, Locale locale) {

        if (value == null) {
            return false;
        }

        double comparableValue = (Double)value;

        return comparableValue > costMin && comparableValue < costMax;
    }

    public boolean filterByPageNo(Object value, Object filter, Locale locale) {

        if (value == null) {
            return false;
        }

        double comparableValue = (int)value;

        return comparableValue > pageNoMin && comparableValue < pageNoMax;
    }

}
