package io.security.basicsecurity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@TableName("account")
@Setter
@Getter
public class Hello {

  @TableId(type = IdType.AUTO)
  private long id;

  private int age;

  private String email;

  private String password;

  private String role;

  private String username;
}
