package ir.sharif.iam.iam.infra;

import ir.sharif.iam.iam.app.IAMHandler;
import ir.sharif.iam.iam.domain.exceptions.BadCredentialException;
import ir.sharif.iam.iam.domain.models.AuthenticatedUser;
import ir.sharif.iam.iam.domain.models.Permission;
import ir.sharif.iam.iam.domain.models.Role;
import ir.sharif.iam.iam.domain.requests.*;
import ir.sharif.iam.iam.domain.responses.JwtToken;
import ir.sharif.iam.iam.domain.responses.OAuthTokenResponse;
import ir.sharif.iam.iam.domain.responses.UserDTO;
import ir.sharif.iam.iam.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class IAMController {
    private final IAMHandler iamHandler;
    private final JwtService jwt;

    @PostMapping("/login")
    public OAuthTokenResponse token(@RequestBody GetOAuthTokenRequest request, @AuthenticationPrincipal AuthenticatedUser user) throws BadCredentialException {
        AuthenticatedUser authenticatedUser = this.iamHandler.handle(new AuthenticateUserCommand(
                request.getUsername(),
                request.getPassword()
        ));
        JwtToken accessToken = jwt.generateAccessToken(authenticatedUser);
        return new OAuthTokenResponse(accessToken);
    }

    @PostMapping("/users")
    @Secured(Permission.Code.ROLE_CREATE_USER)
    public void createUser(@RequestBody CreateUserRequest request) {
        this.iamHandler.handle(request);
    }

    @Secured(Permission.Code.ROLE_UPDATE_USER)
    @PutMapping("/users/{username}")
    public void updateInfo(@PathVariable String username, @RequestBody UpdateUserInfo request) {
        this.iamHandler.handle(request.withUsername(username));
    }

    @Secured(Permission.Code.ROLE_DELETE_USER)
    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username) {
        this.iamHandler.handle(new DeleteUserRequest(username));
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return this.iamHandler.handle(new GetUsersRequest());
    }

    @GetMapping("/users/{username}")
    public UserDTO getUser(@PathVariable String username) {
        return this.iamHandler.handle(new GetUserRequest(username));
    }

    @PostMapping("/roles")
    @Secured(Permission.Code.ROLE_CREATE_ROLE)
    public void createRole(@RequestBody CreateRoleRequest request) {
        this.iamHandler.handle(request);
    }

    @Secured(Permission.Code.ROLE_CREATE_ROLE)
    @PutMapping("/roles/{id}")
    public void updateRole(@PathVariable Integer id, @RequestBody UpdateRoleInfo request) {
        this.iamHandler.handle(request.withId(id));
    }

    @Secured(Permission.Code.ROLE_CREATE_ROLE)
    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable Integer id) {
        this.iamHandler.handle(new DeleteRoleRequest(id));
    }

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return this.iamHandler.handle(new GetRolesRequest());
    }

    @GetMapping("/roles/{id}")
    public Role getRole(@PathVariable Integer id) {
        return this.iamHandler.handle(new GetRoleRequest(id));
    }

}
