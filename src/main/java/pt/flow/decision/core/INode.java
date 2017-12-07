package pt.flow.decision.core;

/**
 * This interface states that a node needs to have a jump strategy. While
 * navigating on a graph of nodes, this is the function that jumps out from the
 * current node
 *
 * @param <CONTEXT_TYPE> Context type to execute the logic on
 * @author Márcio Neves
 */
public interface INode<CONTEXT_TYPE> {
    CONTEXT_TYPE jump(CONTEXT_TYPE context);
}
