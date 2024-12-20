package hello.core.itemservice.web.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }

        // 세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval()); // 비활성화 시키는 시간(초) (1800: 30분)
        log.info("getCreationTime={}", new Date(session.getCreationTime())); // 세션 생성일자
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime())); // 세션에 마지막으로 접근한 시간 ( maxInactiveInterval 와 계산 )
        log.info("isNew={}", session.isNew());

        return "세션 출력";
    }
}
