package com.example.function_module.config;

import com.example.function_module.component.CustomAuthenticationProvider;
import com.example.function_module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {



    @Autowired
    private UserService userService;


    @Autowired
    private CustomAuthenticationProvider authProvider;


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method creates and configures an instance of the AuthenticationManager.
     * The AuthenticationManager is responsible for authenticating users.
     *
     * @param http the HttpSecurity object used to configure the security filter chain
     * @return the configured AuthenticationManager object
     * @throws Exception if there is an error configuring the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        // Get the AuthenticationManagerBuilder from the HttpSecurity object
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Add the custom authentication provider to the AuthenticationManagerBuilder
        authenticationManagerBuilder.authenticationProvider(authProvider);

        // Build and return the AuthenticationManager
        return authenticationManagerBuilder.build();
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity object used to configure the security filter chain
     * @return the configured SecurityFilterChain object
     * @throws Exception if there is an error configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Disable CSRF protection
        http.csrf(AbstractHttpConfigurer::disable);

        // Configure request authorization
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/registration", "/css/**", "/")
                        .permitAll() // Allow all requests to these URLs
                        .anyRequest()
                        .authenticated()// Require authentication for all other requests
        );

        // Configure form login
        http.formLogin((form) -> form
                .loginPage("/login") // Set the login page URL
                .loginProcessingUrl("/login") // Set the URL for processing the login form submission
                .defaultSuccessUrl("/") // Set the default success URL after successful login
                .permitAll() // Allow all requests to the login page
        );

        // Allow all requests to the logout endpoint
        http.logout(LogoutConfigurer::permitAll);


        return http.build();
    }


    /**
     * This method creates and configures an instance of the DaoAuthenticationProvider.
     * The authentication provider is responsible for authenticating users.
     *
     * @return An instance of the AuthenticationProvider configured with the user service and password encoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Create a new instance of the DaoAuthenticationProvider
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();

        // Set the user service for the authentication provider
        auth.setUserDetailsService(userService);

        // Set the password encoder for the authentication provider
        auth.setPasswordEncoder(passwordEncoder());

        // Return the configured authentication provider
        return auth;
    }


}