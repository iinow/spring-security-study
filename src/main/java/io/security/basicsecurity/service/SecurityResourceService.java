package io.security.basicsecurity.service;

import io.security.basicsecurity.domain.entity.AccessIp;
import io.security.basicsecurity.domain.entity.Resources;
import io.security.basicsecurity.repository.AccessIpRepository;
import io.security.basicsecurity.repository.ResourcesRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SecurityResourceService {

  private final ResourcesRepository resourcesRepository;

  private final AccessIpRepository accessIpRepository;

  public Map<RequestMatcher, List<ConfigAttribute>> getResourceList() {
    return resourcesRepository.findAllResources().stream()
        .collect(
            Collectors.toMap(
                v -> new AntPathRequestMatcher(v.getResourceName()),
                v ->
                    v.getRoleSet().stream()
                        .map(role -> new SecurityConfig(role.getRoleName()))
                        .collect(Collectors.toList()),
                (x, y) -> y,
                LinkedHashMap::new));
  }

  public Map<String, List<ConfigAttribute>> getMethodResourceList() {
    return resourcesRepository.findAllMethodResources().stream()
        .collect(
            Collectors.toMap(
                Resources::getResourceName,
                v ->
                    v.getRoleSet().stream()
                        .map(role -> new SecurityConfig(role.getRoleName()))
                        .collect(Collectors.toList()),
                (x, y) -> y,
                LinkedHashMap::new));
  }

  public Map<String, List<ConfigAttribute>> getPointcutResourceList() {
    return resourcesRepository.findAllPointcutResources().stream()
        .collect(
            Collectors.toMap(
                Resources::getResourceName,
                v ->
                    v.getRoleSet().stream()
                        .map(role -> new SecurityConfig(role.getRoleName()))
                        .collect(Collectors.toList()),
                (x, y) -> y,
                LinkedHashMap::new));
  }

  public Set<String> getAccessIpList() {
    return accessIpRepository.findAll().stream()
        .map(AccessIp::getIpAddress)
        .collect(Collectors.toSet());
  }
}
