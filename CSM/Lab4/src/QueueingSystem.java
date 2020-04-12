public interface QueueingSystem {

    int getId();

    boolean addRequest(Request request);
    void addToWaitingQueue(QueueingSystem system, Request request);
    void finishRequest(Request request);
    void unblockRequest(Request request);

    int getQueueLength();
    int getNumChannelsInUse();
}
