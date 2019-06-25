package hu.ponte.upvote.config;

import hu.ponte.upvote.security.LogoutSuccessHandler;
import hu.ponte.upvote.security.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()

                .antMatchers("/api/accounts/**").permitAll()

                .antMatchers(HttpMethod.POST, "/api/ideas").hasRole(USER)
                .antMatchers(HttpMethod.GET, "/api/ideas/allApprovedIdeas").hasRole(USER)
                .antMatchers(HttpMethod.GET, "/api/ideas/allApprovedIdeasByAccount").hasRole(USER)
                .antMatchers(HttpMethod.GET, "/api/ideas/allApprovedIdeasExceptAccount").hasRole(USER)
                .antMatchers(HttpMethod.POST, "/api/ideas/{id}/addVote").hasRole(USER)

                .antMatchers(HttpMethod.GET, "/api/ideas/allApprovedIdeasForAdmin").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/api/ideas/allUnApprovedIdeasForAdmin").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, "/api/ideas/{id}/approve").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/api/ideas/{id}").hasRole(ADMIN)

                .anyRequest().authenticated()
                .and().logout().logoutSuccessHandler(new LogoutSuccessHandler()).deleteCookies("JSESSIONID")
                .and().httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://127.0.0.1:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
