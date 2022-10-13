package uz.najot.springactivedevice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.najot.springactivedevice.entity.AppUser;
import uz.najot.springactivedevice.model.UserDevice;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenUtil {
    private final String secret = "mySecretKey";
    public String generator(String username, Integer deviceId){
        HashMap<String, Object> map = new HashMap<>();
        return Jwts.builder().setClaims(map)
                .setSubject(username)
                .setId(deviceId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*30))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public Claims getUsernameAndDevice(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
