package pt.mystuff.decision.core;

/**
 * This interface allows to pass a command to a node. Each node implementation
 * decides when to execute this command, but typically this is executed on the
 * jump method before applying the exit strategy of the current node
 * 
 * @author Márcio Neves
 * 
 * @param <ANSWER_TYPE>
 * @param <CONTEXT_TYPE>
 */
public interface ICommand<ANSWER_TYPE, CONTEXT_TYPE> {
	public ANSWER_TYPE execute(CONTEXT_TYPE context);
}
