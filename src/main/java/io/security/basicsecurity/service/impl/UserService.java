package io.security.basicsecurity.service.impl;

import io.security.basicsecurity.domain.dto.AccountDto;
import io.security.basicsecurity.domain.entity.Account;
import io.security.basicsecurity.domain.entity.Role;
import io.security.basicsecurity.repository.RoleRepository;
import io.security.basicsecurity.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void createUser(Account account){

    Role role = roleRepository.findByRoleName("ROLE_USER");
    Set<Role> roles = new HashSet<>();
    roles.add(role);
    account.setUserRoles(roles);
    userRepository.save(account);
  }

  @Transactional
  public void modifyUser(AccountDto accountDto){

    ModelMapper modelMapper = new ModelMapper();
    Account account = modelMapper.map(accountDto, Account.class);

    if(accountDto.getRoles() != null){
      Set<Role> roles = new HashSet<>();
      accountDto.getRoles().forEach(role -> {
        Role r = roleRepository.findByRoleName(role);
        roles.add(r);
      });
      account.setUserRoles(roles);
    }
    account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
    userRepository.save(account);

  }

  @Transactional
  public AccountDto getUser(Long id) {

    Account account = userRepository.findById(id).orElse(new Account());
    ModelMapper modelMapper = new ModelMapper();
    AccountDto accountDto = modelMapper.map(account, AccountDto.class);

    List<String> roles = account.getUserRoles()
        .stream()
        .map(role -> role.getRoleName())
        .collect(Collectors.toList());

    accountDto.setRoles(roles);
    return accountDto;
  }

  @Transactional
  public List<Account> getUsers() {
    return userRepository.findAll();
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Secured("ROLE_MANAGER")
  public void order() {
    System.out.println("order");
  }
}
