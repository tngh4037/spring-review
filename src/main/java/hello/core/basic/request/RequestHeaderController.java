package hello.core.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod, // HTTP 메서드를 조회한다.
                          Locale locale, // Locale 정보를 조회한다. (가장 우선순위가 높은 것)
                          @RequestHeader MultiValueMap<String, String> headerMap, // 모든 HTTP 헤더를 MultiValueMap 형식으로 조회한다.
                          @RequestHeader("host") String host, // 특정 HTTP 헤더를 조회한다.
                          @CookieValue(value = "myCookieName", required = false) String myCookieValue // 특정 쿠키를 조회한다.
    ) {
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", myCookieValue);

        return "ok";
    }

}
