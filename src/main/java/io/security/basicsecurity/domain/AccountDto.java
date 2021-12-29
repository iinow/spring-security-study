package io.security.basicsecurity.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDto {

  private String username;
  private String password;
  private String email;
  private int age;
  private String role;
}
