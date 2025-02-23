package com.miniblog.api.member.infrastructure.code;

import com.miniblog.api.member.application.port.CodeGenerator;
import com.miniblog.api.member.domain.EmailCodeType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.miniblog.api.member.domain.EmailCodeType.*;

@Component
public class MemberSecurityCodeGenerator implements CodeGenerator {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+`~,./?[{]}";
    private static final Random RANDOM = new Random();

    @Override
    public List<EmailCodeType> getTypes() {
        return List.of(PASSWORD_RESET_CODE, USERNAME_RECOVERY_CODE);
    }

    /**
     * 10자리 랜덤 문자열 생성
     */
    @Override
    public String generateCode() {
        return IntStream.range(0, 10)
                .mapToObj(i -> String.valueOf(CHARS.charAt(RANDOM.nextInt(CHARS.length()))))
                .collect(Collectors.joining());
    }
}
