import java.util.PriorityQueue;

public class Timeline {
    static double currentTime = 0;
    static PriorityQueue<Event> events = new PriorityQueue<>();

    static void addEvent(Event e) {
        events.add(e);
    }

    static Event next() {
        Event nextEvent = events.poll();
        currentTime = nextEvent.time;
        return nextEvent;
    }

    static double getTime(){
        return currentTime;
    }
}
