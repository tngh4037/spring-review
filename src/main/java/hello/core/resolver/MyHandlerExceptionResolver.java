package hello.core.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {

        try {
            
            // 상태 변환: IllegalArgumentException 은 400으로 처리하고 싶다.
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(400, ex.getMessage()); // response.sendError 로 상태 바꿈
                return new ModelAndView(); // 예외는 먹어버리고, ModelAndView 에 내용이 없이 보냄. (정상 흐름으로 전환)
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null; // null 리턴시, 예외가 그대로 전달
    }
}
