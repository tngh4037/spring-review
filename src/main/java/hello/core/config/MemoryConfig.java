package hello.core.config;

import hello.core.repository.ItemRepository;
import hello.core.repository.memory.MemoryItemRepository;
import hello.core.service.ItemService;
import hello.core.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }
}
