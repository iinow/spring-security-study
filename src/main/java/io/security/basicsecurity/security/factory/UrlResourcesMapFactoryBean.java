package io.security.basicsecurity.security.factory;

import io.security.basicsecurity.service.SecurityResourceService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class UrlResourcesMapFactoryBean implements FactoryBean<Map<RequestMatcher, List<ConfigAttribute>>> {

  private SecurityResourceService securityResourceService;
  private Map<RequestMatcher, List<ConfigAttribute>> resourceMap;

  @Override
  public Map<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {
    if (resourceMap == null) {
      init();
    }
    return resourceMap;
  }

  private void init() {
    this.resourceMap = securityResourceService.getResourceList();
  }

  @Override
  public Class<?> getObjectType() {
    return LinkedHashMap.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }
}
