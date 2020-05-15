package per.qy.simple.cloud.common.base.model;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> {

    @ApiModelProperty("总条数")
    private long total;
    @ApiModelProperty("数据列表")
    private List<T> list;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> PageVo<T> page(List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new PageVo<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
