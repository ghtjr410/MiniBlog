package com.miniblog.api.member.infrastructure.code;

import com.miniblog.api.member.application.port.CodeGenerator;
import com.miniblog.api.member.domain.EmailCodeType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.miniblog.api.member.domain.EmailCodeType.*;

@Component
public class EmailVerificationCodeGenerator implements CodeGenerator {

    @Override
    public List<EmailCodeType> getTypes() {
        return List.of(EMAIL_VERIFICATION_CODE);
    }

    /**
     * 4자리 숫자 랜덤 코드 생성
     */
    @Override
    public String generateCode() {
        return IntStream.range(0, 4)
                .mapToObj(i -> String.valueOf((int) (Math.random() * 10)))
                .collect(Collectors.joining());
    }
}
