package pt.flow.decision;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class ComplexContextTest {
    private static Logger LOGGER = Logger.getLogger(ComplexContextTest.class.getName());

    @Test
    public void arrayOfAtomicLongs() {
        LOGGER.info("arrayOfAtomicLongs");

        AtomicLong atomicLongA = new AtomicLong();
        AtomicLong atomicLongB = new AtomicLong();
        AtomicLong atomicLongC = new AtomicLong();

        // NODE declaration
        DecisionNode<Boolean, AtomicLong[]> start = new DecisionNode<>("start_node_name");
        DecisionNode<Void, AtomicLong[]> end = new DecisionNode<>("end_node_name");

        // START NODE - start node :: rock on/ //
        start.setLogic(context -> {
            context[1].incrementAndGet();
            return true;
        });
        start.link(true, end);// for the purpose of this test I will link the start node to the end node on both options (true and false)

        // END NODE - final node :: no decision/ //
        end.setLogic(context -> {
            int i = 0;
            for (AtomicLong al : context) {
                LOGGER.info("al[" + i++ + "]:" + al.get());
            }
            return null;
        });

        // kickoff //
        AtomicLong[] arrALong;
        start.jump(arrALong = new AtomicLong[]{atomicLongA, atomicLongB, atomicLongC});
        Assert.assertEquals(arrALong[1].get(), 1L);


    }

}
