package hello.core.service;

import hello.core.domain.Member;
import hello.core.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        Connection con = dataSource.getConnection();

        try {
            con.setAutoCommit(false); // 트랜잭션 시작 ( set autocommit false; )
            
            // 비즈니스 로직 수행
            bizLogic(con, fromId, toId, money);
            // 비즈니스 로직 종료

            con.commit(); // 성공시 commit
        } catch (Exception e) {
            con.rollback(); // 실패시 rollback
            throw new IllegalStateException(e);
        } finally {
            // JdbcUtils.closeConnection(con);
            release(con);
        }
    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }

    private void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); // 커넥션 풀 고려
                con.close(); // 참고) (커넥션 풀을 쓰면) 커넥션이 다시 풀로 돌아간다. 따라서 수동커밋 모드를 유지한 상태로 돌아가게 되면, 누군가 이후 커넥션을 획득해서 사용하는 경우, 그대로 수동커밋 모드로 사용하게 되는 것이다. 따라서 자동커밋 모드로 변경 후 반납해야 한다.
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }

}
