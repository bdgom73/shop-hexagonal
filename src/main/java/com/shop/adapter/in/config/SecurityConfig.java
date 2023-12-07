package com.shop.adapter.in.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .formLogin((formLogin) ->
                    formLogin
                            .loginPage("/members/login")
                            .defaultSuccessUrl("/")
                            .usernameParameter("email")
                            .failureUrl("/members/login/error")
                    )
                .logout((logoutConfig) ->
                    logoutConfig
                            .logoutSuccessUrl("/")
                            .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                    )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                                .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                                .requestMatchers("/test/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                .anyRequest().authenticated()
                        )
                .exceptionHandling((exceptionConfig) ->
                                exceptionConfig.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        )
                .csrf((csrf) ->
                        csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}