package io.security.basicsecurity.security.metadatasource;

import io.security.basicsecurity.service.SecurityResourceService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class UrlFilterInvocationSecurityMetadataSource
    implements FilterInvocationSecurityMetadataSource {

  private Map<RequestMatcher, List<ConfigAttribute>> requestMap;

  private final SecurityResourceService securityResourceService;

  public UrlFilterInvocationSecurityMetadataSource(
      Map<RequestMatcher, List<ConfigAttribute>> requestMap,
      SecurityResourceService securityResourceService) {
    this.requestMap = requestMap;
    this.securityResourceService = securityResourceService;
  }

  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    var req = ((FilterInvocation) object).getRequest();
    //    requestMap.put(new AntPathRequestMatcher("/mypage"), List.of(new
    // SecurityConfig("ROLE_USER")));

    return requestMap.entrySet().stream()
        .filter(entry -> entry.getKey().matches(req))
        .limit(1)
        .map(Entry::getValue)
        .findFirst()
        .orElse(null);
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return this.requestMap.entrySet().stream()
        .flatMap(entry -> entry.getValue().stream())
        .collect(Collectors.toList());
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return FilterInvocation.class.isAssignableFrom(clazz);
  }

  public void reload() {
    this.requestMap = securityResourceService.getResourceList();
  }
}
