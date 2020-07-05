public class Event implements Comparable<Event> {

    double time;
    Request request;
    QueueingSystem source;

    Event(double time, Request request, QueueingSystem source){
        this.time = time;
        this.request = request;
        this.source = source;
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(this.time, o.time);
    }
}
