package ir.sharif.iam.iam.domain.responses;

public record UserDTO(String username,
                      String firstName,
                      String lastName,
                      String roleName,
                      Integer roleId,
                      String birthDay){
}
