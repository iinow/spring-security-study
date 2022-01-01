package io.security.basicsecurity.aopsecurity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AopMethodService {

  public void methodSecured() {
    log.info("method secured");
  }
}
