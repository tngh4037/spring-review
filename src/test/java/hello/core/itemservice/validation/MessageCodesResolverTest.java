package hello.core.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    // bindingResult.reject("required", null, "defaultMessage");
    // : bindingResult.reject(..) 는 내부에서 ObjectError를 생성한다.
    //    ㄴ 이때, 생성자에 전달하는 메시지 코드(codes)는 MessageCodesResolver 를 통해서 (errorCode를 기반으로) 생성된 것을 전달한다.
    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }

        assertThat(messageCodes).containsExactly(
                "required.item", // 오류코드.객체명
                "required"); // 오류코드
    }

    // bindingResult.rejectValue("itemName", "required");
    // : bindingResult.rejectValue(..) 는 내부에서 FieldError를 생성한다.
    //    ㄴ 이때, 생성자에 전달하는 메시지 코드(codes)는 MessageCodesResolver 를 통해서 (errorCode를 기반으로) 생성된 것을 전달한다.
    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes(
                "required", "item", "itemName", String.class);

        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }

        assertThat(messageCodes).containsExactly(
                        "required.item.itemName", // 오류코드.객체명.필드명
                        "required.itemName", // 오류코드.필드명
                        "required.java.lang.String", // 오류코드.필드타입
                        "required"); // 오류코드
    }
}
