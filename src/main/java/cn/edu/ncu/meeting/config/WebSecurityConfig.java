package cn.edu.ncu.meeting.config;

import cn.edu.ncu.meeting.security.AuthenticationFilter;
import cn.edu.ncu.meeting.security.AuthenticationProvider;
import cn.edu.ncu.meeting.security.TokenAuthenticationEntryPoint;
import cn.edu.ncu.meeting.user.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This Manage Web Security Config
 * @author lorry
 * @author lin864464995@163.com
 * @see org.springframework.security.config.annotation.web.WebSecurityConfigurer
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationFilter authenticationFilter;

    private final TokenAuthenticationEntryPoint authenticationEntryPoint;

    private final UserService userService;

    public WebSecurityConfig(AuthenticationFilter authenticationFilter,
                             TokenAuthenticationEntryPoint authenticationEntryPoint,
                             UserService userService) {
        this.authenticationFilter = authenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new AuthenticationProvider(userService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
            .authorizeRequests()
                .antMatchers(
                        "/api/user/token", "/api/user/registry",
                        "/api/meeting/get", "/api/meeting/getHot",
                        "/api/meeting/getNewest", "/api/meeting/getStartSoon",
                        "/api/meeting/getHoldUser", "/api/meeting/QRCode").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
