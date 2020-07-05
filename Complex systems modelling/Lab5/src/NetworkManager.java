import java.util.ArrayList;
import java.util.List;

public class NetworkManager {
    private static List<Position> positions = new ArrayList<>();
    private static List<Transition> transitions = new ArrayList<>();

    static void processTimeMoment() {
        while (true){
            Transition nextTransition = Router.selectTransition();
            if (nextTransition == null) break;
            nextTransition.activate();
        }
        Router.reset();
    }

    static void simulate(double maxTime){
        configureNetwork(maxTime);
        processTimeMoment();

        while(Timeline.getTime() < maxTime){
            Event nextEvent = Timeline.next();
            if (Timeline.getTime() > maxTime) break;
            System.out.format("Current time %.2f\n", Timeline.getTime());
            nextEvent.source.withdraw();

            processTimeMoment();
        }

        NetworkStatistics.printReport();
    }

    static void configureNetwork(double maxTime) {
        for (int i = 0; i < 10; i++){
            positions.add(new Position());
        }

        NetworkStatistics.init(positions.size(), maxTime);

        for (int i = 0; i < 6; i++){
            transitions.add(new Transition());
        }

        transitions.get(0).addDelayRNG(5);
        transitions.get(1).addDelayRNG(5);
        transitions.get(5).addDelayRNG(3);

        transitions.get(2).setPriority(1);

        // describing network connections
        Link.addLink(positions.get(0), transitions.get(0), Link.Direction.Input, 1);
        Link.addLink(positions.get(0), transitions.get(0), Link.Direction.Output, 1);
        Link.addLink(positions.get(1), transitions.get(0), Link.Direction.Output, 1);

        Link.addLink(positions.get(1), transitions.get(1), Link.Direction.Input, 1);
        Link.addLink(positions.get(2), transitions.get(1), Link.Direction.Input, 1);
        Link.addLink(positions.get(2), transitions.get(1), Link.Direction.Output, 1);
        Link.addLink(positions.get(3), transitions.get(1), Link.Direction.Output, 1);

        Link.addLink(positions.get(3), transitions.get(2), Link.Direction.Input, 1);
        Link.addLink(positions.get(6), transitions.get(2), Link.Direction.Input, 1);
        Link.addLink(positions.get(5), transitions.get(2), Link.Direction.Output, 1);

        Link.addLink(positions.get(3), transitions.get(3), Link.Direction.Input, 1);
        Link.addLink(positions.get(4), transitions.get(3), Link.Direction.Output, 1);

        Link.addLink(positions.get(5), transitions.get(4), Link.Direction.Input, 1);
        Link.addLink(positions.get(7), transitions.get(4), Link.Direction.Input, 1);
        Link.addLink(positions.get(6), transitions.get(4), Link.Direction.Output, 1);
        Link.addLink(positions.get(8), transitions.get(4), Link.Direction.Output, 1);

        Link.addLink(positions.get(8), transitions.get(5), Link.Direction.Input, 1);
        Link.addLink(positions.get(7), transitions.get(5), Link.Direction.Output, 1);
        Link.addLink(positions.get(9), transitions.get(5), Link.Direction.Output, 1);

        // setting initial marking
        positions.get(0).addMarkers(1);
        positions.get(2).addMarkers(2);
        positions.get(6).addMarkers(8);
        positions.get(7).addMarkers(2);

        // configuring report
        NetworkStatistics.addReportEntry(
                NetworkStatistics.ReportEntry.Type.MarkersInTheEndOfSimulation,
                4, "Rejected requests");

        NetworkStatistics.addReportEntry(
                NetworkStatistics.ReportEntry.Type.MarkersInTheEndOfSimulation,
                9, "Served requests");

        NetworkStatistics.addReportEntry(
                NetworkStatistics.ReportEntry.Type.AverageMarkers,
                1, "Average queue length in queueing system 1");

        NetworkStatistics.addReportEntry(
                NetworkStatistics.ReportEntry.Type.AverageMarkers,
                2, "Average free channels in queueing system 1");

        NetworkStatistics.addReportEntry(
                NetworkStatistics.ReportEntry.Type.AverageMarkers,
                5, "Average queue length in queueing system 2");

        NetworkStatistics.addReportEntry(
                NetworkStatistics.ReportEntry.Type.AverageMarkers,
                7, "Average free channels in queueing system 2");
    }
}
