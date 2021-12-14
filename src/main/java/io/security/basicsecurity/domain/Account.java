package io.security.basicsecurity.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Account {

  @GeneratedValue
  @Id
  private Long id;

  private String username;
  private String password;
  private String email;
  private String age;
  private String role;

  public Account(AccountDto accountDto) {
    this.username = accountDto.getUsername();
    this.password = accountDto.getPassword();
    this.email = accountDto.getEmail();
    this.age = accountDto.getAge();
    this.role = accountDto.getRole();
  }
}
