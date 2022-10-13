package uz.najot.springactivedevice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.najot.springactivedevice.entity.AppUser;
import uz.najot.springactivedevice.entity.Device;
import uz.najot.springactivedevice.repository.AppUserRepository;
import uz.najot.springactivedevice.repository.DeviceRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final DeviceRepository deviceRepository;

    public AppUser loadByUsernameAndDeviceId(String username, Integer deviceId){
        Optional<Device> optionalDevice = deviceRepository.findByAppUserUsernameAndId(username, deviceId);
        return optionalDevice.orElseThrow(()-> new UsernameNotFoundException("user not found")).getAppUser();
    }


    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsernameAndIsActive(username,true).orElseThrow();
    }

}
