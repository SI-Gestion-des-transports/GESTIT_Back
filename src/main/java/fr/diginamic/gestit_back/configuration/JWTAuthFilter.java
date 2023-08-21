package fr.diginamic.gestit_back.configuration;

import fr.diginamic.gestit_back.authentication.LoginUser;
import fr.diginamic.gestit_back.entites.Utilisateur;
import fr.diginamic.gestit_back.utils.JWTUtils;
import fr.diginamic.gestit_back.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Configuration
@AllArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private JWTConfig jwtConfig;
    private JWTUtils jwtUtils;
    private HandlerExceptionResolver handlerExceptionResolver;
    private RedisUtils redisUtils;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader(jwtConfig.getName());
        if (StringUtils.hasText(token)) {

            Claims body;
            try {
                body = jwtUtils.parseJWT(token);
            } catch (ExpiredJwtException e) {
                System.out.println(e.getClaims().get("email"));
                System.out.println(token);
                redisUtils.deleteRedisCache((String) e.getClaims().get("email"), token);
                handlerExceptionResolver.resolveException(request, response, null, e);
                return;
            } catch (JwtException e) {
                handlerExceptionResolver.resolveException(request, response, null, e);
                return;
            }
            if (redisUtils.verifyRedisCache((String) body.get("email"), token)) {
                Utilisateur utilisateur = new Utilisateur(body);
                LoginUser loginUser = new LoginUser(utilisateur);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else handlerExceptionResolver.resolveException(request,response,null,new RuntimeException("Token signed out !"));
        }

        filterChain.doFilter(request, response);
    }
}
