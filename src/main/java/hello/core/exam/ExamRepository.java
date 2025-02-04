package hello.core.exam;

import hello.core.exam.annotation.Retry;
import hello.core.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private static int seq = 0;

    /**
     * 5번에 한 번은 실패하는 요청
     * -> aop 로 retry 할 것.
     */
    @Trace
    @Retry(value = 4)
    public String save(String itemId) {
        seq++;

        if (seq % 5 == 0) {
            throw new IllegalStateException("예외 발생");
        }

        return "ok";
    }

}
