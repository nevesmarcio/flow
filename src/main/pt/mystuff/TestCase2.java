package pt.mystuff;

import pt.mystuff.decision.DecisionNode;
import pt.mystuff.decision.core.AbstractContext;

public class TestCase2 {

	private void testMultipleParameters(Object ... a) {

    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// NODE declaration
		DecisionNode<Boolean, AbstractContext> start = new DecisionNode<>("start_node_name");
		DecisionNode<Boolean, AbstractContext> middle_1 = new DecisionNode<>("middle_node_1_name");
		DecisionNode<Boolean, AbstractContext> middle_2 = new DecisionNode<>("middle_node_2_name");
        DecisionNode<Void, AbstractContext> end = new DecisionNode<>("end_node_name");

		// START NODE - start node :: rock on/ //
		start.setLogic(context -> {
            System.out.println("stop here");
            return true;});
        start.link(true, middle_1);// for the purpose of this test I will link the start node to the end node on both options (true and false)

        middle_1.setLogic(context -> true);
        middle_1.link(true, middle_2);

        middle_2.setLogic(context1 -> true);
        middle_2.link(true, end);

		// END NODE - final node :: no decision/ //
		end.setLogic(context -> {
            System.out.println("context: " + context.toString());
            return null;
        });

		// kickoff //
        start.jump(new AbstractContext() {
            Long a;
public void setA(Long a) {
    this.a =a;
}
        });
		System.out.println("------------------------");

	}

}
