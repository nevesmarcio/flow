package pt.flow;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.flow.decision.DecisionNode;
import pt.flow.decision.core.AbstractContext;

import java.util.Random;

public class FlowTest {

    private static final Logger LOG = LoggerFactory.getLogger(FlowTest.class);

    DecisionNode<Boolean, AbstractContext> start = DecisionNode.<Boolean, AbstractContext>create("start_node_name").setLogic(context -> {
        LOG.info("start here");
        return true;
    });

    // MIDDLE NODES - LAYER 1//
    DecisionNode<Boolean, AbstractContext> middle_1 = DecisionNode.<Boolean, AbstractContext>create("middle_node_1_name")
            .setLogic(context -> {
                LOG.info("middle_node_1");
                return new Random().nextBoolean();//whatever - it doesn't influence traversing - not even called;
            });

    // MIDDLE NODES - LAYER 2//
    DecisionNode<Boolean, AbstractContext> middle_2_true = DecisionNode.<Boolean, AbstractContext>create("middle_node_2_name_true-path")
            .setLogic(context1 -> {
                LOG.info("middle_node_2_true");
                return new Random().nextBoolean();//whatever - it doesn't influence traversing - not even called
            });
    DecisionNode<Boolean, AbstractContext> middle_2_false = DecisionNode.<Boolean, AbstractContext>create("middle_node_2_name_false-path")
            .setLogic(context -> {
                LOG.info("middle_node_2_false");
                return new Random().nextBoolean();//whatever - it doesn't influence traversing - not even called
            });

    // END NODE - final node :: no decision - equivalent to LeafNode/ //
    DecisionNode<Void, AbstractContext> end = DecisionNode.<Void, AbstractContext>create("end_node_name").setLogic(context -> {
        LOG.info("end here");
        return null;
    });

    @Before
    public void setUp() throws Exception {
        start.link(true, middle_1);
        start.link(false, middle_1);
        middle_1.link(true, middle_2_true);
        middle_1.link(false, middle_2_false);
        middle_2_true.link(true, end);
        middle_2_true.link(false, end);
        middle_2_false.link(true, end);
        middle_2_false.link(false, start);

    }

    @Test
    public void depthFirstSearch() {
        Flow.depthFirstSearch(start);
        LOG.info("---------------------");
        Flow.depthFirstSearch(middle_1);
        LOG.info("---------------------");
        Flow.depthFirstSearch(middle_2_true);
    }
}