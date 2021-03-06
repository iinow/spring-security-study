package io.security.basicsecurity.security.configs;

import io.security.basicsecurity.security.common.AjaxLoginAuthenticationEntryPoint;
import io.security.basicsecurity.security.handler.AjaxAccessDeniedHandler;
import io.security.basicsecurity.security.handler.AjaxAuthenticationFailureHandler;
import io.security.basicsecurity.security.handler.AjaxAuthenticationSuccessHandler;
import io.security.basicsecurity.security.provider.AjaxAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(0)
@Configuration
public class AjaxSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(ajaxAuthenticationProvider());
  }

  @Bean
  public AjaxAuthenticationProvider ajaxAuthenticationProvider() {
    return new AjaxAuthenticationProvider();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/api/**")
        .authorizeRequests()
        .antMatchers("/api/messages").hasRole("MANAGER")
        .anyRequest().authenticated()
        .and();
//        .addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

    http.exceptionHandling()
        .authenticationEntryPoint(new AjaxLoginAuthenticationEntryPoint())
        .accessDeniedHandler(ajaxAccessDeniedHandler());
    http.csrf().disable();

    customConfigurerAjax(http);
  }

  private void customConfigurerAjax(HttpSecurity http) throws Exception {
    http.apply(new AjaxLoginConfigurer<>())
        .successHandlerAjax(ajaxAuthenticationSuccessHandler())
        .failureHandlerAjax(ajaxAuthenticationFailureHandler())
        .setAuthenticationManager(authenticationManager())
        .loginProcessingUrl("/api/login");
  }

  @Bean
  public AjaxAccessDeniedHandler ajaxAccessDeniedHandler() {
    return new AjaxAccessDeniedHandler();
  }

//  @Bean
//  public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
//    var filter = new AjaxLoginProcessingFilter();
//    filter.setAuthenticationManager(authenticationManagerBean());
//    filter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
//    filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());
//    return filter;
//  }

  @Bean
  public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
    return new AjaxAuthenticationSuccessHandler();
  }

  @Bean
  public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
    return new AjaxAuthenticationFailureHandler();
  }
}
