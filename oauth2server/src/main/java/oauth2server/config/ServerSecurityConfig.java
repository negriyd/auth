package oauth2server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser("john").password("123").roles("USER");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .usernameParameter("form-username")
                .passwordParameter("form-password")
                .loginProcessingUrl("/login.do").permitAll()
                .loginProcessingUrl("/oauth/authorize").permitAll()
                .and()
                .authorizeRequests().antMatchers("/me").authenticated()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout.do"))
                .and()
                .requestMatchers().antMatchers("/login", "/login.do", "/oauth/authorize", "/oauth/confirm_access")
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .userDetailsService(userDetailsService());
    }
}
