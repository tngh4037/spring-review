package hello.core.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection; // jdbc 표준 인터페이스가 제공하는 Connection
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.core.connection.ConnectionConst.*;

@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection() {

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection={}, class={}", connection, connection.getClass());
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
