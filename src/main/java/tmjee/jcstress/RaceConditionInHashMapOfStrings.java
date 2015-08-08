package tmjee.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.LongResult2;

import java.util.HashMap;
import java.util.Map;

public class RaceConditionInHashMapOfStrings {

    @State
    public static class MyState {
        final Map<String, String> map = new HashMap<>();
    }

    @Description("test racily getting String from HashMap")
    @JCStressTest
    @Outcome(id="[0, 1]", expect = Expect.ACCEPTABLE, desc="get back expected character 'a' and character 'b'")
    public static class StressTest2  {

        @Actor
        public void actor1(MyState myState, LongResult2 r1) {
            myState.map.put("k","a");
            String r = myState.map.get("k");
            r1.r1 = (r == null) ? -1 : (r.equals("a") ? 0 : (r.equals("b") ? 1 : -1));
        }

        @Actor
        public void actor2(MyState myState, LongResult2 r1) {
            myState.map.put("k", "b");
            String r = myState.map.get("k");
            r1.r2 = (r == null) ? -1 : (r.equals("a") ? 0 : (r.equals("b") ? 1 : -1));
        }
    }


}
