package hello.core.itemservice.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

// 모든 사용자의 요청 로그를 남기는 필터
@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    // doFilter 에는 ServletRequest, ServletResponse 가 들어온다.
    // 이는 HttpServletRequest, HttpServletResponse 의 부모인데, 여기에는 기능이 많이 없다. 따라서 다운캐스팅해서 쓰자.
    // 참고) 초창기 인터페이스가 설계될 때, HTTP 요청말고 다른 것들도 받을 수 있도록 하기 위해 이렇게 설계된 것 같다.
    //    ㄴ 실무에서는 대부분 HttpServletRequest 를 사용한다.
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        log.info("log filter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString(); // 요청 구분 값

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response); // 중요) 항상 다음 (필터/서블릿)를 호출해줘야 한다. 안그럼 여기서 끝난다.
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
