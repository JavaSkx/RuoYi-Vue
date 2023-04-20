package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysDevice;
import com.ruoyi.system.domain.SysWater;

import java.util.List;


public interface ISysDeviceService
{
    /**
     * 查询设备信息管理
     *
     * @return 设备信息管理
     */
    public List<SysDevice> selectDeviceInfo(SysDevice sysDevice);

}
