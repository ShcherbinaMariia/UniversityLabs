import java.util.*;
import java.util.stream.Collectors;

public class Router {
    private static Set<Transition> candidateTransitions = new HashSet<>();
    private static Random rng = new Random();

    static void addCandidateTransitions(List<Link> newCandidates){
        newCandidates.forEach(link -> candidateTransitions.add(link.getTransition()));
    }

    static Transition selectTransition(){

        List<Transition> satisfiableTransitions =
                        candidateTransitions.stream()
                        .filter(Transition::isSatisfiable)
                        .collect(Collectors.toList());

        if (satisfiableTransitions.isEmpty()) return null;

        int highestPriority = satisfiableTransitions.stream()
                .max(Comparator.comparingInt(Transition::getPriority))
                .get().getPriority();

        List<Transition> satisfiableHighestPriority = satisfiableTransitions.stream()
                .filter(transition -> transition.getPriority() == highestPriority)
                .collect(Collectors.toList());

        int nextIndex = rng.nextInt(satisfiableHighestPriority.size());
        return satisfiableHighestPriority.get(nextIndex);
    }

    static void reset(){
        candidateTransitions.clear();
    }
}
