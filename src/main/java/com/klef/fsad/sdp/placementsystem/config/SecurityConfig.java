package com.klef.fsad.sdp.placementsystem.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.Customizer;

import com.klef.fsad.sdp.placementsystem.security.JwtFilter;
import com.klef.fsad.sdp.placementsystem.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfig 
{
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;
    
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
    {
        http
        .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
            		
            		.requestMatchers(org.springframework.http.HttpMethod.GET,
            	            "/student/download-resume/**"
            	    ).permitAll()
            		
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/auth/**",
                    "/student/register",
                    "/employer/register",
                    "/user/**"
                ).permitAll()

               
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/student/update", "/student/viewjobs", "/student/applyjob/**", "/student/viewapplications/**").hasAuthority("STUDENT")
                .requestMatchers("/employer/**").hasAuthority("EMPLOYER")
                .requestMatchers("/officer/**").hasAuthority("PLACEMENT OFFICER")

                .anyRequest().authenticated()
            )
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean 
    public AuthenticationProvider authenticationProvider() 
    { 
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService); 
        provider.setPasswordEncoder(passwordEncoder()); 
        return provider; 
    }

    @Bean
    public PasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

   
    @Bean
    public CorsConfigurationSource corsConfigurationSource() 
    {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
        	    "https://fsad-sdp-frontend-placementsystem.onrender.com",
        	    "https://placementmanagementsystem2007.netlify.app"
        	)); // frontend url
        
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}