package pt.mystuff.decision;

import org.junit.Assert;
import org.junit.Test;
import pt.mystuff.decision.core.AbstractContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleFlowTest {


    private static Logger LOGGER = Logger.getLogger(SimpleFlowTest.class.getName());

    private void testMultipleParameters(Object... a) {

    }

    @Test(expected = StackOverflowError.class)
    public void stackOverflow() {
        // NODE declaration
        DecisionNode<Boolean, AbstractContext> start = new DecisionNode<>("start_node_name");
        DecisionNode<Boolean, AbstractContext> middle_1 = new DecisionNode<>("middle_node_1_name");
        DecisionNode<Boolean, AbstractContext> middle_2 = new DecisionNode<>("middle_node_2_name");
        DecisionNode<Void, AbstractContext> end = new DecisionNode<>("end_node_name");

        // START NODE - start node :: rock on/ //
        start.setLogic(context -> {
            LOGGER.info("start here");
            return true;
        });
        start.link(true, middle_1);// for the purpose of this test I will link the start node to the end node on both options (true and false)

        middle_1.setLogic(context -> {
            // some reflection
            Method setter = null, getter = null;
            try {
                setter = context.getClass().getMethod("setA", long.class);
                getter = context.getClass().getMethod("getA");
                long l = (long) getter.invoke(context);
                setter.invoke(context, l + 1);
                if (LOGGER.isLoggable(Level.FINEST)) LOGGER.finest(":" + (long) getter.invoke(context));
            } catch (NoSuchMethodException e) {
                Assert.fail(e.getMessage());
            } catch (IllegalAccessException e) {
                Assert.fail(e.getMessage());
            } catch (InvocationTargetException e) {
                Assert.fail(e.getMessage());
            }

            return true;
        });
        middle_1.link(true, middle_2);

        middle_2.setLogic(context1 -> true);
        middle_2.link(true, middle_1);

        // END NODE - final node :: no decision/ //
        end.setLogic(context -> {
            LOGGER.info("end here");
            return null;
        });

        // kickoff //
        start.jump(new AbstractContext() {
            private long a;

            public void setA(long a) {
                this.a = a;
            }

            public long getA() {
                return a;
            }
        });

    }

}
