public class Request {
    private int id;
    double creationTime;

    Request(int id, double creationTime) {
        this.id = id;
        this.creationTime = creationTime;
    }

    int getId(){
        return this.id;
    }

    double getCreationTime() { return this.creationTime; }
}
