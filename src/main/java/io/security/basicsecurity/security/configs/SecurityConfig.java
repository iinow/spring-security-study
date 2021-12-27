package io.security.basicsecurity.security.configs;

import io.security.basicsecurity.security.common.FormAuthenticationDetailsSource;
import io.security.basicsecurity.security.handler.CustomAccessDeniedHandler;
import io.security.basicsecurity.security.handler.CustomAuthenticationFailureHandler;
import io.security.basicsecurity.security.handler.CustomAuthenticationSuccessHandler;
import io.security.basicsecurity.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import io.security.basicsecurity.security.provider.CustomAuthenticationProvider;
import io.security.basicsecurity.security.service.CustomUserDetailsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Order(1)
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomUserDetailsService customUserDetailsService;

  private final FormAuthenticationDetailsSource formAuthenticationDetailsSource;

  private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

  private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(customUserDetailsService); provider
    auth.authenticationProvider(customAuthenticationProvider());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
//        .antMatchers("/", "/users", "/login*", "user/login/**").permitAll()
//        .antMatchers("/mypage").hasRole("USER")
//        .antMatchers("/messages").hasRole("MANAGER")
//        .antMatchers("/config").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login_proc")
        .defaultSuccessUrl("/")
        .authenticationDetailsSource(formAuthenticationDetailsSource)
        .successHandler(customAuthenticationSuccessHandler)
        .failureHandler(customAuthenticationFailureHandler)
        .permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedHandler(customAccessDeniedHandler())
        .and()
        .addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
        ;
  }

  @Bean
  public CustomAccessDeniedHandler customAccessDeniedHandler() {
    return new CustomAccessDeniedHandler("/denied");
  }

  @Bean
  public CustomAuthenticationProvider customAuthenticationProvider() {
    return new CustomAuthenticationProvider(customUserDetailsService, passwordEncoder());
  }

  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
    FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
    interceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSource());
    interceptor.setAuthenticationManager(authenticationManagerBean());
    interceptor.setAccessDecisionManager(affAccessDecisionManager());
    return interceptor;
  }

  private AccessDecisionManager affAccessDecisionManager() {
    return new AffirmativeBased(getAccessDecisionVoter());
  }

  private List<AccessDecisionVoter<?>> getAccessDecisionVoter() {
    return List.of(new RoleVoter());
  }

  @Bean
  public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
    return new UrlFilterInvocationSecurityMetadataSource();
  }
}
