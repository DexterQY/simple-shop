package per.qy.simple.cloud.common.base.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    private String id;
    @ApiParam(hidden = true)
    @ApiModelProperty("创建人")
    private String createUser;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiParam(hidden = true)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiParam(hidden = true)
    @ApiModelProperty("最后更新时间")
    private LocalDateTime updateTime;
}
