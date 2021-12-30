package io.security.basicsecurity.controller.user;

import io.security.basicsecurity.domain.dto.AccountDto;
import io.security.basicsecurity.domain.entity.Account;
import io.security.basicsecurity.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  @GetMapping(value="/mypage")
  public String myPage() {

    userService.order();

    return "user/mypage";
  }

  @GetMapping("/users")
  public String createUser() {
    return "user/login/register";
  }

  @PostMapping("/users")
  public String createUser(AccountDto accountDto) {
    accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
    userService.createUser(new Account(accountDto));
    return "redirect:/";
  }
}
