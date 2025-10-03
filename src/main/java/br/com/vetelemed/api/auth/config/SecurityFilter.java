package br.com.vetelemed.api.auth.config;

import br.com.vetelemed.api.auth.repository.UserRepository;
import br.com.vetelemed.api.auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

         final String authorization = request.getHeader("Authorization");

         String jwt = null;
         String username = null;

         if (authorization != null && authorization.startsWith("Bearer ")){
             jwt = authorization.substring(7);
             username = tokenService.getSubject(jwt);
         }

         if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

             UserDetails user = userRepository.findByLogin(username);

             if (!tokenService.verifyToken(jwt, user.getUsername()))
                 throw new RuntimeException("Token inválido ou expirado");

             UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
             SecurityContextHolder.getContext().setAuthentication(authentication);

             }
         filterChain.doFilter(request, response);
         }
    }