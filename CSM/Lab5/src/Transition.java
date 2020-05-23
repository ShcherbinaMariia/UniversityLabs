import java.util.ArrayList;
import java.util.List;

public class Transition {
    private static int nextId = 0;

    private int id;
    private int priority = 0;
    private List<Link> inputs;
    private List<Link> outputs;
    private ExponentialRNG delayRNG = null;
    private boolean occupied = false;

    Transition(){
        this.id = nextId;
        nextId++;
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
    }

    void addDelayRNG(double lambda){
        delayRNG = new ExponentialRNG(lambda);
    }

    void addInput(Link link){
       inputs.add(link);
    }

    void addOutput(Link link){
        outputs.add(link);
    }

    void setPriority(int priority) {
        this.priority = priority;
    }

    int getPriority() {
        return this.priority;
    }

    int getId(){
        return this.id;
    }

    boolean hasDelay() {
        return delayRNG != null;
    }

    void activate() {
        System.out.println("Transition " + this.getId() + " was activated");
        inputs.forEach(Link::withdrawMarkers);
        occupied = true;
        if (this.hasDelay()) {
            double delay = delayRNG.next();
            Timeline.addEvent(new Event(Timeline.getTime() + delay, this));
        } else {
            withdraw();
        }
    }

    void withdraw(){
        System.out.println("Transition " + this.getId() + " was freed");
        occupied = false;
        outputs.forEach(Link::addMarkers);
    }

    boolean isSatisfiable() {
        if (occupied) return false;
        for (Link link: inputs){
            if (!link.isSatisfiable()) return false;
        }
        return true;
    }

}
