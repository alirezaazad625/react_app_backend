package ir.sharif.iam.iam.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Role {
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

    private String name;


    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Permission> permissions = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;


    public Role(String name, Set<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public void update(String name, Set<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }
}