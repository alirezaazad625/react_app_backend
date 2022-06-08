package ir.sharif.iam.iam.domain.requests;


import ir.sharif.iam.iam.domain.models.Permission;
import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public class CreateRoleRequest {
    private String name;
    private Set<Permission> permissions;
}
