package ir.sharif.iam.iam.app;

import ir.sharif.iam.iam.app.ports.RolesRepository;
import ir.sharif.iam.iam.app.ports.UsersRepository;
import ir.sharif.iam.iam.domain.exceptions.BadCredentialException;
import ir.sharif.iam.iam.domain.models.*;
import ir.sharif.iam.iam.domain.requests.*;
import ir.sharif.iam.iam.domain.responses.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IAMHandler {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public void handle(CreateUserRequest request) {
        if (Boolean.TRUE.equals(usersRepository.findById(request.getUsername()).isPresent())) {
            throw new DataIntegrityViolationException("user exists");
        } else {
            if (request.getRoleId() != null && request.getRoleId() != 0)
                this.rolesRepository.findById(request.getRoleId()).orElseThrow();
            var user = new User(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getUsername(),
                    this.encoder.encode(request.getPassword()),
                    request.getRoleId(),
                    request.getBirthDay()
            );
            this.usersRepository.save(user);
        }
    }

    public AuthenticatedUser handle(AuthenticateUserCommand command) {
        var username = command.getUsername();
        var user = this.usersRepository
                .findById(username)
                .orElseThrow(BadCredentialException::new);
        Set<Permission> permissions = Set.of();
        if (user.getRoleId() != null && user.getRoleId() != 0)
            permissions = this.rolesRepository.findById(user.getRoleId()).orElseThrow().getPermissions();
        return user.authenticate(encoder, command.getPassword(), permissions);
    }


    public void handle(UpdateUserInfo request) {
        if (request.getRoleId() != null && request.getRoleId() != 0)
            this.rolesRepository.findById(request.getRoleId()).orElseThrow();
        var user = this.usersRepository.findById(request.getUsername()).orElseThrow();
        user.update(request, this.encoder, request.getRoleId());
        this.usersRepository.save(user);
    }


    public void handle(DeleteUserRequest request) {
        this.usersRepository.deleteById(request.getUsername());
    }

    public List<UserDTO> handle(GetUsersRequest ignored) {
        var rolesMap = this.rolesRepository.findAll().stream().collect(Collectors.toMap(Role::getId, role -> role));
        return this.usersRepository.findAll(Sort.by("username")).stream()
                .map(user -> new UserDTO(
                                user.getUsername(),
                                user.getFirstName(),
                                user.getLastName(),
                                rolesMap.get(user.getRoleId()) != null ? rolesMap.get(user.getRoleId()).getName() : null,
                                rolesMap.get(user.getRoleId()) != null ? rolesMap.get(user.getRoleId()).getId() : null,
                                user.getBirthDay()
                        )
                ).toList();
    }

    public UserDTO handle(GetUserRequest request) {
        var user = this.usersRepository.findById(request.getUsername()).orElseThrow();
        Role role = null;
        if (user.getRoleId() != null && user.getRoleId() != 0)
            role = rolesRepository.getById(user.getRoleId());
        return new UserDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                role != null ? role.getName() : null,
                role != null ? role.getId() : null,
                user.getBirthDay()
        );
    }

    public void handle(CreateRoleRequest request) {
        this.rolesRepository.save(new Role(request.getName(), request.getPermissions()));
    }

    public void handle(UpdateRoleInfo request) {
        var role = rolesRepository.findById(request.getId()).orElseThrow();
        role.update(request.getName(), request.getPermissions());
        this.rolesRepository.save(role);
    }

    public void handle(DeleteRoleRequest request) {
        rolesRepository.deleteById(request.getId());
    }

    public List<Role> handle(GetRolesRequest request) {
        return rolesRepository.findAll(Sort.by(Role_.NAME));
    }

    public Role handle(GetRoleRequest request) {
        return rolesRepository.findById(request.getId()).orElseThrow();
    }
}
