package hello.core;

import hello.core.config.JdbcTemplateV1Config;
import hello.core.config.JdbcTemplateV2Config;
import hello.core.config.JdbcTemplateV3Config;
import hello.core.config.MemoryConfig;
import hello.core.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

// @Import(MemoryConfig.class)
// @Import(JdbcTemplateV1Config.class)
// @Import(JdbcTemplateV2Config.class)
@Import(JdbcTemplateV3Config.class)
@SpringBootApplication(scanBasePackages = "hello.core.web")
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

}
