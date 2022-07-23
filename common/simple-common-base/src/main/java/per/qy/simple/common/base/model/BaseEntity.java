package per.qy.simple.common.base.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实体基类
 *
 * @author : QY
 * @date : 2022/5/26
 */
@Data
public class BaseEntity {

    /** id */
    private Long id;
    /** 创建人 */
    private Long createBy;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新人 */
    private Long updateBy;
    /** 更新时间 */
    private LocalDateTime updateTime;
}
