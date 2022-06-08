package ir.sharif.iam.iam.domain.requests;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOAuthTokenRequest {
    private String username;
    private String password;
}
