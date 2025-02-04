package hello.core.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

    // private final ApplicationContext applicationContext;
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }

    public void external() {
        log.info("call external");
        // applicationContext.getBean(CallServiceV2.class).internal(); // 외부 메서드 호출 (프록시를 통한)
        callServiceProvider.getObject().internal(); // 외부 메서드 호출 (프록시를 통한)
    }

    public void internal() {
        log.info("call internal");
    }

}
