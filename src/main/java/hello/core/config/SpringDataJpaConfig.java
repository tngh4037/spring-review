package hello.core.config;

import hello.core.repository.ItemRepository;
import hello.core.repository.jpa.JpaItemRepositoryV2;
import hello.core.repository.jpa.SpringDataJpaItemRepository;
import hello.core.service.ItemService;
import hello.core.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringDataJpaConfig {

    private final SpringDataJpaItemRepository springDataJpaItemRepository;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV2(springDataJpaItemRepository);
    }
}
