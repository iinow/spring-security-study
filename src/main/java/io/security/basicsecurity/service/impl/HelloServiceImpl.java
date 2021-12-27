package io.security.basicsecurity.service.impl;

import io.security.basicsecurity.mapper.HelloMapper;
import io.security.basicsecurity.model.Hello;
import io.security.basicsecurity.service.HelloService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HelloServiceImpl implements HelloService {

  private final HelloMapper helloMapper;

  @Override
  public List<Hello> findAll() {
    return helloMapper.selectList(null);
  }
}
