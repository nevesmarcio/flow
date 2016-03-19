package pt.mystuff.decision;

import java.util.HashMap;

import pt.mystuff.decision.core.AbstractNode;


/**
 * Specific implementation of a node that navigates out of itself, according to
 * the return value of its own logic function. Its jump function calls
 * recursively the resolved node jump function.
 * 
 * @author Márcio Neves
 * 
 * @param <ANSWER_TYPE>
 * @param <CONTEXT_TYPE>
 */
public class DecisionNode<ANSWER_TYPE, CONTEXT_TYPE> extends AbstractNode<ANSWER_TYPE, CONTEXT_TYPE> {
	
	public DecisionNode(String name) {
		super(name);
	}

	private HashMap<ANSWER_TYPE, AbstractNode<?, CONTEXT_TYPE>> links = new HashMap<>(); // answer/ node

	public void link(ANSWER_TYPE answer, AbstractNode<?, CONTEXT_TYPE> node) {
		this.links.put(answer, node);
	}

	@Override
	public CONTEXT_TYPE jump(CONTEXT_TYPE context) {
		context = super.jump(context);
		
		Object s;
		try {
			if ((s = this.getLogic().execute(context)) != null)
				links.get(s).jump(context);
			else
				System.out.println("No more jumps!");
		} catch (Exception e) {
			System.out.println("Failed to jump!");
			e.printStackTrace();
		}
		return context;
	}

	
}
