package pt.flow.decision;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.flow.decision.core.AbstractNode;
import pt.flow.decision.core.ICommand;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;


/**
 * Specific implementation of a node that navigates out of itself, according to
 * the return value of its own logic function. Its jump function calls
 * recursively the resolved node jump function.
 *
 * @param <ANSWER_TYPE>  Response type
 * @param <CONTEXT_TYPE> Context type to execute the logic on
 * @author Márcio Neves
 */
public class DecisionNode<ANSWER_TYPE, CONTEXT_TYPE> extends AbstractNode<ANSWER_TYPE, CONTEXT_TYPE> {
    private static final Logger LOG = LoggerFactory.getLogger(DecisionNode.class);
    private HashMap<ANSWER_TYPE, AbstractNode<?, CONTEXT_TYPE>> links = new HashMap<>(); // answer/ node

    public DecisionNode(String name) {
        super(name);
    }

    public static <ANSWER_TYPE, CONTEXT_TYPE> DecisionNode<ANSWER_TYPE, CONTEXT_TYPE>
    create(String name) {
        return new DecisionNode<>(name);
    }

    @Override
    public DecisionNode<ANSWER_TYPE, CONTEXT_TYPE> setLogic(ICommand<ANSWER_TYPE, CONTEXT_TYPE> logic) {
        this.logic = logic;
        return this;
    }

    public DecisionNode<ANSWER_TYPE, CONTEXT_TYPE> link(ANSWER_TYPE answer, AbstractNode<?, CONTEXT_TYPE> node) {
        this.links.put(answer, node);
        return this;
    }

    @Override
    public CONTEXT_TYPE jump(CONTEXT_TYPE context) {
        context = super.jump(context);

        ANSWER_TYPE s;
        try {
            s = this.getLogic().execute(context);
            if (s == null) {
                LOG.debug(this.getName() + ": logic block returning null => leaf node - no more jumps!");
            } else if (!links.containsKey(s)) {
                LOG.debug(this.getName() + ": no links => leaf node - no more jumps!");
            } else {
                links.get(s).jump(context);
            }
        } catch (Exception e) {
            LOG.warn(this.getName() + ": Failed to jump!", e);
        }
        return context;
    }

    @Override
    public Map<Pair<String,Class>, AbstractNode<?, CONTEXT_TYPE>> getLinkedNodes() {
        Map<Pair<String,Class>, AbstractNode<?, CONTEXT_TYPE>> linkedNodes = new HashMap<>(links.size());
        links.forEach(new BiConsumer<ANSWER_TYPE, AbstractNode<?, CONTEXT_TYPE>>() {
            @Override
            public void accept(ANSWER_TYPE answer, AbstractNode<?, CONTEXT_TYPE> context) {
                linkedNodes.put(Pair.with(answer.toString(), answer.getClass()), context);
            }
        });

        return linkedNodes;
    }
}
