package hello.core.config;

import hello.core.repository.ItemRepository;
import hello.core.repository.jpa.JpaItemRepository;
import hello.core.repository.mybatis.ItemMapper;
import hello.core.repository.mybatis.MyBatisItemRepository;
import hello.core.service.ItemService;
import hello.core.service.ItemServiceV1;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {

    private final EntityManager em;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepository(em);
    }
}
