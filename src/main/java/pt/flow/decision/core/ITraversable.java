package pt.flow.decision.core;

import org.javatuples.Pair;

import java.util.Map;

/**
 * This interface makes a node traversable: eg. it enables a node to be traversed by searching algorithms
 * As an alternative to defining an interface one could declare an abstract method in the AbstractNode class,
 * but the usage of Generics would cause type erasure making it painful the usage of getLinkedNodes method without a strong type
 */
public interface ITraversable<CONTEXT_TYPE> {
    /**
     * String is the edge name/ identifier and type
     * Abstract Node is the node it points to
     */
    Map<Pair<String, Class>, AbstractNode<?, CONTEXT_TYPE>> getLinkedNodes();
}
