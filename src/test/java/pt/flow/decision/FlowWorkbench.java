package pt.flow.decision;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.flow.Flow;
import pt.flow.decision.core.AbstractContext;
import pt.flow.decision.core.ICommand;

import java.util.Random;

/**
 * Not really a test case - used it to develop the lib and try out some graphs
 */
public class FlowWorkbench {
    private static final Logger LOG = LoggerFactory.getLogger(ComplexContextTest.class);

    @Test
    public void dummy() {
        // NODE declaration
        DecisionNode<String, AbstractContext> node1 = new DecisionNode<String, AbstractContext>("node1_name");
        DecisionNode<Integer, AbstractContext> node2 = new DecisionNode<Integer, AbstractContext>("node2_name");
        DecisionNode<Boolean, AbstractContext> node3 = new DecisionNode<Boolean, AbstractContext>("node3_name");
        DecisionNode<Long, AbstractContext> a_new_node = new DecisionNode<Long, AbstractContext>("a_new_node_name");
        LeafNode<AbstractContext> terminal_node = new LeafNode<>("terminal_node_name");

        // /DECISION NODE1 - start node :: rock on
        node1.setLogic(new ICommand<String, AbstractContext>() {

            @Override
            public String execute(AbstractContext context) {
                boolean calc = new Random(System.nanoTime()).nextBoolean();
                LOG.info("**node1_decision_process**: " + calc);
                return calc ? "ans_node2" : "ans_node3";
            }
        });
        node1.link("ans_node2", node2);
        node1.link("ans_node3", node3);

        // /DECISION NODE2 - final node :: no decision/ //
        node2.setLogic(new ICommand<Integer, AbstractContext>() {

            @Override
            public Integer execute(AbstractContext context) {
                LOG.info("**node2_decision_process**");
                return 0;
            }
        });
        node2.link(0, a_new_node);

        // /DECISION NODE3 - bridge node :: ?/ //
        node3.setLogic(new ICommand<Boolean, AbstractContext>() {

            @Override
            public Boolean execute(AbstractContext context) {
                LOG.info("**node3_decision_process**");
                return true;
            }
        });
        node3.link(true, node2);

        // /A NEW NODE/ //
        a_new_node.setLogic(new ICommand<Long, AbstractContext>() {

            @Override
            public Long execute(AbstractContext context) {
                Long lng = System.nanoTime() % 4L + 1;
                LOG.info("**a_new_node_decision_process** l: " + lng);
                switch (lng.intValue()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        return lng;
                    default:
                        return null;
                }
            }
        });
        a_new_node.link(1L, node1);
        a_new_node.link(2L, node2);
        a_new_node.link(3L, node3);
        a_new_node.link(4L, terminal_node);

        // /TERMINAL NODE/ //
        terminal_node.setLogic(new ICommand<Void, AbstractContext>() {
            @Override
            public Void execute(AbstractContext context) {
                LOG.info("**terminal_node_decision_process**");
                return null;
            }
        });

        // kickoff 1 //
        AbstractContext ctx;
        node1.jump(ctx = new AbstractContext() {
            private String coolStuff;

            // fluent interface
            public AbstractContext setCoolStuff(String coolStuff) {
                this.coolStuff = coolStuff;
                return this;
            }

            @Override
            public String toString() {
                return coolStuff + " | super=" + super.toString();
            }
        }.setCoolStuff("cooL!"));
        LOG.info("------------------------");


        // I can start at any point in the chain
        // kickoff 2 //
        node3.jump(ctx);
        LOG.info("------------------------");

        // kickoff 3 //
        a_new_node.jump(ctx);
        LOG.info("------------------------");

        // kickoff 4 //
        terminal_node.jump(ctx);
        LOG.info("------------------------");

        Flow.depthFirstSearch(node1);
    }


}
