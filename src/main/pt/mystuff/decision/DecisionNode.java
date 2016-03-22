package pt.mystuff.decision;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.mystuff.decision.core.AbstractNode;


/**
 * Specific implementation of a node that navigates out of itself, according to
 * the return value of its own logic function. Its jump function calls
 * recursively the resolved node jump function.
 *
 * @param <ANSWER_TYPE>
 * @param <CONTEXT_TYPE>
 * @author Márcio Neves
 */
public class DecisionNode<ANSWER_TYPE, CONTEXT_TYPE> extends AbstractNode<ANSWER_TYPE, CONTEXT_TYPE> {
    private static Logger LOGGER = Logger.getLogger(DecisionNode.class.getName());

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

        ANSWER_TYPE s;
        try {
            if ((s = this.getLogic().execute(context)) == null) {
                if (LOGGER.isLoggable(Level.INFO)) LOGGER.info("logic block returning null => leaf node - no more jumps!");
            } else if (!links.containsKey(s)) {
                if (LOGGER.isLoggable(Level.INFO)) LOGGER.info("no links => leaf node - no more jumps!");
            } else {
                links.get(s).jump(context);
            }
        } catch (Exception e) {
            if (LOGGER.isLoggable(Level.SEVERE)) LOGGER.log(Level.SEVERE, "Failed to jump!", e);
        }
        return context;
    }


}
