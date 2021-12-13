package io.security.basicsecurity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Student {

  @Id
  private Long id;

  @Column
  private String name;


  @Column(columnDefinition = "text[]")
  @Type(type = "io.security.basicsecurity.CustomStringArrayType")
  private List<String> levels;
}
