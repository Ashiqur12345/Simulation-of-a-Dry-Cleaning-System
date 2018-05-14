import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Service {

    Server server1 = new Server();
    Server server2 = new Server();
    Server server3 = new Server();
    Server server4 = new Server();
    Server server5 = new Server();

    int n = 6;
    public void service(){


        double serviceTime1 = 0.167;
        double st2 = 0.25;
        double st3 = 0.20;
        double st4 = 0.20;
        //double st5 = 0.167;


        Queue<Suits> successfullyServed = new LinkedList<>();

        double server2CurrentTime = 0;
        double server3CurrentTime = 0;
        double server4CurrentTime = 0;


        Queue<Jacket> jq = new LinkedList();
        Queue<Pants> pq = new LinkedList();

        Distribution d = new Distribution();
        Damage damage = new Damage();

        double currentTime = 0;
        double nextEvent;
        double nextArrival = Integer.MAX_VALUE;
        double nextDeparture = Integer.MAX_VALUE;

        Suits suits = new Suits();
        nextArrival = suits.arrivalTime;
        currentTime += nextArrival;
        //1st server ...
        while (n >= 0)
        {

            if (nextArrival<nextDeparture)
                nextEvent = 0;
            else
                nextEvent = 1;

            //for Arrival event ...
            if(nextEvent == 0)
            {
                currentTime = nextArrival;
                if(server1.busy == false){
                    suits.setServiceTime(d.geometric(serviceTime1));
                    nextDeparture = currentTime + suits.serviceTime;
                    suits.setDepartureTime(nextDeparture);
                    server1.setBusy(true);

                    server1.served.add(suits);

                }
                else{
                    server1.queue.add(suits);

                }
                suits = new Suits();
                suits.setArrivalTime(currentTime+suits.arrivalTime);
                nextArrival = suits.arrivalTime;
            }
            //for Departure event ...
            else {
                if(n == 0){
                   currentTime = nextDeparture;
                   suits.setServiceTime(d.geometric(serviceTime1));
                   nextDeparture = currentTime + suits.serviceTime;
                   suits.setDepartureTime(nextDeparture);
                   server1.served.add(suits);
                }
//                else
//                    server1.served.add(suits);

                currentTime = nextDeparture;
                nextDeparture = Integer.MAX_VALUE;

                if(server1.queue.isEmpty()){
                    server1.busy = false;
                }
                else {
//
                    Suits temp = server1.queue.poll();
                    temp.setServiceTime(d.geometric(serviceTime1));
                    temp.setDepartureTime(currentTime + temp.serviceTime);
                    nextDeparture = temp.departureTime;
                    server1.served.add(temp);
                    //server1.queue.add(suits);
                    if(suits.arrivalTime > currentTime)
                        currentTime = suits.arrivalTime;

                    currentTime = nextDeparture;
                    //nextDeparture = Integer.MAX_VALUE;


                }
            }
//            server1.avgLengthQueue += server1.queue.size();
//            if(server1.queue.size() > server1.maxLengthQueue)
//                server1.maxLengthQueue = server1.queue.size();

            //for server 2 & 3 ...
            if(!server1.served.isEmpty()){

//                server2.avgLengthQueue += server1.served.size();
//                if(server1.served.size() > server2.maxLengthQueue)
//                    server2.maxLengthQueue = server1.served.size();
//
//
//                server3.avgLengthQueue += server1.served.size();
//                if(server1.served.size() > server3.maxLengthQueue)
//                    server3.maxLengthQueue = server1.served.size();

                for(Suits s:server1.served){
                    if(!s.deliver) {
                        s.setDeliver(true);

                        Jacket jacket = s.jacket;
                        Pants pants = s.pants;

                        //for server 2 jacket...
                        if(server2CurrentTime < s.departureTime && n != 6)
                            server2CurrentTime = s.departureTime;
                        if(n == 6)
                            server2CurrentTime = server2CurrentTime + s.departureTime;
                        jacket.setArrivalTime(server2CurrentTime);
                        jacket.setServiceTime(d.geometric(st2));
                        server2CurrentTime = server2CurrentTime + jacket.serviceTime;
                        jacket.setDepartureTime(server2CurrentTime);
                        jacket.setDamage(damage.findDamage(0.05));
                        server2.serveJacket.add(jacket);

                        //for server 3 pants...
                        if(server3CurrentTime < s.departureTime && n != 6)
                            server3CurrentTime = s.departureTime;
                        if(n == 6)
                            server3CurrentTime = server3CurrentTime + s.departureTime;
                        pants.setArrivalTime(server3CurrentTime);
                        pants.setServiceTime(d.geometric(st3));
                        server3CurrentTime = server3CurrentTime + pants.serviceTime;
                        pants.setDepartureTime(server3CurrentTime);
                        pants.setDamage(damage.findDamage(0.10));
                        server3.servePant.add(pants);

                    }
                }

            }

            //server 4 ....
            if(!server2.serveJacket.isEmpty() && !server3.servePant.isEmpty()){
                Suits st = new Suits();
                //int count1 = 0,count2 = 0;
                for(Jacket j:server2.serveJacket){
                    if(!j.deliver) {
                        j.setDeliver(true);
                        jq.add(j);
                        //count1++;
                    }

                }
                for(Pants p:server3.servePant){
                    if(!p.deliver) {
                        p.setDeliver(true);
                        pq.add(p);
                    }
                }

                //cheak for both jacket and pant ...

                if( jq.size() > pq.size()) {
                    for(Pants p:pq) {

                        Jacket jj = jq.poll();
                        Pants pp = pq.poll();
                        //which departure max server start from there ...
                        if(jj.departureTime > pp.departureTime) {
                            if(server4CurrentTime < jj.departureTime && n != 6)
                                server4CurrentTime = jj.departureTime;
                            if(n == 6)
                                server4CurrentTime = server4CurrentTime + jj.departureTime;

                            //server4CurrentTime += jj.departureTime;
                            st.setArrivalTime(server4CurrentTime);
                        }
                        else{
                            if(server4CurrentTime < pp.departureTime && n != 6)
                                server4CurrentTime = pp.departureTime;
                            if(n == 6)
                                server4CurrentTime = server4CurrentTime + pp.departureTime;
                            //server4CurrentTime += pp.departureTime;
                            st.setArrivalTime(server4CurrentTime);
                        }

                        st.setJacket(jj);
                        st.setPants(pp);
                        st.setServiceTime(d.geometric(st4));
                        st.setDepartureTime(server4CurrentTime += st.serviceTime);
                        server4CurrentTime += st.departureTime;
                        server4.served.add(st);

                    }
                }
                else {
                    for(Jacket j:jq) {
                        Jacket jj = jq.poll();
                        Pants pp = pq.poll();
                        //which departure max server start from there ...
                        if(jj.departureTime > pp.departureTime) {
                            if(server4CurrentTime < jj.departureTime && n != 6)
                                server4CurrentTime = jj.departureTime;
                            if(n == 6)
                                server4CurrentTime = server4CurrentTime + jj.departureTime;

                            //server4CurrentTime = server4CurrentTime + jj.departureTime;
                            st.setArrivalTime(server4CurrentTime);
                        }
                        else{
                            if(server4CurrentTime < pp.departureTime && n != 6)
                                server4CurrentTime = pp.departureTime;
                            if(n == 6)
                                server4CurrentTime = server4CurrentTime + pp.departureTime;

                            //server4CurrentTime = server4CurrentTime + pp.departureTime;
                            st.setArrivalTime(server4CurrentTime);
                        }

                        st.setJacket(jj);
                        st.setPants(pp);
                        st.setServiceTime(d.geometric(st4));
                        server4CurrentTime = server4CurrentTime + st.serviceTime;
                        st.setDepartureTime(server4CurrentTime);
                        server4.served.add(st);

                    }
                }

            }
            //for served customer or server 5 for damaged ...
            if(!server4.served.isEmpty()){
                for(Suits ss:server4.served){
                    if(!ss.deliver){
                        ss.setDeliver(true);
                        if(!ss.jacket.damage && !ss.pants.damage){
                            successfullyServed.add(ss);
                        }
                        else
                            server5.queue.add(ss);
                    }
                }
            }


            n--;
        }

        System.out.println("~: served at server 1 :~");
        for(Suits s:server1.served){
            System.out.println(s);
        }
        System.out.println("");

        System.out.println("~: jacket served at server 2 :~");
        for(Jacket j:server2.serveJacket){
            System.out.println(j);
        }
        System.out.println("");

        System.out.println("~: pant served at server 3 :~");
        for(Pants p:server3.servePant){
            System.out.println(p);
        }
        System.out.println("");

        System.out.println("~: served at server 4 :~");
        for(Suits s:server4.served){
            System.out.println(s);
        }
        System.out.println("");

        System.out.println("~: successfully served :~");
        for(Suits s:successfullyServed)
            System.out.println(s);
        System.out.println("");

        System.out.println("~: damaged :~");
        for(Suits s:server5.queue)
            System.out.println(s);

        System.out.println("\n");
    }

    public double getServer1Utilization () {
        double count = 0;
        double end = 0;
        for(Suits s1:server1.served){
            count+= s1.serviceTime;
            end = s1.departureTime;
        }
        //System.out.println((double)(count/end));
        return (double)(count/end);
    }

    public double getServer2Utilization() {
        double count = 0;
        double end = 0;
        for(Suits s2:server1.served){
            count+= s2.serviceTime;
            end = s2.departureTime;
        }
        //System.out.println((double)(count/end));
        return (double)(count/end);
    }

    public double getServer3Utilization() {
        double count = 0;
        double end = 0;
        for(Suits s3:server1.served){
            count+= s3.serviceTime;
            end = s3.departureTime;
        }
        //System.out.println((double)(count/end));
        return (double)(count/end);
    }

    public double getServer4Utilization() {
        double count = 0;
        double end = 0;
        for(Suits s4:server4.served){
            count+= s4.serviceTime;
            end = s4.departureTime;
        }
        //System.out.println((double)(count/end));
        return (double)(count/end);
    }


    //Maximum Length ...
    public double getServer1MaximumLength() {

        ArrayList<Suits> l1 = new ArrayList(server1.served);
        double max = 0;
        for(int i = 1; i < server1.served.size(); i++){
            if(l1.get(i).arrivalTime < l1.get(i-1).departureTime){
                if((l1.get(i-1).departureTime - l1.get(i).arrivalTime) > max){
                    max = l1.get(i-1).departureTime - l1.get(i).arrivalTime;
                }

            }
        }
        System.out.println("Server1 Maximum Queue Length = "+max);
        return max;
    }

    public double getServer2MaximumLength() {

        ArrayList<Jacket> l2 = new ArrayList(server2.serveJacket);
        double max = 0;
        for(int i = 1; i < server2.serveJacket.size(); i++){
            if(l2.get(i).arrivalTime < l2.get(i-1).departureTime){
                if((l2.get(i-1).departureTime - l2.get(i).arrivalTime) > max){
                    max = l2.get(i-1).departureTime - l2.get(i).arrivalTime;
                }

            }
        }
        System.out.println("Server2 Maximum Queue Length = "+max);
        return max;
    }

    public double getServer3MaximumLength() {

        ArrayList<Pants> l1 = new ArrayList(server3.servePant);
        double max = 0;
        for(int i = 1; i < server3.servePant.size(); i++){
            if(l1.get(i).arrivalTime < l1.get(i-1).departureTime){
                if((l1.get(i-1).departureTime - l1.get(i).arrivalTime) > max){
                    max = l1.get(i-1).departureTime - l1.get(i).arrivalTime;
                }

            }
        }
        System.out.println("Server3 Maximum Queue Length = "+max);
        return max;
    }

    public double getServer4MaximumLength() {

        ArrayList<Suits> l1 = new ArrayList(server4.served);
        double maxJacket = 0;
        double maxPant = 0;
        for(int i = 1; i < server4.served.size(); i++){
            if(l1.get(i).jacket.arrivalTime < l1.get(i-1).jacket.departureTime){
                if((l1.get(i-1).jacket.departureTime - l1.get(i).jacket.arrivalTime) > maxJacket){
                    maxJacket = l1.get(i-1).jacket.departureTime - l1.get(i).jacket.arrivalTime;
                }

            }
        }
        System.out.println("Server4 Jacket Maximum Queue Length = "+maxJacket);

        for(int i = 1; i < server4.served.size(); i++){
            if(l1.get(i).pants.arrivalTime < l1.get(i-1).pants.departureTime){
                if((l1.get(i-1).pants.departureTime - l1.get(i).pants.arrivalTime) > maxPant){
                    maxPant = l1.get(i-1).pants.departureTime - l1.get(i).pants.arrivalTime;
                }

            }
        }
        System.out.println("Server4 Pant Maximum Queue Length = "+maxPant);

        return 1;
    }




//    public double getServer1MaximumLength() {
//        System.out.println("Server1 Maximum Queue Length = "+server1.maxLengthQueue);
//        return server1.maxLengthQueue;
//    }
//
//    public double getServer2MaximumLength() {
//        System.out.println("Server2 Maximum Queue Length = "+server2.maxLengthQueue);
//        return server2.maxLengthQueue;
//    }
//
//    public double getServer3MaximumLength() {
//        System.out.println("Server3 Maximum Queue Length = "+server3.maxLengthQueue);
//        return server3.maxLengthQueue;
//    }
//    public double getServer4MaximumLength() {
//        System.out.println("Server4 Maximum Queue Length = "+server4.maxLengthQueue);
//        return server4.maxLengthQueue;
//    }

    //Average Length ...
    public double getServer1AverageLength() {
        System.out.println("\n");
        ArrayList<Suits> l1 = new ArrayList(server1.served);
        double avg = 0;
        for(int i = 1; i < server1.served.size(); i++){
            if(l1.get(i).arrivalTime < l1.get(i-1).departureTime){

                avg += l1.get(i-1).departureTime - l1.get(i).arrivalTime ;
            }
        }
        avg = avg/server1.served.size();
        System.out.println("Server1 Maximum Queue Length = "+avg);
        return avg;
    }

    public double getServer2AverageLength() {

        ArrayList<Jacket> l1 = new ArrayList(server2.serveJacket);
        double avg = 0;
        for(int i = 1; i < server2.serveJacket.size(); i++){
            if(l1.get(i).arrivalTime < l1.get(i-1).departureTime){

                avg += l1.get(i-1).departureTime - l1.get(i).arrivalTime ;
            }
        }
        avg = avg/server2.serveJacket.size();
        System.out.println("Server2 Jacket Maximum Queue Length = "+avg);
        return avg;
    }

    public double getServer3AverageLength() {

        ArrayList<Pants> l1 = new ArrayList(server3.servePant);
        double avg = 0;
        for(int i = 1; i < server3.servePant.size(); i++){
            if(l1.get(i).arrivalTime < l1.get(i-1).departureTime){

                avg += l1.get(i-1).departureTime - l1.get(i).arrivalTime ;
            }
        }
        avg = avg/server3.servePant.size();
        System.out.println("Server3 Pant Maximum Queue Length = "+avg);
        return avg;
    }

    public double getServer4AverageLength() {

        ArrayList<Suits> l1 = new ArrayList(server4.served);
        double avg = 0;
        for(int i = 1; i < server4.served.size(); i++){
            if(l1.get(i).arrivalTime < l1.get(i-1).departureTime){

                avg += l1.get(i-1).departureTime - l1.get(i).arrivalTime ;
            }
        }
        avg = avg/server4.served.size();
        System.out.println("Server4 Maximum Queue Length = "+avg);
        return avg;
    }


//    public double getServer1AverageLength () {
//        for(double a:server1.avgLengthQueue)
//
//        return server1;
//    }

//    public Server getServer1AverageLength () {
//        return server1;
//    }
//    public Server getServer1AverageLength () {
//        return server1;
//    }
//    public Server getServer1AverageLength () {
//        return server1;
//    }


    public double getAverageTime() {
        double avg = 0;
        ArrayList<Suits> list1 = new ArrayList(server1.served);
        ArrayList<Jacket> list2 = new ArrayList(server2.serveJacket);
        ArrayList<Pants> list3 = new ArrayList(server3.servePant);
        ArrayList<Suits> list4 = new ArrayList(server4.served);

        System.out.println("\n~: Average Time :~");
        for(int i = 0; i < server4.served.size(); i++){
            avg += list1.get(i).serviceTime;

            if(list2.get(i).serviceTime > list3.get(i).serviceTime){
                avg += list2.get(i).serviceTime;
            }
            else {
                avg += list3.get(i).serviceTime;
            }
            avg += list4.get(i).serviceTime;

            System.out.println("average time for task"+ (i+1) +" = "+avg/4);
            avg = 0;
        }
        return 1;
    }

    public double getMaximumTime() {
        double max = 0;
        int maxAtServer = 0;
        ArrayList<Suits> list1 = new ArrayList(server1.served);
        ArrayList<Jacket> list2 = new ArrayList(server2.serveJacket);
        ArrayList<Pants> list3 = new ArrayList(server3.servePant);
        ArrayList<Suits> list4 = new ArrayList(server4.served);

        System.out.println("\n~: Maximum Time :~");
        for(int i = 0; i < server4.served.size(); i++){
            if(list1.get(i).serviceTime > max){
                max = list1.get(i).serviceTime;
                maxAtServer = 1;
            }

            if(list2.get(i).serviceTime > max){
                max = list2.get(i).serviceTime;
                maxAtServer = 2;
            }

            if(list3.get(i).serviceTime > max){
                max = list3.get(i).serviceTime;
                maxAtServer = 3;
            }

            if(list4.get(i).serviceTime > max){
                max = list4.get(i).serviceTime;
                maxAtServer = 4;
            }

            System.out.println("maximum time for task "+ (i+1) +": = "+max +" at server = "+ maxAtServer);
            maxAtServer = 0;
            max = 0;
        }
        return 1;
    }
}
