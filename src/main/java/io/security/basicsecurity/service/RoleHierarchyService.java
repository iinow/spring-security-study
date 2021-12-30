package io.security.basicsecurity.service;

import io.security.basicsecurity.repository.RoleHierarchyRepository;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleHierarchyService {

  private final RoleHierarchyRepository roleHierarchyRepository;

  @Transactional
  public String findAllHierarchy() {
    return roleHierarchyRepository.findAll().stream()
        .filter(roleHierarchy -> roleHierarchy.getParentName() != null)
        .map(
            roleHierarchy ->
                roleHierarchy.getParentName().getChildName()
                    + " > "
                    + roleHierarchy.getChildName()
                    + "\n")
        .collect(Collectors.joining(""));
  }
}
