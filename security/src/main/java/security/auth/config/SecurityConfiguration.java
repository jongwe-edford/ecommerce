package security.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@RequiredArgsConstructor
public class SecurityConfiguration {
     private final UserDetailsService userDetailsService;

     private final AuthEntryPoint unauthorizedHandler;

     private final AuthTokenFilter authenticationJwtTokenFilter;

     @Bean
     public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
     }

     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
          return authenticationConfiguration.getAuthenticationManager();
     }



     @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          http.cors().and().csrf().disable()
                  .authorizeHttpRequests()
                  .antMatchers("/auth/**","/vendor/**").permitAll()
                  .anyRequest().authenticated()
                  .and()
                  .addFilterBefore(authenticationJwtTokenFilter,UsernamePasswordAuthenticationFilter.class)
                  .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//          http.addFilterAfter(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

          return http.build();
     }

     @Bean
     public WebMvcConfigurer corsConfigurer() {
          return new WebMvcConfigurer() {
               @Override
               public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedMethods("*");
               }
          };
     }
}                       