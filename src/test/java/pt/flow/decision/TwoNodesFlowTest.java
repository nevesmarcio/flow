package pt.flow.decision;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.logging.Logger;

public class TwoNodesFlowTest {

    private static Logger LOGGER = Logger.getLogger(TwoNodesFlowTest.class.getName());

    private static DecisionNode<Boolean, Boolean> start;

    @BeforeClass
    public static void setupFlow() {
        LOGGER.info("SetupFlow");

        // NODE declaration
        start = new DecisionNode<>("start_node_name");
        DecisionNode<Boolean, Boolean> end = new DecisionNode<>("end_node_name");

        // START NODE - start node :: rock on
        // for the purpose of this test I will link the start node to the end node on both options (true and false)
        start.link(true, end);
        start.link(false, end);
        start.setLogic(context -> context.booleanValue());

        // END NODE - final node :: no decision - this is a leaf node in practice
        // no linking, so it's a final/ leaf node
        end.setLogic(context -> context.booleanValue());
    }

    @Test
    public void truePath() {
        LOGGER.info("SimpleTest");
        // kickoff //
        Assert.assertEquals(start.jump(true), true);
    }

    @Test
    public void falsePath() {
        LOGGER.info("SimpleTestAltPath");
        // kickoff //
        Assert.assertEquals(start.jump(false), false);
    }

}
