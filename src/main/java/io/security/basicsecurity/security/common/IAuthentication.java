package io.security.basicsecurity.security.common;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public abstract class IAuthentication<C, D, P> extends AbstractAuthenticationToken {

  public IAuthentication(
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
  }

  @Override
  public abstract C getCredentials();

  @Override
  public abstract P getPrincipal();
}
