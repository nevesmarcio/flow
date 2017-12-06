package pt.flow.decision.core;

/**
 * This interface allows to pass a command to a node. Each node implementation
 * decides when to execute this command, but typically this is executed on the
 * jump method before applying the exit strategy of the current node
 *
 * @param <ANSWER_TYPE>  Response type
 * @param <CONTEXT_TYPE> Context type to execute the logic on
 * @author Márcio Neves
 */
public interface ICommand<ANSWER_TYPE, CONTEXT_TYPE> {
    ANSWER_TYPE execute(CONTEXT_TYPE context);
}
