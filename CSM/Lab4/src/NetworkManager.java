import java.util.LinkedList;
import java.util.List;

public class NetworkManager {

    static InputStream inputStream;
    static List<QueueingSystem> queueingSystems = new LinkedList<>();

    public static void createNetwork(){
        // creating network and defining it's configuration
        inputStream = new InputStream(5);

        int n = 2;
        Router.init(n);

        QueueingSystem s1 = new QueueingSystemImpl(1, 2, 5, -1);
        QueueingSystem s2 = new QueueingSystemImpl(2, 2, 3, 8);

        queueingSystems.add(s1);
        queueingSystems.add(s2);

        Router.addRoute(null, s1, 1);
        Router.addRoute(s1, s2, 1);
        Router.addRoute(s2, null, 1);
    }

    public static void simulation(double maxTime){
        createNetwork();
        NetworkStatistics.init(maxTime);
        inputStream.createRequest();
        Event nextEvent = Timeline.next();

        while (Timeline.getTime() < maxTime){
            if (nextEvent.source == null) {
                Router.passNext(null, nextEvent.request);
                inputStream.createRequest();
            } else {
                nextEvent.source.finishRequest(nextEvent.request);
            }
            NetworkStatistics.updateStates();
            nextEvent = Timeline.next();
        }

        NetworkStatistics.printReport();
    }
}
