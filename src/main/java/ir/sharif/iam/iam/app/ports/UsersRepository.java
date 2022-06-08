package ir.sharif.iam.iam.app.ports;

import ir.sharif.iam.iam.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<String> {
}
