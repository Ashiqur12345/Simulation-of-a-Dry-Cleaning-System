import java.util.LinkedList;
import java.util.Queue;

public class Server {

    Queue<Suits> queue = new LinkedList<>();
    Queue<Suits> served = new LinkedList<>();
    Queue<Jacket> serveJacket = new LinkedList<>();
    Queue<Pants> servePant = new LinkedList<>();

    double avgLengthQueue = 0;
    double maxLengthQueue = 0;

    double avgLengthQueue2 = 0;
    double maxLengthQueue2 = 0;

    Boolean busy = false;

    public void setBusy(Boolean busy){
        this.busy = busy;
    }
}
