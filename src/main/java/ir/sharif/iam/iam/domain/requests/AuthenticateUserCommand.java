package ir.sharif.iam.iam.domain.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class AuthenticateUserCommand {
    private String username;
    private String password;
}
