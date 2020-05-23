public class Event implements Comparable<Event> {

    double time;
    Transition source;

    Event(double time, Transition source){
        this.time = time;
        this.source = source;
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(this.time, o.time);
    }
}