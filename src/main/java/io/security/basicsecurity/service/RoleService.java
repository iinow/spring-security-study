package io.security.basicsecurity.service;

import io.security.basicsecurity.domain.entity.Role;
import io.security.basicsecurity.repository.RoleRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Transactional
  public Role getRole(long id) {
    return roleRepository.findById(id).orElse(new Role());
  }

  @Transactional
  public List<Role> getRoles() {

    return roleRepository.findAll();
  }

  @Transactional
  public void createRole(Role role){

    roleRepository.save(role);
  }

  @Transactional
  public void deleteRole(long id) {
    roleRepository.deleteById(id);
  }
}
