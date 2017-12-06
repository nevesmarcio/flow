package pt.flow.decision;

import pt.flow.decision.core.AbstractNode;
import pt.flow.decision.core.ICommand;

import java.util.Collections;

/**
 * Specific implementation of a note that ends a flow. Its jump function does
 * not recursively call other nodes jump function. This node does not support
 * links to other nodes.
 *
 * @param <CONTEXT_TYPE> Context type to execute the logic on
 * @author Márcio Neves
 */
public class LeafNode<CONTEXT_TYPE> extends AbstractNode<Void, CONTEXT_TYPE> {

    public LeafNode(String name) {
        super(name);
    }

    public static <CONTEXT_TYPE> LeafNode<CONTEXT_TYPE> create(String name, Class<CONTEXT_TYPE> input) {
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
    public void print(int level) {
        String prefix = String.join("|", Collections.nCopies(level, "\t")) + "└ ";
        System.out.println(prefix + getName());
    }
}
