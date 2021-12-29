package io.security.basicsecurity.security.service;

import io.security.basicsecurity.domain.entity.Role;
import io.security.basicsecurity.repository.UserRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var account =
        Optional.ofNullable(userRepository.findOneByUsername(username))
            .orElseThrow(() -> new UsernameNotFoundException("UsernameNotFoundException"));

    return new AccountContext(
        account,
        account.getUserRoles().stream()
            .map(Role::getRoleName)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()));
  }
}
