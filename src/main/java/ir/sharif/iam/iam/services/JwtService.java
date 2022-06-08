package ir.sharif.iam.iam.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import ir.sharif.iam.iam.domain.models.AuthenticatedUser;
import ir.sharif.iam.iam.domain.models.Permission;
import ir.sharif.iam.iam.domain.responses.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Service
public class JwtService {
    public static final String USERNAME_CLAIM = "username";
    public static final String ROLES_CLAIM = "roles";
    public static final String TOKEN_TYPE_CLAIM = "token_type";


    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.minutes}")
    private int tokenExpiryMinutes;

    @Autowired
    private Algorithm algorithm;


    public JwtToken generateAccessToken(AuthenticatedUser user) {
        Date expiresAt = Date.from(Instant.now().plus(Duration.ofMinutes(this.tokenExpiryMinutes)));
        String token = JWT.create()
                .withIssuer(issuer)
                .withClaim(USERNAME_CLAIM, user.getUsername().toString())
                .withClaim(TOKEN_TYPE_CLAIM, "access_token")
                .withArrayClaim(ROLES_CLAIM, user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new))
                .withExpiresAt(expiresAt)
                .sign(this.algorithm);
        return new JwtToken(token, "Bearer");
    }

    public AuthenticatedUser decodeAccessToken(String accessToken) {
        JWTVerifier verifier = JWT.require(this.algorithm)
                .withIssuer(issuer)
                .withClaim(TOKEN_TYPE_CLAIM, "access_token")
                .build();

        var decodedJWT = verifier.verify(accessToken);
        var username = decodedJWT.getClaim(USERNAME_CLAIM).asString();
        Collection<Permission> permissions = decodedJWT.getClaim(ROLES_CLAIM).asList(String.class).stream()
                .map(Permission::valueOf).toList();

        return new AuthenticatedUser(username, permissions);
    }
}
