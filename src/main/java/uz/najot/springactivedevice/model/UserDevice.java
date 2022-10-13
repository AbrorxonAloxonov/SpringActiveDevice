package uz.najot.springactivedevice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDevice {
    private String username;
    private Integer deviceId;
}
