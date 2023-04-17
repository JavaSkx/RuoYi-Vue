package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysWater;

/**
 * 水质信息管理Service接口
 * 
 * @author JavaSkx
 * @date 2023-04-17
 */
public interface ISysWaterService 
{
    /**
     * 查询水质信息管理
     * 
     * @param waterId 水质信息管理主键
     * @return 水质信息管理
     */
    public SysWater selectSysWaterByWaterId(Long waterId);

    /**
     * 查询水质信息管理列表
     * 
     * @param sysWater 水质信息管理
     * @return 水质信息管理集合
     */
    public List<SysWater> selectSysWaterList(SysWater sysWater);

    /**
     * 新增水质信息管理
     * 
     * @param sysWater 水质信息管理
     * @return 结果
     */
    public int insertSysWater(SysWater sysWater);

    /**
     * 修改水质信息管理
     * 
     * @param sysWater 水质信息管理
     * @return 结果
     */
    public int updateSysWater(SysWater sysWater);

    /**
     * 批量删除水质信息管理
     * 
     * @param waterIds 需要删除的水质信息管理主键集合
     * @return 结果
     */
    public int deleteSysWaterByWaterIds(Long[] waterIds);

    /**
     * 删除水质信息管理信息
     * 
     * @param waterId 水质信息管理主键
     * @return 结果
     */
    public int deleteSysWaterByWaterId(Long waterId);
}
