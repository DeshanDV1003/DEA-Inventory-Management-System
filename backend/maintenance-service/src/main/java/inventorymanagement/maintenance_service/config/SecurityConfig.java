/*
 * SecurityConfig
 *
 * Purpose:
 * - Configures basic security settings for Maintenance service.
 * - Disables CSRF protection for REST API testing.
 * - Allows all incoming requests (no authentication required).
 *
 * Note:
 * - This configuration is suitable for development/testing phase.
 * - Can be extended later for JWT-based authentication.
 */

package inventorymanagement.maintenance_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for Postman testing
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow all requests
                );

        return http.build();
    }
}