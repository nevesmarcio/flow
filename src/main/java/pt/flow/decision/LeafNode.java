package pt.flow.decision;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.flow.decision.core.AbstractNode;
import pt.flow.decision.core.ICommand;

import java.util.Map;

/**
 * Specific implementation of a note that ends a flow. Its jump function does
 * not recursively call other nodes jump function. This node does not support
 * links to other nodes.
 *
 * @param <CONTEXT_TYPE> Context type to execute the logic on
 * @author Márcio Neves
 */
public class LeafNode<CONTEXT_TYPE> extends AbstractNode<Void, CONTEXT_TYPE> {
    private static final Logger LOG = LoggerFactory.getLogger(LeafNode.class);
    public LeafNode(String name) {
        super(name);
    }

    public static <CONTEXT_TYPE> LeafNode<CONTEXT_TYPE> create(String name) {
        return new LeafNode<>(name);
    }

    @Override
    public LeafNode<CONTEXT_TYPE> setLogic(ICommand<Void, CONTEXT_TYPE> logic) {
        this.logic = logic;
        return this;
    }

    @Override
    public CONTEXT_TYPE jump(CONTEXT_TYPE context) {
        context = super.jump(context);
        this.getLogic().execute(context);
        return context;
    }

    @Override
    public Map<Pair<String,Class>, AbstractNode<?, CONTEXT_TYPE>> getLinkedNodes() {
        return null;
    }
}
