package io.security.basicsecurity.security.factory;

import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class UrlResourcesMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

//  private SecurityResourceService securityResourceService;
  private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap;

  @Override
  public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {
    if (resourceMap == null) {

    }
    return resourceMap;
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
