package com.miniblog.back.member.email;

import com.miniblog.back.member.dto.request.EmailVerificationRequestDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailProvider {

    private final JavaMailSender javaMailSender;

    public void send(EmailVerificationRequestDTO requestDTO, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            String SUBJECT = "[Mini Blog] 인증메일";
            String htmlContent = getContent(code);

            messageHelper.setTo(requestDTO.email());
            messageHelper.setSubject(SUBJECT);
            messageHelper.setText(htmlContent, true);
            messageHelper.setFrom("teamhyy0626@gmail.com");

            javaMailSender.send(message);

        } catch (Exception ex) {
            throw new RuntimeException("이메일 발송 실패");
        }
    }

    private String getContent(String code) {
        String content = "";
        content += "<h1 style='text-align: center;'>[Mini Blog] 인증메일</h1>";
        content += "<h3 style='text-align: center;'>인증코드 : <strong style='font-size: 32px; letter-spacing: 8px;'>"+ code + "</strong></h3>";
        return content;

    }
}
