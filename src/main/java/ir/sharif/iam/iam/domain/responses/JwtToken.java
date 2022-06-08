package ir.sharif.iam.iam.domain.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;

@Getter
@AllArgsConstructor
public class JwtToken {
    private final String token;
    private final String tokenType;
}
