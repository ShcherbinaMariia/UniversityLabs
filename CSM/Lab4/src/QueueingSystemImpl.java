import java.util.ArrayDeque;
import java.util.Queue;

public class QueueingSystemImpl implements QueueingSystem{

    private Queue<Request> queue = new ArrayDeque<>();
    private Queue<WaitingQueueEntry> waitingQueue = new ArrayDeque<>();
    private int occupiedChannels = 0;
    private int blockedChannels = 0;
    private int freeChannels;
    private int maxQueueLength; // -1 stands for infinite queues
    private ExponentialRNG exponentialRNG;
    private int id;

    class WaitingQueueEntry{
        QueueingSystem system;
        Request request;

        WaitingQueueEntry(QueueingSystem system, Request request){
            this.system = system;
            this.request = request;
        }
    }

    QueueingSystemImpl(int id, int channels, double mu, int maxQueueLength) {
        this.id = id;
        this.freeChannels = channels;
        exponentialRNG = new ExponentialRNG(mu);
        this.maxQueueLength = maxQueueLength;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean addRequest(Request request) {
        if (freeChannels != 0) {
            processRequest(request);
            return true;
        }

        if (maxQueueLength == -1 || queue.size() < maxQueueLength){
            addRequestToQueue(request);
            return true;
        }

        return false;
    }

    @Override
    public void addToWaitingQueue(QueueingSystem system, Request request) {
        waitingQueue.add(new WaitingQueueEntry(system, request));
    }

    @Override
    public void finishRequest(Request request) {
        if (Router.passNext(this, request)){
            freeOccupiedChannel();
            processNextRequest();
        } else {
            blockOccupiedChannel();
        }
    }

    @Override
    public void unblockRequest(Request request) {
        unblockChannel();
        processNextRequest();
    }

    @Override
    public int getQueueLength() {
        return this.queue.size();
    }

    @Override
    public int getNumChannelsInUse() {
        return this.blockedChannels + this.occupiedChannels;
    }

    // channel state transitions
    private void occupyChannel() {
        freeChannels--;
        occupiedChannels++;
    }

    private void blockOccupiedChannel(){
        occupiedChannels--;
        blockedChannels++;
    }

    private void freeOccupiedChannel(){
        occupiedChannels--;
        freeChannels++;
    }

    private void unblockChannel(){
        blockedChannels--;
        freeChannels++;
    }

    private void addRequestToQueue(Request request){
        queue.add(request);
        System.out.println("Request " + request.getId() + " added to queue in system "
                + this.getId() + ". Size of queue: " + this.queue.size());
    }

    private void processRequest(Request request){
        occupyChannel();
        double processingTime = exponentialRNG.next();
        double finishingTime = Timeline.getTime() + processingTime;
        Timeline.addEvent(new Event(finishingTime, request, this));
        System.out.println("System " + this.getId() + " started processing of request "
                + request.getId());
    }

    private void processNextRequest(){
        if (queue.isEmpty()) return;

        Request nextRequest = queue.poll();
        processRequest(nextRequest);
        unblockWaitingRequest();
    }

    private void unblockWaitingRequest(){
        if (waitingQueue.isEmpty()) return;

        WaitingQueueEntry entry = waitingQueue.poll();
        addRequestToQueue(entry.request);
        entry.system.unblockRequest(entry.request);
    }
}
