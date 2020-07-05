import java.util.ArrayList;
import java.util.List;

public class Router {

    static class Route {
        QueueingSystem from;
        QueueingSystem to;
        double probability;

        Route(QueueingSystem from, QueueingSystem to, double probability){
            this.from = from;
            this.to = to;
            this.probability = probability;
        }
    }

    static List<List<Route>> routes;

    static void init(int n) {
        routes = new ArrayList<>(n);
        for (int i = 0; i <= n; i++){
            routes.add(new ArrayList<>());
        }
    }

    static void addRoute(QueueingSystem from, QueueingSystem to, double probability){
        int fromId = 0;
        if (from != null) fromId = from.getId();
        routes.get(fromId).add(new Route(from, to, probability));
    }

    static boolean passNext(QueueingSystem system, Request request){

        int fromId = 0;
        if (system != null) fromId = system.getId();
        List<Route> possibleNext = routes.get(fromId);

        Route nextRoute = possibleNext.get(0);
        if (possibleNext.size() != 1){
            double p = Math.random(), acc = 0;

            for (int i = 0; i < possibleNext.size() && acc < p; i++){
                nextRoute = possibleNext.get(i);
                acc += nextRoute.probability;
            }
        }

        QueueingSystem next = nextRoute.to;
        if (next == null) {
            NetworkStatistics.processFinishedRequest(request);
            return true;
        }

        if (next.addRequest(request)) {
            return true;
        }

        next.addToWaitingQueue(system, request);
        return false;
    }
}
