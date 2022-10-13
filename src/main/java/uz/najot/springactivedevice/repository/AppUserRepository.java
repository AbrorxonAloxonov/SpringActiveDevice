package uz.najot.springactivedevice.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.najot.springactivedevice.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByUsernameAndIsActive(String username,boolean isActive);
}
