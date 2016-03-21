package pt.mystuff.decision;

import org.junit.Test;
import pt.mystuff.decision.core.AbstractContext;

import java.util.logging.Logger;

public class SimpleFlowTest {


    private static Logger LOGGER = Logger.getLogger(SimpleFlowTest.class.getName());

    private void testMultipleParameters(Object... a) {

    }

    @Test
    public void linearPath() {
        // NODE declaration
        DecisionNode<Boolean, AbstractContext> start = new DecisionNode<>("start_node_name");
        DecisionNode<Boolean, AbstractContext> middle_1 = new DecisionNode<>("middle_node_1_name");
        DecisionNode<Boolean, AbstractContext> middle_2 = new DecisionNode<>("middle_node_2_name");
        DecisionNode<Void, AbstractContext> end = new DecisionNode<>("end_node_name");

        // START NODE - start node :: rock on/ //
        start.setLogic(context -> {
            LOGGER.info("stop here");
            return true;
        });
        start.link(true, middle_1);// for the purpose of this test I will link the start node to the end node on both options (true and false)

        middle_1.setLogic(context -> true);
        middle_1.link(true, middle_2);

        middle_2.setLogic(context1 -> true);
        middle_2.link(true, end);

        // END NODE - final node :: no decision/ //
        end.setLogic(context -> {
            LOGGER.info("context: " + context.toString());
            return null;
        });

        // kickoff //
        start.jump(new AbstractContext() {
            Long a;

            public void setA(Long a) {
                this.a = a;
            }
        });
        LOGGER.info("------------------------");

    }

}
