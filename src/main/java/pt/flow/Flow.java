package pt.flow;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.flow.decision.core.AbstractNode;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class Flow {
    private static final Logger LOG = LoggerFactory.getLogger(Flow.class);
    private static final String C1 = "└";
    private static final String C2 = "├";
    private static final String C3 = "|";
    private static final String C4 = "\t";
    private static final String ALREADY_EXPLORED = "-->";
    private static final String FINAL_NODE = ">|";

    /**
     * Given a start vertex of a graph, it traverses the graph with a depth first strategy
     *
     * @param vertex
     */
    public static void depthFirstSearch(AbstractNode<?, ?> vertex) {
        // every time the function is called, it create its own context of execution, therefore allowing
        // the search algorithm to be exposed as a static method (part of a "static" class)
        HashMap<String, AbstractNode> exploredNodesMap = new HashMap<>();
        depthFirstSearch(vertex, exploredNodesMap, -1);
    }

    private static void depthFirstSearch(AbstractNode<?, ?> vertex, HashMap<String, AbstractNode> exploredNodesMap, int prevLevel) {
        final int currentLevel = prevLevel + 1;
        final String prefix0 = String.join(C3, Collections.nCopies(currentLevel, C4)) + C1;
        final String edgePrefix = String.join(C2, Collections.nCopies(currentLevel + 1, C4)) + C3;
        final String childPrefix = String.join(C3, Collections.nCopies(currentLevel + 1, C4)) + C1;

        //label 'vertex' as explored
        exploredNodesMap.put(vertex.getName(), vertex);

        if (currentLevel == 0) System.out.println(MessageFormat.format("{0} ''{1}''", prefix0, vertex.getName()));

        //for all edges of 'vertex' do
        vertex.getLinkedNodes().forEach(new BiConsumer<Pair<String, Class>, AbstractNode>() {
            @Override
            public void accept(Pair<String, Class> s, AbstractNode abstractNode) {
                System.out.println(MessageFormat.format("{0} If response ({1}) is ''{2}''", edgePrefix, s.getValue1().getSimpleName(), s.getValue0()));
                boolean isExplored = exploredNodesMap.containsKey(abstractNode.getName());
                int nrEdges = abstractNode.getLinkedNodes().size();
                LOG.debug("[" + currentLevel + "]" + vertex.getName() + "(" + s + ")-->" + abstractNode.getName() + (isExplored && nrEdges > 0 ? "*" : (nrEdges == 0 ? "^" : "")));
                System.out.println(MessageFormat.format("{0} ''{1}'' {2}", childPrefix, abstractNode.getName(), (isExplored && nrEdges > 0) ? ALREADY_EXPLORED : (nrEdges == 0 ? FINAL_NODE : "")));
                if (!isExplored) {
                    depthFirstSearch(abstractNode, exploredNodesMap, currentLevel);
                }
            }
        });
    }
}
