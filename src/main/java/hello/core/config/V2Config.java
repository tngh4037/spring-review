package hello.core.config;

import hello.core.repository.ItemRepository;
import hello.core.repository.jpa.JpaItemRepositoryV2;
import hello.core.repository.jpa.JpaItemRepositoryV3;
import hello.core.repository.v2.ItemQueryRepositoryV2;
import hello.core.repository.v2.ItemRepositoryV2;
import hello.core.service.ItemService;
import hello.core.service.ItemServiceV2;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class V2Config {

    private final ItemRepositoryV2 itemRepositoryV2;
    private final EntityManager em;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV2(itemRepositoryV2, itemQueryRepositoryV2());
    }

    @Bean
    public ItemQueryRepositoryV2 itemQueryRepositoryV2() {
        return new ItemQueryRepositoryV2(em);
    }

    @Bean
    public ItemRepository itemRepository() { // for TestDataInit
        return new JpaItemRepositoryV3(em);
    }

}
