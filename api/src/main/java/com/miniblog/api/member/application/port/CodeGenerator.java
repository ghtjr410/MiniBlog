package com.miniblog.api.member.application.port;

import com.miniblog.api.member.domain.EmailCodeType;

import java.util.List;

public interface CodeGenerator {
    List<EmailCodeType> getTypes();
    String generateCode();
}
