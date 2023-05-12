package dev.juunihana.adelaide.adelaide_api.configuration;

import dev.juunihana.adelaide.adelaide_api.service.JwtService;
import dev.juunihana.adelaide.adelaide_api.service.UserAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final UserAuthService userAuthService;
  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = "";
    String headerAuthorization = request.getHeader("Authorization");
    if (!StringUtils.hasLength(headerAuthorization) &&
        headerAuthorization.startsWith("Bearer ")) {
      token = headerAuthorization.replace("Bearer ", "");
    }

    String username = jwtService.getUsername(token);
    if (StringUtils.hasLength(username)
        && SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal().equals("anonymousUser")) {
      UserDetails userDetails = userAuthService.loadUserByUsername(username);
      if (jwtService.isValid(token, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
