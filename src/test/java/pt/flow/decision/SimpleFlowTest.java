package pt.flow.decision;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.flow.decision.core.AbstractContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleFlowTest {
    private static final Logger LOG = LoggerFactory.getLogger(ComplexContextTest.class);

    private void testMultipleParameters(Object... a) {

    }

    @Test(expected = StackOverflowError.class)
    public void stackOverflow() {
        // NODE declaration
        // START NODE - start node :: rock on/ //
        DecisionNode<Boolean, AbstractContext> start = DecisionNode.<Boolean, AbstractContext>create("start_node_name").setLogic(context -> {
            LOG.info("start here");
            return true;
        });

        // MIDDLE NODE //
        DecisionNode<Boolean, AbstractContext> middle_1 = DecisionNode.<Boolean, AbstractContext>create("middle_node_1_name")
                .setLogic(context -> {
                    // some reflection
                    Method setter = null, getter = null;
                    try {
                        setter = context.getClass().getMethod("setA", long.class);
                        getter = context.getClass().getMethod("getA");
                        long l = (long) getter.invoke(context);
                        setter.invoke(context, l + 1);
                        if (LOG.isTraceEnabled()) LOG.trace(":" + (long) getter.invoke(context));
                    } catch (NoSuchMethodException e) {
                        Assert.fail(e.getMessage());
                    } catch (IllegalAccessException e) {
                        Assert.fail(e.getMessage());
                    } catch (InvocationTargetException e) {
                        Assert.fail(e.getMessage());
                    }

                    return true;
                });

        // MIDDLE NODE //
        DecisionNode<Boolean, AbstractContext> middle_2 = DecisionNode.<Boolean, AbstractContext>create("middle_node_2_name")
                .setLogic(context1 -> true);

        // END NODE - final node :: no decision - equivalent to LeafNode/ //
        DecisionNode<Void, AbstractContext> end = DecisionNode.<Void, AbstractContext>create("end_node_name").setLogic(context -> {
            LOG.info("end here");
            return null;
        });

        // Linking - Graph creation
        start.link(true, middle_1);// for the purpose of this test I will link the start node to the end node on both options (true and false)
        middle_1.link(true, middle_2);
        middle_2.link(true, middle_1);

        // kickoff //
        start.jump(new AbstractContext() {
            private long a;

            public long getA() {
                return a;
            }

            public void setA(long a) {
                this.a = a;
            }
        });

    }

}
