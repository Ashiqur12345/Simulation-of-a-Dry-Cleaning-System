import java.util.LinkedList;
import java.util.Queue;

public class Jacket {

    Distribution d = new Distribution();
    Queue queue = new LinkedList();
    double arrivalTime;
    double departureTime;
    double serviceTime;
    boolean damage = false;
    boolean deliver = false;

    public Jacket() {
        //this.arrivalTime = d.exponential();
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(double departureTime) {
        this.departureTime = departureTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setDamage(boolean damage) {
        this.damage = damage;
    }

    public void setDeliver(boolean deliver) {
        this.deliver = deliver;
    }

    public String toString(){
        String s = "";
        s += "arvTime="+arrivalTime+" servTime="+serviceTime+" depTime="+departureTime;
        return s;
    }
}
