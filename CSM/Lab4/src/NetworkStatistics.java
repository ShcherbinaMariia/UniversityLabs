import java.util.LinkedList;
import java.util.List;

public class NetworkStatistics {

    private static int nServed = 0;
    private static List<List<StateEntry>> states = new LinkedList<>();
    private static double timeSpentInNetwork;
    private static double maxTime;

    public static void init(double maxTime){
        NetworkStatistics.maxTime = maxTime;
        for (int i = 0; i <= NetworkManager.queueingSystems.size(); i++){
            states.add(new LinkedList<>());
        }
    }

    public static void processFinishedRequest(Request request){
        nServed++;
        timeSpentInNetwork += Timeline.getTime() - request.getCreationTime();
    }

    public static void updateStates(){
        double time = Timeline.getTime();
        for (QueueingSystem qs : NetworkManager.queueingSystems){
            states.get(qs.getId()).add(new StateEntry(time, qs.getQueueLength(), qs.getNumChannelsInUse()));
        }
    }

    public static void printReport(){
        System.out.println();
        System.out.println("Simulation results: ");

        double avgTimeSpentInNetwork = timeSpentInNetwork / nServed;
        System.out.format("Average time spent in system by request T = %.2f\n", avgTimeSpentInNetwork);
        System.out.println();

        for (QueueingSystem qs: NetworkManager.queueingSystems){
            System.out.println("For the system " + qs.getId() + ": ");

            double prevTime = 0, queueLengthAcc = 0, channelsInUseAcc = 0;

            for (StateEntry entry : states.get(qs.getId())){
                queueLengthAcc += (entry.timestamp - prevTime) * entry.queueLength;
                channelsInUseAcc += (entry.timestamp - prevTime) * entry.channelsInUse;
                prevTime = entry.timestamp;
            }
            System.out.format("Average queue length L = %.2f\n", queueLengthAcc/maxTime);
            System.out.format("Average waiting time in queue Q = %.2f\n", queueLengthAcc/nServed);
            System.out.format("Average number of channels in use R = %.2f\n", channelsInUseAcc/maxTime);
            System.out.println();
        }
    }
}

class StateEntry{

    double timestamp;
    int queueLength;
    int channelsInUse;

    StateEntry(double timestamp, int queueLength, int channelsInUse){
        this.timestamp = timestamp;
        this.queueLength = queueLength;
        this.channelsInUse = channelsInUse;
    }
}
