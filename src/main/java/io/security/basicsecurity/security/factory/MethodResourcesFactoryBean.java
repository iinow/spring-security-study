package io.security.basicsecurity.security.factory;

import io.security.basicsecurity.service.SecurityResourceService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;

@RequiredArgsConstructor
public class MethodResourcesFactoryBean implements FactoryBean<Map<String, List<ConfigAttribute>>> {

  @Autowired
  private SecurityResourceService securityResourceService;

  private final String resourceType;

  private Map<String, List<ConfigAttribute>> resourceMap;

  private void init() {
    if (resourceType.equals("method")) {
      this.resourceMap = securityResourceService.getMethodResourceList();
    } else if (resourceType.equals("pointcut")) {
      this.resourceMap = securityResourceService.getPointcutResourceList();
    }
  }

  @Override
  public Map<String, List<ConfigAttribute>> getObject() {
    if (resourceMap == null) {
      init();
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
