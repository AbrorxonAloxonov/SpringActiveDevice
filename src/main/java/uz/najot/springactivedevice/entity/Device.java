package uz.najot.springactivedevice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device extends BasicEntity{
    private String appName;
    private String ipAddress;
    private String deviceName;
    @ManyToOne
    private AppUser appUser;

    public Device(Integer id, String appName, Date createDate,boolean isActive, String ipAddress, String deviceName, Integer appUser) {
        super.setId(id);
        super.setCreatedDate(createDate);
        super.setIsActive(isActive);
        this.appName = appName;
        this.deviceName = deviceName;
        this.ipAddress = ipAddress;
//        this.appUser.setId(appUser);
    }
}
