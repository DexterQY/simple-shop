package per.qy.simple.common.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> {

    /** 总条数 */
    private long total;
    /** 数据列表 */
    private List<T> list;
}
