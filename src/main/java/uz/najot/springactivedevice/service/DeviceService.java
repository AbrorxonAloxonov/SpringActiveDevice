package uz.najot.springactivedevice.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.najot.springactivedevice.entity.AppUser;
import uz.najot.springactivedevice.entity.Device;
import uz.najot.springactivedevice.model.ResponseModel;
import uz.najot.springactivedevice.repository.DeviceRepository;
import uz.najot.springactivedevice.repository.RefreshTokenRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final AppUserService appUserService;
    private final RefreshTokenRepository refreshTokenRepository;
    @Transactional
    public ResponseEntity<?> deleteDevice(Integer deviceId,String username) {
        AppUser appUser = appUserService.loadUserByUsername(username);
        Optional<Device> device = deviceRepository.findByIdAndAppUserId(deviceId, appUser.getId());
        if (device.isPresent()) {
            refreshTokenRepository.deleteByDeviceId(deviceId);
            deviceRepository.deleteById(deviceId);
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.ok("not deleted");
    }
    public ResponseModel getAllDevice(){
        List<Device> device = deviceRepository.findAll();
        return ResponseModel.getSuccess(device);
    }
}
