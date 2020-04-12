public class InputStream {
    ExponentialRNG exponentialRNG;
    int requestsCounter = 0;

    InputStream(double lambda){
        this.exponentialRNG = new ExponentialRNG(lambda);
    }

    void createRequest(){
        double timeInterval = exponentialRNG.next();
        double creationTime = Timeline.getTime() + timeInterval;
        Request request = new Request(requestsCounter, creationTime);
        requestsCounter++;
        Timeline.addEvent(new Event(creationTime, request, null));
    }
}
