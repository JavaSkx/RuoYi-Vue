package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 水质信息管理对象 sys_water
 *
 * @author JavaSkx
 * @date 2023-04-17
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
    private Float ph;

    /** 溶解氧 */
    @Excel(name = "溶解氧")
    private Float ddo;

    /** 高猛酸盐指数 */
    @Excel(name = "高猛酸盐指数")
    private Float pv;

    /** 化学需氧量 */
    @Excel(name = "化学需氧量")
    private Float cod;

    /** 五日生化需氧量 */
    @Excel(name = "五日生化需氧量")
    private Float bod;

    /** 氨氮 */
    @Excel(name = "氨氮")
    private Float nhn;

    /** 总磷 */
    @Excel(name = "总磷")
    private Float p;

    /** 总氮 */
    @Excel(name = "总氮")
    private Float n;

    /** 铜 */
    @Excel(name = "铜")
    private Float cu;

    /** 锌 */
    @Excel(name = "锌")
    private Float zn;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 水质等级 */
    @Excel(name = "水质等级")
    private String rk;

//    public void setWaterId(Long waterId)
//    {
//        this.waterId = waterId;
//    }
//
//    public Long getWaterId()
//    {
//        return waterId;
//    }
//    public void setWaterName(String waterName)
//    {
//        this.waterName = waterName;
//    }
//
//    public String getWaterName()
//    {
//        return waterName;
//    }
//    public void setPh(Long ph)
//    {
//        this.ph = ph;
//    }
//
//    public Long getPh()
//    {
//        return ph;
//    }
//    public void setDdo(Long ddo)
//    {
//        this.ddo = ddo;
//    }
//
//    public Long getDdo()
//    {
//        return ddo;
//    }
//    public void setPv(Long pv)
//    {
//        this.pv = pv;
//    }
//
//    public Long getPv()
//    {
//        return pv;
//    }
//    public void setCod(Long cod)
//    {
//        this.cod = cod;
//    }
//
//    public Long getCod()
//    {
//        return cod;
//    }
//    public void setBod(Long bod)
//    {
//        this.bod = bod;
//    }
//
//    public Long getBod()
//    {
//        return bod;
//    }
//    public void setNhn(Long nhn)
//    {
//        this.nhn = nhn;
//    }
//
//    public Long getNhn()
//    {
//        return nhn;
//    }
//    public void setP(Long p)
//    {
//        this.p = p;
//    }
//
//    public Long getP()
//    {
//        return p;
//    }
//    public void setN(Long n)
//    {
//        this.n = n;
//    }
//
//    public Long getN()
//    {
//        return n;
//    }
//    public void setCu(Long cu)
//    {
//        this.cu = cu;
//    }
//
//    public Long getCu()
//    {
//        return cu;
//    }
//    public void setZn(Long zn)
//    {
//        this.zn = zn;
//    }
//
//    public Long getZn()
//    {
//        return zn;
//    }
//    public void setDelFlag(String delFlag)
//    {
//        this.delFlag = delFlag;
//    }
//
//    public String getDelFlag()
//    {
//        return delFlag;
//    }
//    public void setRk(String rk)
//    {
//        this.rk = rk;
//    }
//
//    public String getRk()
//    {
//        return rk;
//    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("waterId", getWaterId())
                .append("waterName", getWaterName())
                .append("ph", getPh())
                .append("ddo", getDdo())
                .append("pv", getPv())
                .append("cod", getCod())
                .append("bod", getBod())
                .append("nhn", getNhn())
                .append("p", getP())
                .append("n", getN())
                .append("cu", getCu())
                .append("zn", getZn())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("rk", getRk())
                .toString();
    }
}
