package ir.sharif.iam.iam.domain.models;


import ir.sharif.iam.iam.domain.exceptions.BadCredentialException;
import ir.sharif.iam.iam.domain.requests.UpdateUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "users")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt = new Date();

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt = new Date();

    @PreUpdate
    protected void updateNow() {
        this.updatedAt = new Date();
    }

    @Id
    private String username;

    private String passwordHash;

    private Integer roleId;

    private String firstName;

    private String lastName;

    private String birthDay;

    public User(String firstName, String lastName, String username, String password, Integer roleId, String birthDay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = password;
        this.roleId = roleId;
        this.birthDay = birthDay;
    }

    public AuthenticatedUser authenticate(PasswordEncoder encoder, String rawPassword, Set<Permission> permissions) {
        if (!encoder.matches(rawPassword, passwordHash))
            throw new BadCredentialException();
        return new AuthenticatedUser(this.username, permissions);
    }


    public void update(UpdateUserInfo request, PasswordEncoder encoder, Integer roleId) {
        if (request.getPassword() != null && !request.getPassword().equals(""))
            this.passwordHash = encoder.encode(request.getPassword());
        this.roleId = roleId;
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.birthDay = request.getBirthDay();
    }
}
