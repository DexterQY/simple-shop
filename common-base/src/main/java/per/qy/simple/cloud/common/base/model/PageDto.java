package per.qy.simple.cloud.common.base.model;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Function: 分页请求模型
 * Description:
 * Author: QY
 * Date: 2019/6/11
 */
@Data
public class PageDto {

    @Min(1)
    @ApiParam(value = "页码，从1开始，默认1")
    private int pageIndex = 1;
    @Min(1)
    @Max(100)
    @ApiParam(value = "每页数量，默认20")
    private int pageSize = 20;
}
