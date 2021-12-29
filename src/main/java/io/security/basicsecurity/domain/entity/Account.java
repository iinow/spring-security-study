package io.security.basicsecurity.domain.entity;

import io.security.basicsecurity.domain.AccountDto;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Account {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String username;

  @Column
  private String email;

  @Column
  private int age;

  @Column
  private String password;

  @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL})
  @JoinTable(name = "account_roles", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
      @JoinColumn(name = "role_id") })
  private Set<Role> userRoles = new HashSet<>();

  public Account(AccountDto accountDto) {
    this.username = accountDto.getUsername();
    this.password = accountDto.getPassword();
    this.email = accountDto.getEmail();
    this.age = accountDto.getAge();
//    this.role = accountDto.getRole();
  }
}
