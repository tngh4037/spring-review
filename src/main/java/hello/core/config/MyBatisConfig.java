package hello.core.config;

import hello.core.repository.ItemRepository;
import hello.core.repository.jdbctemplate.JdbcTemplateItemRepositoryV3;
import hello.core.repository.mybatis.ItemMapper;
import hello.core.repository.mybatis.MyBatisItemRepository;
import hello.core.service.ItemService;
import hello.core.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private final ItemMapper itemMapper;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MyBatisItemRepository(itemMapper);
    }
}
