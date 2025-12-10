package paymentservice.user.command.repository;

import com.valetparker.chagok.user.command.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends UserRepository, JpaRepository<User, Long> {

}
