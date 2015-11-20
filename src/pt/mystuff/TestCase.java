package pt.mystuff;

import java.util.Random;

import pt.mystuff.decision.DecisionNode;
import pt.mystuff.decision.LeafNode;
import pt.mystuff.decision.core.AbstractContext;
import pt.mystuff.decision.core.ICommand;

public class TestCase {

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// NODE declaration
		DecisionNode<String, AbstractContext> node1 = new DecisionNode<String, AbstractContext>("node1_name");
		DecisionNode<Integer, AbstractContext> node2 = new DecisionNode<Integer, AbstractContext>("node2_name");
		DecisionNode<Boolean, AbstractContext> node3 = new DecisionNode<Boolean, AbstractContext>("node3_name");
		DecisionNode<Long, AbstractContext> a_new_node = new DecisionNode<Long, AbstractContext>("a_new_node_name");
		LeafNode<Void, AbstractContext> terminal_node = new LeafNode<>("terminal_node_name");

		// /DECISION NODE1 - start node :: rock on/ //
		node1.setLogic(new ICommand<String, AbstractContext>() {

			@Override
			public String execute(AbstractContext context) {
				boolean calc = new Random(System.nanoTime()).nextBoolean();
				System.out.println("**node1_decision_process** b: " + calc);
				return calc ? "ans_node2" : "ans_node3";
			}
		});
		node1.link("ans_node2", node2);
		node1.link("ans_node3", node3);

		// /DECISION NODE2 - final node :: no decision/ //
		node2.setLogic(new ICommand<Integer, AbstractContext>() {

			@Override
			public Integer execute(AbstractContext context) {
				System.out.println("**node2_decision_process**");
				return 0;
			}
		});
		node2.link(0, a_new_node);
		
		// /DECISION NODE3 - bridge node :: ?/ // 
		node3.setLogic(new ICommand<Boolean, AbstractContext>() {

			@Override
			public Boolean execute(AbstractContext context) {
				System.out.println("**node3_decision_process**");
				return true;
			}
		});
		node3.link(true, node2);

		// /A NEW NODE/ // 
		a_new_node.setLogic(new ICommand<Long, AbstractContext>() {

			@Override
			public Long execute(AbstractContext context) {
				Long lng = System.nanoTime() % 4L + 1;
				System.out.println("**a_new_node_decision_process** l: " + lng);
				switch (lng.intValue()) {
				case 1: 
				case 2:
				case 3:
				case 4:
					return lng; 
				default:
					return null;
				}
			}
		});
		a_new_node.link(1L, node1);
		a_new_node.link(2L, node2);
		a_new_node.link(3L, node3);
		a_new_node.link(4L, terminal_node);

		// /TERMINAL NODE/ //
		terminal_node.setLogic(new ICommand<Void, AbstractContext>() {
			@Override
			public Void execute(AbstractContext context) {
				System.out.println("**terminal_node_decision_process**");
				return null;
			}
		});
		
		// kickoff //
		AbstractContext ctx;
		node1.jump(ctx = new AbstractContext() {
			private String coolStuff;
			
			// fluent interface
			public AbstractContext setCoolStuff(String coolStuff) {
				this.coolStuff = coolStuff;
				return this;
			}
			
			@Override
			public String toString() {
				return super.toString() + coolStuff;
			}
		}.setCoolStuff("cooL!"));
		System.out.println("------------------------");

		// kickoff 2 //
		node3.jump(ctx);
		System.out.println("------------------------");
		
		// kickoff 3 //
		a_new_node.jump(ctx);
		System.out.println("------------------------");
		
		// kickoff 4 //
		terminal_node.jump(ctx);
		
	}

}
