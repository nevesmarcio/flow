package pt.mystuff;

import pt.mystuff.decision.DecisionNode;

public class TestCase4 {
    /**
     * @param args
     */
    public static void main(String[] args) {

        // NODE declaration
        DecisionNode<Boolean, Void> start = new DecisionNode<>("start_node_name");
        DecisionNode<Void, Void> end = new DecisionNode<>("end_node_name");

        // START NODE - start node :: rock on/ //
        start.setLogic(context -> true);
        start.link(true, end);// for the purpose of this test I will link the start node to the end node on both options (true and false)

        // END NODE - final node :: no decision/ //
        end.setLogic(context -> null);

        // kickoff //
        start.jump(null);
        System.out.println("------------------------");

    }

}
