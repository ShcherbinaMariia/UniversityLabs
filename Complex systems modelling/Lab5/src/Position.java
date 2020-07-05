import java.util.ArrayList;
import java.util.List;

public class Position {

    private static int nextId = 0;

    private int id;
    private List<Link> inputTo = new ArrayList<>();
    private int markers;

    Position(){
        this.id = nextId;
        nextId++;
    }

    int getId(){
        return this.id;
    }

    void addInputTo(Link link){
        inputTo.add(link);
    }

    void withdrawMarkers(int amount){
        markers -= amount;
        NetworkStatistics.positionChange(this);
    }

    void addMarkers(int amount){
        markers += amount;
        Router.addCandidateTransitions(this.inputTo);
        NetworkStatistics.positionChange(this);
    }

    int getMarkers(){
        return this.markers;
    }
}
