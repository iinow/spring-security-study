package io.security.basicsecurity.security.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class PermitAllFilter extends FilterSecurityInterceptor {

  private static final String FILTER_APPLIED =
      "__spring_security_filterSecurityInterceptor_filterApplied";

  private boolean observeOncePerRequest = true;

  private final List<RequestMatcher> permitAllRequestMatchers;

  public PermitAllFilter(String... permitAllResources) {
    this.permitAllRequestMatchers =
        Stream.of(permitAllResources).map(AntPathRequestMatcher::new).collect(Collectors.toList());
  }

  @Override
  protected InterceptorStatusToken beforeInvocation(Object object) {
    boolean permiatAll = false;

    var request = ((FilterInvocation) object).getRequest();
    long count = permitAllRequestMatchers.stream()
        .filter(requestMatcher -> requestMatcher.matches(request))
        .count();

    if(count != 0) {
      permiatAll = true;
    }

    if(permiatAll) {
      return null;
    }

    return super.beforeInvocation(object);
  }

  public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
    if (isApplied(filterInvocation) && this.observeOncePerRequest) {
      // filter already applied to this request and user wants us to observe
      // once-per-request handling, so don't re-do security checking
      filterInvocation
          .getChain()
          .doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
      return;
    }
    // first time this request being called, so perform security checking
    if (filterInvocation.getRequest() != null && this.observeOncePerRequest) {
      filterInvocation.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
    }
    InterceptorStatusToken token = beforeInvocation(filterInvocation);
    try {
      filterInvocation
          .getChain()
          .doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
    } finally {
      super.finallyInvocation(token);
    }
    super.afterInvocation(token, null);
  }

  private boolean isApplied(FilterInvocation filterInvocation) {
    return (filterInvocation.getRequest() != null)
        && (filterInvocation.getRequest().getAttribute(FILTER_APPLIED) != null);
  }
}
