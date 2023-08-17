package fr.diginamic.gestit_back.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Map;

@Configuration
@EnableMethodSecurity(securedEnabled = true)

public class WebSecurityConfiguration {
    private final AuthenticationConfiguration configuration;

    public WebSecurityConfiguration(AuthenticationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTAuthFilter jwtAuthFilter) throws Exception{
        http.authorizeHttpRequests(
                        auth -> auth
                                //.requestMatchers(HttpMethod.POST,"/utilisateur/create").permitAll()
                                .requestMatchers(HttpMethod.POST,"/login").anonymous()
                                //.requestMatchers(mvc.pattern(HttpMethod.POST, "")).permitAll()
                                .anyRequest().permitAll()//.authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable
                        //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        //.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler()::handle)
                )
                .sessionManagement(sm->sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.headers(headers -> headers
                       // .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        String encodingId = "bcrypt";
        return new DelegatingPasswordEncoder(encodingId, Map.of(encodingId, new BCryptPasswordEncoder()));
    }
    @Scope("prototype")
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

}

