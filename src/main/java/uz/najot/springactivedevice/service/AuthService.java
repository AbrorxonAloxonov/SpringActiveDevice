package uz.najot.springactivedevice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.najot.springactivedevice.entity.AppUser;
import uz.najot.springactivedevice.entity.Device;
import uz.najot.springactivedevice.entity.RefreshToken;
import uz.najot.springactivedevice.model.ResponseModel;
import uz.najot.springactivedevice.repository.DeviceRepository;
import uz.najot.springactivedevice.repository.RefreshTokenRepository;
import uz.najot.springactivedevice.security.AppSecurityConfig;
import uz.najot.springactivedevice.security.JwtTokenUtil;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppUserService appUserService;
    private final AppSecurityConfig appSecurityConfig;
    private final DeviceRepository deviceRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;
    @Transactional
    public ResponseModel authLogin(String appName, String deviceName, String remoteAdd,String username, String password){
        AppUser appUser = appUserService.loadUserByUsername(username);
        if (appSecurityConfig.passwordEncoder().matches(password,appUser.getPassword())){
            Device device = deviceRepository.save(new Device(appName,remoteAdd,deviceName, appUser));
            RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 2), appUser, device);
            refreshTokenRepository.save(refreshToken);
            return makeModel(username,device.getId(),refreshToken.getToken());
        }else {
            return new ResponseModel(101,"username or password incorrect",null);
        }

    }
    public ResponseModel refreshToken(String oldRefreshToken){
        Optional<RefreshToken> byToken = refreshTokenRepository.findByToken(oldRefreshToken);
        if (byToken.isPresent() && byToken.get().getExpiredDate().getTime()>=System.currentTimeMillis()){
            RefreshToken refreshToken = byToken.get();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshTokenRepository.save(refreshToken);
            return makeModel(refreshToken.getAppUser().getUsername(),refreshToken.getDevice().getId(),refreshToken.getToken());
        }else {
            return new ResponseModel(102,"token expired or not found",null);
        }
    }
    public ResponseModel makeModel(String username, Integer deviceId, String refreshToken){
        String accessToken = jwtTokenUtil.generator(username, deviceId);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("type","Bearer");
        map.put("expiredAt",60000);
        map.put("refreshToken",refreshToken);
        return ResponseModel.getSuccess(map);
    }
}
