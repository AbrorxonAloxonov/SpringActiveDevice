package uz.najot.springactivedevice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken extends BasicEntity{
    private String token;
    private Date expiredDate;
    @ManyToOne
    private AppUser appUser;
    @ManyToOne
    private Device device;
}
