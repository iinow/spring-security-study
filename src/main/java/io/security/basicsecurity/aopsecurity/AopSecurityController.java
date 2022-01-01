package io.security.basicsecurity.aopsecurity;

import io.security.basicsecurity.domain.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AopSecurityController {

  private final AopMethodService aopMethodService;

  private final AopPointcutService aopPointcutService;

  @GetMapping("/preAuthorize")
  @PreAuthorize("hasRole('ROLE_USER') and #accountDto.username == principal.username")
  public String preAuthorize(AccountDto accountDto, Model model) {
    model.addAttribute("method", "Success @PreAuthorize");

    return "aop/method";
  }

  @GetMapping("/methodSecured")
  public String methodSecured(Model model) {
    aopMethodService.methodSecured();
    model.addAttribute("method", "Success MethodSecured");

    return "aop/method";
  }

  @GetMapping("/pointcutSecured")
  public String pointcutSecured(Model model) {
    aopPointcutService.notSecured();
    aopPointcutService.pointcutSecured();
    model.addAttribute("pointcut", "Success PointcutSecured");

    return "aop/method";
  }
}
