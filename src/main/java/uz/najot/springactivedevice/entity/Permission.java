package uz.najot.springactivedevice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BasicEntity implements GrantedAuthority {
    private String permission;
    @Override
    public String getAuthority() {
        return permission;
    }
}
