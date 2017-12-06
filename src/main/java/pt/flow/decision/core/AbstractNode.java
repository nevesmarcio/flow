package pt.flow.decision.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * This abstract class is the basic structure of a node: <br>
 * -- It implements the INode interface to guarantee that the node has a
 * navigation method. <br>
 * -- It is composed by a ICommand object that guarantees that each node can
 * store its specific logic to be executed. <br>
 * -- Each node also has a name.
 *
 * @param <ANSWER_TYPE>  Response type
 * @param <CONTEXT_TYPE> Context type to execute the logic on
 * @author Márcio Neves
 */
public abstract class AbstractNode<ANSWER_TYPE, CONTEXT_TYPE> implements INode<CONTEXT_TYPE> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractNode.class);
    private final String name;
    protected ICommand<ANSWER_TYPE, CONTEXT_TYPE> logic;

    public AbstractNode(String name) {
        this.name = name;
    }

    public ICommand<ANSWER_TYPE, CONTEXT_TYPE> getLogic() {
        return logic;
    }

    public AbstractNode<ANSWER_TYPE, CONTEXT_TYPE> setLogic(ICommand<ANSWER_TYPE, CONTEXT_TYPE> logic) {
        this.logic = logic;
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public CONTEXT_TYPE jump(CONTEXT_TYPE context) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("name: " + this.getName());
        }
        return context;
    }

    public void print(int level) {
        System.out.println(String.join("|", Collections.nCopies(level, "\t")) + "└ " + getName());
    }
}
