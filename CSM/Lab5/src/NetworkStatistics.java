import java.util.ArrayList;
import java.util.List;

public class NetworkStatistics {

    static List<List<StatsEntry>> positionsStats;
    static double maxTime;
    static List<ReportEntry> reportFormat;

    static class StatsEntry{
        double timestamp;
        int markers;

        StatsEntry(double timestamp, int markers){
            this.timestamp = timestamp;
            this.markers = markers;
        }
    }

    static class ReportEntry{
        enum Type{
            MarkersInTheEndOfSimulation,
            AverageMarkers
        }

        Type type;
        int positionId;
        String message;

        ReportEntry(Type type, int positionId, String message){
            this.type = type;
            this.positionId = positionId;
            this.message = message;
        }
    }

    static void init(int positions, double maxTime){
        NetworkStatistics.maxTime = maxTime;
        positionsStats =  new ArrayList<>();
        for (int i = 0; i < positions; i++){
            positionsStats.add(new ArrayList<>());
            positionsStats.get(i).add(new StatsEntry(0, 0));
        }
        reportFormat = new ArrayList<>();
    }

    static void positionChange(Position position){
        StatsEntry entry = new StatsEntry(Timeline.getTime(), position.getMarkers());
        positionsStats.get(position.getId()).add(entry);
    }

    static void addReportEntry(ReportEntry.Type type, int positionId, String message){
        reportFormat.add(new ReportEntry(type, positionId, message));
    }

    private static ArrayList<Double> averageMarkers() {

        ArrayList<Double> averageMarkers = new ArrayList<>();

        for (List<StatsEntry> positionStats: positionsStats){
            double prevTimestamp = 0, prevMarkers = 0, accumulator = 0;

            for (StatsEntry current: positionStats){
                accumulator += (current.timestamp - prevTimestamp) * prevMarkers;
                prevMarkers = current.markers;
                prevTimestamp = current.timestamp;
            }

            accumulator += (maxTime - prevTimestamp) * prevMarkers;
            averageMarkers.add(accumulator / maxTime);
        }

        return averageMarkers;
    }


    public static void printReport(){
        System.out.println();
        System.out.println("Simulation results: ");

        List<Double> averageMarkers = averageMarkers();

        for (ReportEntry reportEntry : reportFormat){
            StringBuilder reportEntryBuilder = new StringBuilder();
            reportEntryBuilder.append(reportEntry.message).append(": ");

            if (reportEntry.type == ReportEntry.Type.AverageMarkers){
                reportEntryBuilder.append(averageMarkers.get(reportEntry.positionId));
            }
            if (reportEntry.type == ReportEntry.Type.MarkersInTheEndOfSimulation){
                List<StatsEntry> statsReportEntryPosition = positionsStats.get(reportEntry.positionId);
                reportEntryBuilder.append(statsReportEntryPosition.get(statsReportEntryPosition.size() - 1).markers);
            }
            System.out.println(reportEntryBuilder.toString());
        }
    }
}
