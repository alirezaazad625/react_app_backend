package ir.sharif.iam.iam.domain.requests;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateUserInfo {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Integer roleId;
    private String birthDay;

    public UpdateUserInfo withUsername(String username) {
        this.username = username;
        return this;
    }
}
