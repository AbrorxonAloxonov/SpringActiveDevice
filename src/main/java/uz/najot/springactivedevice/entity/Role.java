package uz.najot.springactivedevice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role  extends BasicEntity{
    private String roleName;
    @ManyToMany
    private Set<Permission> permissions;

    public Set<Permission> getPermissions(){
        permissions.add(new Permission(roleName));
        return permissions;
    }
}
