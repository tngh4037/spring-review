package hello.core.basic.request;

import hello.core.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @RequestMapping("/request-param-v2")
    @ResponseBody
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    // HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
    @RequestMapping("/request-param-v3")
    @ResponseBody
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // String, Integer, int 등의 단순 타입이면서, 변수명이 요청파라미터명과 같으면 @RequestParam 도 생략 가능
    @RequestMapping("/request-param-v4")
    @ResponseBody
    public String requestParamV4(String username,
                                 int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam.required (default: true)
     *
     * 주의) 필수 파라미터가 없는 경우
     * /request-param-required 요청 시 
     *  => username이 없으므로 예외 ( 400 Bad Request )
     *
     * 주의) 파라미터 이름만 사용하는 경우
     * /request-param-required?username= 요청 시
     *  => 빈문자로 통과
     *
     * 주의) Primitive 사용 시
     * /request-param-required 요청 시
     *  => int age -> null을 int에 입력하는 것은 불가능
     *    ㄴ 파라미터를 보내지 않은 경우 null 이 들어가야 하는데, int 라서, 이 경우 500 오류
     *  => 해결방법: Integer로 변경하거나 or defaultValue 사용하기
     */
    @RequestMapping("/request-param-required")
    @ResponseBody
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * defaultValue 를 사용하면 아래 모두 적용된다.
     * - 파라미터가 없는 경우
     * - 파라미터 이름만 사용하는 경우 (값은 비어 있는 경우)
     *
     * 따라서 defaultValue 적용 시, required 는 크게 의미가 없다.
     */
    @RequestMapping("/request-param-default")
    @ResponseBody
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                      @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam Map, MultiValueMap
     * - Map(key=value)
     * - MultiValueMap(key=[value1, value2, ...]) ex) (key=userIds, value=[id1, id2])
     *
     * 파라미터의 값이 1개가 확실하다면 Map을 사용해도 되지만, 그렇지 않다면 MultiValueMap을 사용하자.
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"),
                paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@RequestParam String username,
                                   @RequestParam int age) {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(/*@ModelAttribute*/ HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

}
