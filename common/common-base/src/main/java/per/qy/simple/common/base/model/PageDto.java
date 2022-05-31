package per.qy.simple.common.base.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 分页请求
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Data
public class PageDto {

    /** 页码，从1开始，默认1 */
    @Min(1)
    private int pageIndex = 1;
    /** 每页数量，默认20，最大200 */
    @Min(1)
    @Max(200)
    private int pageSize = 20;
}
