package io.security.basicsecurity.security.service;

import io.security.basicsecurity.repository.UserRepository;
import java.util.List;
import java.util.Optional;
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
    var account = Optional.ofNullable(userRepository.findOneByUsername(username))
        .orElseThrow(() -> new UsernameNotFoundException("UsernameNotFoundException"));

    var accountContext = new AccountContext(account, List.of(new SimpleGrantedAuthority(account.getRole())));
    return accountContext;
  }
}
