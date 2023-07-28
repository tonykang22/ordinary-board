package com.tonykang22.ordinaryboard.config;

import com.tonykang22.ordinaryboard.domain.UserAccount;
import com.tonykang22.ordinaryboard.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean
    private UserAccountRepository userAccountRepository;

    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "tonyTest",
                "pw",
                "tony-test@gmail.com",
                "tony-test",
                "testing"
        )));
    }
}
