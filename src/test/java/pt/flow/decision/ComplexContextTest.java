package pt.flow.decision;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class ComplexContextTest {
    private static final Logger LOG = LoggerFactory.getLogger(ComplexContextTest.class);

    @Test
    public void arrayOfAtomicLongs() {
        LOG.info("arrayOfAtomicLongs");

        AtomicLong atomicLongA = new AtomicLong();
        AtomicLong atomicLongB = new AtomicLong();
        AtomicLong atomicLongC = new AtomicLong();

        // NODE declaration
        DecisionNode<Boolean, AtomicLong[]> start = new DecisionNode<>("start_node_name");

        // END NODE A - final node :: no decision/ //
        LeafNode<AtomicLong[]> end_node_A = new LeafNode<>("end_node_A_name");
        end_node_A.setLogic(context -> {
            int i = 0;
            for (AtomicLong al : context) {
                LOG.info("al[" + i++ + "]:" + al.get());
            }
            return null;
        });
        // END NODE B - fluent style - final node :: no decision/ //
        LeafNode<AtomicLong[]> end_node_B = LeafNode.<AtomicLong[]>create("end_node_B_name")
                .setLogic(context -> {
                    int i = 0;
                    for (AtomicLong al : context) {
                        LOG.info("al[" + i++ + "]:" + al.get());
                    }
                    return null;
                });

        // START NODE - start node :: rock on/ //
        start.setLogic(context -> {
            context[0].incrementAndGet();
            return context[0].longValue() % 2 == 0;
        });
        start.link(true, end_node_A);// for the purpose of this test I will link the start node to the end node on both options (true and false)
        start.link(false, end_node_B);


        // kickoff //
        AtomicLong[] arrALong;
        start.jump(arrALong = new AtomicLong[]{atomicLongA, atomicLongB, atomicLongC});
        Assert.assertEquals(arrALong[0].get(), 1L);


    }

}
