package ir.sharif.iam.iam.domain.requests;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteUserRequest {
    private String username;
}
