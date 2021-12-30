package io.security.basicsecurity.security.voter;

import io.security.basicsecurity.service.SecurityResourceService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@RequiredArgsConstructor
public class IpAddressVoter implements AccessDecisionVoter<Object> {

  private final SecurityResourceService securityResourceService;

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  public int vote(
      Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
    var details = (WebAuthenticationDetails) authentication.getDetails();
    String remoteAddress = details.getRemoteAddress();

    if(securityResourceService.getAccessIpList().contains(remoteAddress)) {
      return ACCESS_ABSTAIN;
    }

    throw new AccessDeniedException("Invalid IpAddress");
  }
}
