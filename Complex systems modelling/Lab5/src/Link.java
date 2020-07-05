public class Link {
    enum Direction {
        Input,
        Output
    }
    private Position position;
    private Transition transition;
    private Direction direction;
    private int multiplicity;

    private Link(Position position, Transition transition, Direction direction, int multiplicity){
        this.position = position;
        this.transition = transition;
        this.direction = direction;
        this.multiplicity = multiplicity;
    }

    static void addLink(Position position, Transition transition, Direction direction, int multiplicity){
        Link link = new Link(position, transition, direction, multiplicity);

        if (direction == Direction.Input){
            transition.addInput(link);
            position.addInputTo(link);
        } else {
            transition.addOutput(link);
        }
    }

    Transition getTransition() {
        return this.transition;
    }

    void withdrawMarkers(){
        this.position.withdrawMarkers(this.multiplicity);
    }

    void addMarkers() {
        this.position.addMarkers(this.multiplicity);
    }

    boolean isSatisfiable() {
        return this.position.getMarkers() >= this.multiplicity;
    }

}
