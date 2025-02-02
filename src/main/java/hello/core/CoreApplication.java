package hello.core;

import hello.core.config.AppV1Config;
import hello.core.config.AppV2Config;
import hello.core.config.v1_proxy.ConcreteProxyConfig;
import hello.core.config.v1_proxy.InterfaceProxyConfig;
import hello.core.config.v2_dynamicproxy.DynamicProxyBasicConfig;
import hello.core.config.v2_dynamicproxy.DynamicProxyFilterConfig;
import hello.core.config.v3_proxyfactory.ProxyFactoryConfigV1;
import hello.core.config.v3_proxyfactory.ProxyFactoryConfigV2;
import hello.core.config.v4_postprocessor.BeanPostProcessorConfig;
import hello.core.config.v5_autoproxy.AutoProxyConfig;
import hello.core.config.v6_aop.AopConfig;
import hello.core.trace.logtrace.LogTrace;
import hello.core.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

// @Import({AppV1Config.class, AppV2Config.class})
// @Import({InterfaceProxyConfig.class, ConcreteProxyConfig.class})
// @Import(DynamicProxyBasicConfig.class)
// @Import(DynamicProxyFilterConfig.class)
// @Import({ProxyFactoryConfigV1.class, ProxyFactoryConfigV2.class})
// @Import(BeanPostProcessorConfig.class)
// @Import(AutoProxyConfig.class)
@Import(AopConfig.class)
@SpringBootApplication(scanBasePackages = "hello.core.app.v3")
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}
}
