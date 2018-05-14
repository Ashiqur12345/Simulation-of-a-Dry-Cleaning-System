public class Suits {
    Jacket jacket;
    Pants pants;

    double arrivalTime;
    double departureTime;
    double serviceTime;
    boolean deliver = false;


    Distribution d = new Distribution();

    public Suits() {
        this.jacket = new Jacket();
        this.pants = new Pants();
        this.arrivalTime = d.geometric(0.1);
    }

    public void setJacket(Jacket jacket) {
        this.jacket = jacket;
    }

    public void setPants(Pants pants) {
        this.pants = pants;
    }

    public void setDepartureTime(double departureTime) {
        this.departureTime = departureTime;
        //System.out.println("departureTime set="+this.departureTime);
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
        //System.out.println("serviceTime set="+this.serviceTime);
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
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
