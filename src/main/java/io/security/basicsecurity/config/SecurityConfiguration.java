package io.security.basicsecurity.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.expression.WebExpressionVoter;

//@RequiredArgsConstructor
//@Slf4j
//@Configuration
//@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    private final UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().and() // csrf form._csrf 변수에 저장이 된다
                .authorizeRequests()
                .anyRequest().authenticated()
                .accessDecisionManager(accessDecisionManager())
//                .and()
//
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/ff")
//                .failureUrl("/aa")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginProcessingUrl("/login_process")
//                .successHandler((request, response, authentication) -> {
//                    RequestCache cache = new HttpSessionRequestCache();
//                    SavedRequest savedRequest = cache.getRequest(request, response);
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
//                .and()

//                .sessionManagement()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .expiredUrl("")
//                .and()
//                .sessionFixation().changeSessionId()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
            .sessionManagement()
            .maximumSessions(1).maxSessionsPreventsLogin(true)
            .and()
            .sessionFixation().changeSessionId()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> {
                    response.sendRedirect("/login");
                }))
                .accessDeniedHandler(((request, response, accessDeniedException) -> {

                }))
                .and()
        ;

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

//    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                new WebExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter());
        return new UnanimousBased(decisionVoters);
    }

}
