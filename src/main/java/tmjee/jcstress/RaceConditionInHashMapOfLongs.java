package tmjee.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.LongResult2;

import java.util.HashMap;
import java.util.Map;

public class RaceConditionInHashMapOfLongs {

    @State
    public static class MyState {
        public final Map<String, Long> map = new HashMap<>();
    }


    @JCStressTest
    @Description("Test racily getting long from HashMap")
    @Outcome(id="[0, 1]", expect = Expect.ACCEPTABLE, desc = "get back long 0L and 1L")
    public static class StressTest1 {

        @Actor
        public void actor1(MyState myState, LongResult2 r1) {
            myState.map.put("k", 0L);
            Long r = myState.map.get("k");
            r1.r1 = (r == null ? -1 : r);
        }

        @Actor
        public void actor2(MyState myState, LongResult2 r1) {
            myState.map.put("k", 1L);
            Long r = myState.map.get("k");
            r1.r2 = (r == null ? -1 : r);
        }
    }
}
