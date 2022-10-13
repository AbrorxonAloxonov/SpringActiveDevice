package uz.najot.springactivedevice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.najot.springactivedevice.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByDeviceId(Integer id);
}
