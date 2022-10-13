package uz.najot.springactivedevice.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.najot.springactivedevice.entity.AppUser;
import uz.najot.springactivedevice.service.AppUserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final AppUserService appUserService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token!=null && token.startsWith("Bearer ")){
            token = token.substring(7);
            Claims claims = jwtTokenUtil.getUsernameAndDevice(token);
            if (claims.getSubject()!=null && claims.getExpiration().getTime()>=System.currentTimeMillis()
                    && claims.getId()!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                AppUser appUser = appUserService.loadByUsernameAndDeviceId(claims.getSubject(), Integer.parseInt(claims.getId()));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword(), null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
    filterChain.doFilter(request,response);
    }

}
