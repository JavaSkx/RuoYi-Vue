package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 水质信息查询对象 sys_water
 *
 * @author JavaSkx
 * @date 2023-03-28
 */
@Data
public class SysWater extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 水域ID */
    private Long waterId;

    /** 河流名称 */
    @Excel(name = "河流名称")
    private String waterName;

    /** PH值 */
    @Excel(name = "PH值")
    private Long ph;

    /** 溶解氧 */
    @Excel(name = "溶解氧")
    private Long ddo;

    /** 高猛酸盐指数 */
    @Excel(name = "高猛酸盐指数")
    private Long pv;

    /** 化学需氧量 */
    @Excel(name = "化学需氧量")
    private Long cod;

    /** 五日生化需氧量 */
    @Excel(name = "五日生化需氧量")
    private Long bod;

    /** 氨氮 */
    @Excel(name = "氨氮")
    private Long nhn;

    /** 总磷 */
    @Excel(name = "总磷")
    private Long p;

    /** 总氮 */
    @Excel(name = "总氮")
    private Long n;

    /** 铜 */
    @Excel(name = "铜")
    private Long cu;

    /** 锌 */
    @Excel(name = "锌")
    private Long zn;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
