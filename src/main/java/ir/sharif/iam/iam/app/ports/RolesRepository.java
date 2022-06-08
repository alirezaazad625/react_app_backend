package ir.sharif.iam.iam.app.ports;

import ir.sharif.iam.iam.domain.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Integer> {
}
