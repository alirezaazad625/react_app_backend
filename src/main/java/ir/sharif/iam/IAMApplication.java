package ir.sharif.iam;


import ir.sharif.iam.iam.app.ports.RolesRepository;
import ir.sharif.iam.iam.app.ports.UsersRepository;
import ir.sharif.iam.iam.domain.models.Permission;
import ir.sharif.iam.iam.domain.models.Role;
import ir.sharif.iam.iam.domain.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;


@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableAsync
@EnableScheduling
public class IAMApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(IAMApplication.class, args);
        var usersRepository = context.getBean(UsersRepository.class);
        var rolesRepository = context.getBean(RolesRepository.class);
        if (!usersRepository.existsById("admin")) {
            var role = rolesRepository.save(new Role("admin",
                    Set.of(
                            Permission.ROLE_CREATE_ROLE,
                            Permission.ROLE_CREATE_USER,
                            Permission.ROLE_DELETE_USER,
                            Permission.ROLE_UPDATE_USER
                    )
            ));
            usersRepository.save(new User(null, null, "admin", context.getBean(PasswordEncoder.class).encode("12345678"), role.getId(), null));
        }
    }
}

