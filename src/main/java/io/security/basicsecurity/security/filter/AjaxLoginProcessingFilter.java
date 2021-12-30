package io.security.basicsecurity.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.security.basicsecurity.domain.dto.AccountDto;
import io.security.basicsecurity.security.token.AjaxAuthenticationToken;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

  @Setter
  private ObjectMapper objectMapper = new ObjectMapper();

  public AjaxLoginProcessingFilter() {
    super(new AntPathRequestMatcher("/api/login"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    if(!isAjax(request)) {
      throw new IllegalStateException("Authentication is not supported");
    }

    AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);
    if(ObjectUtils.isEmpty(accountDto.getUsername()) || ObjectUtils.isEmpty(accountDto.getPassword())) {
      throw new IllegalArgumentException("Username or Password is empty");
    }

    AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(accountDto.getUsername(), accountDto.getPassword());
    return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
  }

  private boolean isAjax(HttpServletRequest request) {
    if ("XMLHttpRequest".equals(request.getHeader("X-Requested-with"))) {
      return true;
    }
    return false;
  }
}
