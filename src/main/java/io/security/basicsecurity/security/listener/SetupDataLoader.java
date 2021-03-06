package io.security.basicsecurity.security.listener;

import io.security.basicsecurity.domain.entity.AccessIp;
import io.security.basicsecurity.domain.entity.Account;
import io.security.basicsecurity.domain.entity.Resources;
import io.security.basicsecurity.domain.entity.Role;
import io.security.basicsecurity.domain.entity.RoleHierarchy;
import io.security.basicsecurity.repository.AccessIpRepository;
import io.security.basicsecurity.repository.ResourcesRepository;
import io.security.basicsecurity.repository.RoleHierarchyRepository;
import io.security.basicsecurity.repository.RoleRepository;
import io.security.basicsecurity.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private boolean alreadySetup = false;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ResourcesRepository resourcesRepository;

  @Autowired
  private RoleHierarchyRepository roleHierarchyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AccessIpRepository accessIpRepository;

  private static AtomicInteger count = new AtomicInteger(0);

  @Override
  @Transactional
  public void onApplicationEvent(final ContextRefreshedEvent event) {

    if (alreadySetup) {
      return;
    }

//    setupSecurityResources();
//
//    setupAccessIpData();
//
//    alreadySetup = true;
  }



  private void setupSecurityResources() {
    Set<Role> roles = new HashSet<>();
    Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "?????????");
    roles.add(adminRole);
    createResourceIfNotFound("/admin/**", "", roles, "url");
    createUserIfNotFound("admin@gmail.com", "admin@admin.com", "pass", roles);
    Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "???????????????");
    Role userRole = createRoleIfNotFound("ROLE_USER", "???????????????");
    createRoleHierarchyIfNotFound(managerRole, adminRole);
    createRoleHierarchyIfNotFound(userRole, managerRole);


  }

  @Transactional
  public Role createRoleIfNotFound(String roleName, String roleDesc) {

    Role role = roleRepository.findByRoleName(roleName);

    if (role == null) {
      role = Role.builder()
          .roleName(roleName)
          .roleDesc(roleDesc)
          .build();
    }
    return roleRepository.save(role);
  }

  @Transactional
  public Account createUserIfNotFound(final String userName, final String email, final String password, Set<Role> roleSet) {

    Account account = userRepository.findOneByUsername(userName);

    if (account == null) {
      account = Account.builder()
          .username(userName)
          .email(email)
          .password(passwordEncoder.encode(password))
          .userRoles(roleSet)
          .build();
    }
    return userRepository.save(account);
  }

  @Transactional
  public Resources createResourceIfNotFound(String resourceName, String httpMethod, Set<Role> roleSet, String resourceType) {
    Resources resources = resourcesRepository.findByResourceNameAndHttpMethod(resourceName, httpMethod);

    if (resources == null) {
      resources = Resources.builder()
          .resourceName(resourceName)
          .roleSet(roleSet)
          .httpMethod(httpMethod)
          .resourceType(resourceType)
          .orderNum(count.incrementAndGet())
          .build();
    }
    return resourcesRepository.save(resources);
  }

  @Transactional
  public void createRoleHierarchyIfNotFound(Role childRole, Role parentRole) {

    RoleHierarchy roleHierarchy = roleHierarchyRepository.findByChildName(parentRole.getRoleName());
    if (roleHierarchy == null) {
      roleHierarchy = RoleHierarchy.builder()
          .childName(parentRole.getRoleName())
          .build();
    }
    RoleHierarchy parentRoleHierarchy = roleHierarchyRepository.save(roleHierarchy);

    roleHierarchy = roleHierarchyRepository.findByChildName(childRole.getRoleName());
    if (roleHierarchy == null) {
      roleHierarchy = RoleHierarchy.builder()
          .childName(childRole.getRoleName())
          .build();
    }

    RoleHierarchy childRoleHierarchy = roleHierarchyRepository.save(roleHierarchy);
    childRoleHierarchy.setParentName(parentRoleHierarchy);
  }

  private void setupAccessIpData() {
    AccessIp byIpAddress = accessIpRepository.findByIpAddress("127.0.0.1");
    if (byIpAddress == null) {
      AccessIp accessIp = AccessIp.builder()
          .ipAddress("127.0.0.1")
          .build();
      accessIpRepository.save(accessIp);
    }
  }
}
