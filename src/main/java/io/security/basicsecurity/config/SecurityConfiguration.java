package io.security.basicsecurity.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@RequiredArgsConstructor
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()

                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/ff")
//                .failureUrl("/aa")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginProcessingUrl("/login_process")
//                .successHandler((request, response, authentication) -> {
//                    log.debug("authentication, name: {}", authentication.getName());
//                })
//                .failureHandler(((request, response, exception) -> {
//                    log.error("exception, {}", exception.getMessage(), exception);
//                }))
//                .permitAll()
//                .and()
//
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login")
//                .addLogoutHandler(((request, response, authentication) -> {
//                    request.getSession().invalidate();
//                }))
//                .logoutSuccessHandler(((request, response, authentication) -> {
//                    response.sendRedirect("/login");
//                }))
//                .deleteCookies("remember-me")
//                .and()
//
//                .rememberMe()
//                .rememberMeParameter("rememberMe")
//                .tokenValiditySeconds(3600)
//                .userDetailsService(userDetailsService)
//                .alwaysRemember(false)
                .and()

                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("")
                .and()
                .sessionFixation().changeSessionId()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
//                .antMatcher("")
//                .authorizeRequests().antMatchers()
        ;

    }
}
