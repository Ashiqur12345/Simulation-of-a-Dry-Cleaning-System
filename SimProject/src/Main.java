public class Main {
    public static void main(String arg[]){
        Service s = new Service();

        s.service();

        s.getServer1Utilization();
        s.getServer2Utilization();
        s.getServer3Utilization();
        s.getServer4Utilization();

        s.getServer1MaximumLength();
        s.getServer2MaximumLength();
        s.getServer3MaximumLength();
        s.getServer4MaximumLength();

        s.getServer1AverageLength();
        s.getServer2AverageLength();
        s.getServer3AverageLength();
        s.getServer4AverageLength();

        s.getAverageTime();
        s.getMaximumTime();

    }
}
