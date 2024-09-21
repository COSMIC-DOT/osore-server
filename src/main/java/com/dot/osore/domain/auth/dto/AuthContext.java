package com.dot.osore.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class AuthContext {
    private SignInInfo principal;
}
