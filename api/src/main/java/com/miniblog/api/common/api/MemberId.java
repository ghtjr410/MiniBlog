package com.miniblog.api.common.api;

import java.lang.annotation.*;

/**
 * 컨트롤러 메서드의 파라미터에서 회원 ID를 추출하는 어노테이션
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MemberId {
}
