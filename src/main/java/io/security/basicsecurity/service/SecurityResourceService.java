package io.security.basicsecurity.service;

import io.security.basicsecurity.repository.ResourcesRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
}
