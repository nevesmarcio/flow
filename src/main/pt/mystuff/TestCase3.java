package pt.mystuff;

import pt.mystuff.decision.DecisionNode;

import java.util.concurrent.atomic.AtomicLong;

public class TestCase3 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        AtomicLong atomicLongA = new AtomicLong();
        AtomicLong atomicLongB = new AtomicLong();
        AtomicLong atomicLongC = new AtomicLong();

        // NODE declaration
        DecisionNode<Boolean, AtomicLong[]> start = new DecisionNode<>("start_node_name");
        DecisionNode<Void, AtomicLong[]> end = new DecisionNode<>("end_node_name");

        // START NODE - start node :: rock on/ //
        start.setLogic(context -> {
            context[1].incrementAndGet();
            return true;
        });
        start.link(true, end);// for the purpose of this test I will link the start node to the end node on both options (true and false)

        // END NODE - final node :: no decision/ //
        end.setLogic(context -> {
            System.out.println("context: " + context.toString());
            return null;
        });

        // kickoff //
        start.jump(new AtomicLong[]{atomicLongA, atomicLongB, atomicLongC});
        System.out.println("------------------------");

    }

}
