package pt.flow.decision;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Not really a test case - used it to develop the lib and try out some graphs
 */
public class FluentCreationTest {
    private static final Logger LOG = LoggerFactory.getLogger(ComplexContextTest.class);

    @Test
    public void dummy() {

        LeafNode<Long> printResult = LeafNode.<Long>create("print")
                .setLogic(context -> {
                    LOG.info(context.toString());
                    return null;
                });

        DecisionNode<Boolean, Long> node = DecisionNode.<Boolean, Long>create("checkValue")
                .setLogic(context -> context > 0L)
                .link(Boolean.TRUE, printResult)
                .link(Boolean.FALSE, printResult);

        node.print(0);
    }


}
