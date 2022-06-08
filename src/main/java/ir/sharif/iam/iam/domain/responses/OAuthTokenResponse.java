package ir.sharif.iam.iam.domain.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class OAuthTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;

    public OAuthTokenResponse(JwtToken accessToken) {
        this.accessToken = accessToken.getToken();
        this.tokenType = accessToken.getTokenType();
    }
}
