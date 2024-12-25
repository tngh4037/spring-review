package hello.core.api;

import hello.core.exception.UserException;
import hello.core.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    // (동작순서) 해당 controller 에서 오류 발생 -> DispatcherServlet -> ExceptionHandlerExceptionResolver -> 해당 컨트롤러에 @ExceptionHandler 있는지 체크 -> (있다면) 호출 (+ 이때, 해당 컨트롤러의 특성들을 살려서 처리된다. (@ResponseBody 등))
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // response.setStatus(400)
    public ErrorResult illegalExceptionHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage()); // 참고로 완전히 정상 흐름으로 처리된다. -> 따라서 WAS까지 예외가 전달되지 않기에 WAS에서 다시 내부 호출(/error)하지 않는다.
    }

    @ExceptionHandler // 애노테이션에 예외를 생략하면, 메서드 매개변수의 예외로 적용된다.
    public ResponseEntity<ErrorResult> userHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        return new ResponseEntity<>(
                new ErrorResult("USER-EX", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult exHandler(Exception e) { // (IllegalArgumentException(+하위 포함), UserException(+하위 포함)) 에서 처리하지 못하는 경우, 여기서 처리
        log.error("[exHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
