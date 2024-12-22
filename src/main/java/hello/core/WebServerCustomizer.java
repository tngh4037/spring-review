package hello.core;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

// 서블릿 컨테이너가 이렇게 하도록 제공해준다.
// 웹 서버를 커스터마이징 하는 것.
// @Component
public class WebServerCustomizer
        implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // (404 예외의 경우) /error-page/400 를 처리할 컨트롤러를 WAS에서 다시 호출
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");

        // (500 예외의 경우) /error-page/500 를 처리할 컨트롤러를 WAS에서 다시 호출
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

        // RuntimeException 과 그 하위 예외 발생시 /error-page/500 를 처리할 컨트롤러를 WAS에서 다시 호출
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }
}
