package uz.najot.springactivedevice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.najot.springactivedevice.entity.Device;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device,Integer> {
    Optional<Device> findByAppUserUsernameAndId(String username,Integer deviceId);
    Optional<Device> findByIdAndAppUserId(Integer deviceId,Integer app_user_id);
}
