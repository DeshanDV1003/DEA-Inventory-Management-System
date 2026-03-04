/**
 * SecurityConfig
 *
 * This configuration class manages the security layer of the Purchase Order Service
 * using Spring Security.
 *
 * Purpose:
 * - Defines the security filter chain that intercepts all incoming HTTP requests.
 * - Configures authorization rules for the microservice endpoints.
 * - Manages security features like CSRF (Cross-Site Request Forgery).
 *
 * Variables & Logic:
 * - Currently configured for a "Permit All" strategy to facilitate smooth
 *   development and testing with Postman and Frontend integration.
 *
 * Annotations:
 * - @Configuration: Tells Spring to process this class and generate bean definitions.
 * - @EnableWebSecurity: Enables Spring Security's web security support and provides
 *   the Spring MVC integration.
 * - @Bean: Registers the return value of the method as a bean in the Spring context.
 */

package inventorymanagement.purchase_order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * securityFilterChain
     *
     * Configures the main security filter chain for the application.
     *
     * @param http The HttpSecurity object used to build the security configuration.
     * @return A SecurityFilterChain that Spring Security will use to protect the app.
     * @throws Exception If an error occurs during the security configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection. This is necessary for testing APIs with
                // Postman/React without needing a CSRF token in every request.
                .csrf(csrf -> csrf.disable())

                // Defines the authorization rules for HTTP requests.
                .authorizeHttpRequests(auth -> auth
                        // Currently set to permitAll(), allowing any client to access
                        // any endpoint (GET, POST, etc.) without authentication.
                        .anyRequest().permitAll()
                );

        // Builds and returns the security configuration object
        return http.build();
    }
}