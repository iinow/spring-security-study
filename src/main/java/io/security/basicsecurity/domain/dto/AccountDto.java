package io.security.basicsecurity.domain.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDto {

  private String id;
  private String username;
  private String email;
  private int age;
  private String password;
  private List<String> roles;
}
