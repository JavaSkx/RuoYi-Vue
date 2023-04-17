package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysWaterMapper;
import com.ruoyi.system.domain.SysWater;
import com.ruoyi.system.service.ISysWaterService;

/**
 * 水质信息管理Service业务层处理
 * 
 * @author JavaSkx
 * @date 2023-04-17
 */
@Service
public class SysWaterServiceImpl implements ISysWaterService 
{
    @Autowired
    private SysWaterMapper sysWaterMapper;

    /**
     * 查询水质信息管理
     * 
     * @param waterId 水质信息管理主键
     * @return 水质信息管理
     */
    @Override
    public SysWater selectSysWaterByWaterId(Long waterId)
    {
        return sysWaterMapper.selectSysWaterByWaterId(waterId);
    }

    /**
     * 查询水质信息管理列表
     * 
     * @param sysWater 水质信息管理
     * @return 水质信息管理
     */
    @Override
    public List<SysWater> selectSysWaterList(SysWater sysWater)
    {
        return sysWaterMapper.selectSysWaterList(sysWater);
    }

    /**
     * 新增水质信息管理
     * 
     * @param sysWater 水质信息管理
     * @return 结果
     */
    @Override
    public int insertSysWater(SysWater sysWater)
    {
        sysWater.setCreateTime(DateUtils.getNowDate());
        return sysWaterMapper.insertSysWater(sysWater);
    }

    /**
     * 修改水质信息管理
     * 
     * @param sysWater 水质信息管理
     * @return 结果
     */
    @Override
    public int updateSysWater(SysWater sysWater)
    {
        sysWater.setUpdateTime(DateUtils.getNowDate());
        return sysWaterMapper.updateSysWater(sysWater);
    }

    /**
     * 批量删除水质信息管理
     * 
     * @param waterIds 需要删除的水质信息管理主键
     * @return 结果
     */
    @Override
    public int deleteSysWaterByWaterIds(Long[] waterIds)
    {
        return sysWaterMapper.deleteSysWaterByWaterIds(waterIds);
    }

    /**
     * 删除水质信息管理信息
     * 
     * @param waterId 水质信息管理主键
     * @return 结果
     */
    @Override
    public int deleteSysWaterByWaterId(Long waterId)
    {
        return sysWaterMapper.deleteSysWaterByWaterId(waterId);
    }
}
