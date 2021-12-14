package io.security.basicsecurity.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDto {

  private String username;
  private String password;
  private String email;
  private String age;
  private String role;
}
