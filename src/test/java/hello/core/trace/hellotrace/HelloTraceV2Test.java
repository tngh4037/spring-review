package hello.core.trace.hellotrace;

import hello.core.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class HelloTraceV2Test {

    @Test
    void begin_end() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
        TraceStatus status3 = trace.beginSync(status2.getTraceId(), "hello3");
        TraceStatus status4 = trace.beginSync(status3.getTraceId(), "hello4");
        trace.end(status4);
        trace.end(status3);
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exceptionEnd() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status1 = trace.begin("hello");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

}