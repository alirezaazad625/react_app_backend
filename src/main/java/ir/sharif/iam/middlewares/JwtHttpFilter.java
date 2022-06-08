package ir.sharif.iam.middlewares;

import com.auth0.jwt.exceptions.JWTVerificationException;
import ir.sharif.iam.iam.domain.models.AuthenticatedUser;
import ir.sharif.iam.iam.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtHttpFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Autowired
    public JwtHttpFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || header.isEmpty() || !header.substring(0, 7).equalsIgnoreCase("bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String accessToken = header.substring(7);

        AuthenticatedUser authenticatedUser;
        try {
            authenticatedUser = this.jwtService.decodeAccessToken(accessToken);
            Authentication auth = new JwtAuth(authenticatedUser);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (JWTVerificationException e) {
            response.setStatus(401);
            return;
        }

        chain.doFilter(request, response);
    }
}
