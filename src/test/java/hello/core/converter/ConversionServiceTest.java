package hello.core.converter;

import hello.core.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.*;

public class ConversionServiceTest {

    @Test
    void conversionService() {
        // 등록 ( 우리가 사용할 컨버터들을 컨버전 서비스에 등록 )
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // 사용 (참고. conversionService 가 타입 정보 등을 참고해서, 실행되어야 할 컨버터를 찾아 실행한다.)
        Integer result1 = conversionService.convert("10", Integer.class); // Integer.class: source 가 반환해야 할 타입
        assertThat(result1).isEqualTo(10);

        String result2 = conversionService.convert(10, String.class);
        assertThat(result2).isEqualTo("10");

        IpPort result3 = conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(result3).isEqualTo(new IpPort("127.0.0.1", 8080));

        String result4 = conversionService.convert(new IpPort("127.0.0.1", 8080), String.class);
        assertThat(result4).isEqualTo("127.0.0.1:8080");
    }

}
