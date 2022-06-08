package ir.sharif.iam.iam.domain.requests;


import lombok.*;

@AllArgsConstructor
@Setter
@Getter
public class CreateUserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Integer roleId;
    private String birthDay;
}
