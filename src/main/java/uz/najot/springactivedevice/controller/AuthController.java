package uz.najot.springactivedevice.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.najot.springactivedevice.entity.Device;
import uz.najot.springactivedevice.model.ResponseModel;
import uz.najot.springactivedevice.security.JwtTokenUtil;
import uz.najot.springactivedevice.service.AuthService;
import uz.najot.springactivedevice.service.DeviceService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final DeviceService deviceService;

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<?> getToken(HttpServletRequest httpRequest, @RequestHeader String deviceName, @RequestHeader String appName,
                                      @RequestParam String username, @RequestParam String password
    ) {
        ResponseModel responseModel = authService.authLogin(appName, deviceName, httpRequest.getRemoteAddr(), username, password);
        return ResponseEntity.ok(responseModel);
    }
    @RequestMapping(value = "/auth/refresh", method = RequestMethod.POST)
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken){
        ResponseModel responseModel = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(responseModel);
    }
    @RequestMapping(value = "/auth/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestHeader String token ,@RequestHeader Integer deviceId){
        Claims claims = jwtTokenUtil.getUsernameAndDevice(token);
        return deviceService.deleteDevice(deviceId, claims.getSubject());
    }
    @RequestMapping(value = "/auth/getDevice",method = RequestMethod.GET)
    public ResponseModel getAllDevice(){
        return deviceService.getAllDevice();
    }
}
