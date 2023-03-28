package com.ruoyi.system.domain;

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
    private Long Do;

    /** 高猛酸盐指数 */
    @Excel(name = "高猛酸盐指数")
    private Long PV;

    /** 化学需氧量 */
    @Excel(name = "化学需氧量")
    private Long COD;

    /** 五日生化需氧量 */
    @Excel(name = "五日生化需氧量")
    private Long BOD;

    /** 氨氮 */
    @Excel(name = "氨氮")
    private Long NHN;

    /** 总磷 */
    @Excel(name = "总磷")
    private Long P;

    /** 总氮 */
    @Excel(name = "总氮")
    private Long N;

    /** 铜 */
    @Excel(name = "铜")
    private Long cu;

    /** 锌 */
    @Excel(name = "锌")
    private Long zn;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setWaterId(Long waterId) 
    {
        this.waterId = waterId;
    }

    public Long getWaterId() 
    {
        return waterId;
    }
    public void setWaterName(String waterName) 
    {
        this.waterName = waterName;
    }

    public String getWaterName() 
    {
        return waterName;
    }
    public void setPh(Long ph) 
    {
        this.ph = ph;
    }

    public Long getPh() 
    {
        return ph;
    }
    public void setDo(Long Do) 
    {
        this.Do = Do;
    }

    public Long getDo() 
    {
        return Do;
    }
    public void setPV(Long PV) 
    {
        this.PV = PV;
    }

    public Long getPV() 
    {
        return PV;
    }
    public void setCOD(Long COD) 
    {
        this.COD = COD;
    }

    public Long getCOD() 
    {
        return COD;
    }
    public void setBOD(Long BOD) 
    {
        this.BOD = BOD;
    }

    public Long getBOD() 
    {
        return BOD;
    }
    public void setNHN(Long NHN) 
    {
        this.NHN = NHN;
    }

    public Long getNHN() 
    {
        return NHN;
    }
    public void setP(Long P) 
    {
        this.P = P;
    }

    public Long getP() 
    {
        return P;
    }
    public void setN(Long N) 
    {
        this.N = N;
    }

    public Long getN() 
    {
        return N;
    }
    public void setCu(Long cu) 
    {
        this.cu = cu;
    }

    public Long getCu() 
    {
        return cu;
    }
    public void setZn(Long zn) 
    {
        this.zn = zn;
    }

    public Long getZn() 
    {
        return zn;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("waterId", getWaterId())
            .append("waterName", getWaterName())
            .append("ph", getPh())
            .append("Do", getDo())
            .append("PV", getPV())
            .append("COD", getCOD())
            .append("BOD", getBOD())
            .append("NHN", getNHN())
            .append("P", getP())
            .append("N", getN())
            .append("cu", getCu())
            .append("zn", getZn())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
