package com.miniblog.api.common.api;

import com.miniblog.api.auth.application.util.UserPrincipal;
import com.miniblog.api.common.domain.exception.ResourceUnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

import static com.miniblog.api.auth.domain.AuthErrorMessage.AUTH_INVALID_CREDENTIALS;
import static com.miniblog.api.auth.domain.AuthErrorMessage.AUTH_MISSING_CREDENTIALS;

@Component
public class MemberIdResolver implements HandlerMethodArgumentResolver {

    /**
     * @MemberId 어노테이션이 적용된 Long 타입 파라미터를 지원
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberId.class)
                && parameter.getParameterType().equals(Long.class);
    }

    /**
     * 현재 인증된 사용자 정보를 기반으로 회원 ID 반환
     * 인증 정보가 없거나 유효하지 않으면 예외 발생
     */
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        Authentication authentication = getAuthentication();
        UserPrincipal principal = getAuthenticatedPrincipal(authentication);

        return getMemberId(principal);
    }

    /**
     * 현재 요청의 인증 정보 조회
     * 없을 경우 인증 예외 발생
     */
    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ResourceUnauthorizedException(AUTH_MISSING_CREDENTIALS);
        }
        return authentication;
    }

    /**
     * 인증 정보에서 사용자 정보를 추출
     * 유효하지 않으면 예외 발생
     */
    private UserPrincipal getAuthenticatedPrincipal(Authentication authentication) {
        return Optional.ofNullable(authentication.getPrincipal())
                .filter(UserPrincipal.class::isInstance)
                .map(UserPrincipal.class::cast)
                .orElseThrow(() -> new ResourceUnauthorizedException(AUTH_INVALID_CREDENTIALS));
    }

    /**
     * 사용자 정보에서 회원 ID 추출
     * ID가 존재하지 않으면 예외 발생
     */
    private Long getMemberId(UserPrincipal principal) {
        if (principal.getMember() == null || principal.getMember().getId() == null) {
            throw new ResourceUnauthorizedException(AUTH_INVALID_CREDENTIALS);
        }
        return principal.getMember().getId();
    }
}
