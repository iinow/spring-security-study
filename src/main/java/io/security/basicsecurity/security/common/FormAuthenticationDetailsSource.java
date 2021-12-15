package io.security.basicsecurity.security.common;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class FormAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, FormWebAuthenticationDetails> {

  @Override
  public FormWebAuthenticationDetails buildDetails(HttpServletRequest context) {
    return new FormWebAuthenticationDetails(context);
  }
}
