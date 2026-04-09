package sajeevan.task_track.Config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sajeevan.task_track.Service.JWTService;
import sajeevan.task_track.Service.MyUserDetailsService;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String name = null;
        Long userId = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) { // Check the token startes with Bearer
            token = authHeader.substring(7);
            name = jwtService.extractUserName(token); // getting name from the token
            userId = jwtService.extractUserId(token);
        }

        if (name != null && SecurityContextHolder.getContext().getAuthentication() == null) { // Checks the username is
                                                                                              // not empty and checks it
                                                                                              // is already
                                                                                              // authenticated
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(name);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }

}
