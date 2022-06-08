package ir.sharif.iam.iam.domain.requests;

import ir.sharif.iam.iam.domain.models.Permission;
import lombok.*;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateRoleInfo {
    private Integer id;
    private String name;
    private Set<Permission> permissions;

    public UpdateRoleInfo withId(Integer id) {
        this.id = id;
        return this;
    }
}
