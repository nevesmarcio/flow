package pt.mystuff.decision.core;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This abstract class is the basic structure of a node: <br>
 * -->It implements the INode interface to guarantee that the node has a
 * navigation method. <br>
 * -->It is composed by a ICommand object that guarantees that each node can
 * store its specific logic to be executed. <br>
 * -->Each node also has a name.
 *
 * @param <ANSWER_TYPE>
 * @param <CONTEXT_TYPE>
 * @author Márcio Neves
 */
public abstract class AbstractNode<ANSWER_TYPE, CONTEXT_TYPE> implements INode<CONTEXT_TYPE> {
    private static Logger LOGGER = Logger.getLogger(AbstractNode.class.getName());

    private String name;
    private ICommand<ANSWER_TYPE, CONTEXT_TYPE> logic;

    public ICommand<ANSWER_TYPE, CONTEXT_TYPE> getLogic() {
        return logic;
    }

    public void setLogic(ICommand<ANSWER_TYPE, CONTEXT_TYPE> logic) {
        this.logic = logic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractNode(String name) {
        this.name = name;
    }

    @Override
    public CONTEXT_TYPE jump(CONTEXT_TYPE context) {
        if (LOGGER.isLoggable(Level.FINE)) LOGGER.fine("name: " + this.getName());
        return context;
    }

}
