package dev.juunihana.adelaide.adelaide_api.configuration;

import dev.juunihana.adelaide.adelaide_api.service.JwtService;
import dev.juunihana.adelaide.adelaide_api.service.UserAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
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
    if (request.getCookies() == null) {
      filterChain.doFilter(request, response);
      return;
    }
    Cookie tokenCookie = Arrays.stream(request.getCookies())
        .filter(cookie -> cookie.getName().equals("auth"))
        .findFirst().orElse(null);

    if (Objects.isNull(tokenCookie)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = tokenCookie.getValue();

    String username = jwtService.getUsername(token);
    if (StringUtils.hasLength(username)
        && SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
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
