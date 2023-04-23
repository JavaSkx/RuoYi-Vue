package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysDevice;

import java.util.List;


public interface ISysDeviceService
{
    /**
     * 查询设备信息管理
     *
     * @return 设备信息管理集合
     */
    public List<SysDevice> selectDeviceInfo(SysDevice sysDevice);

    /**
     * 新增设备
     *
     * @Param sysDevice 设备信息
     */
    public int insertSysDevice(SysDevice sysDevice);

//    /**
//     * 更新设备信息
//     *
//     */
//    public int updateSysDevice(Long id);
//
//    /**
//     * 删除设备
//     */
//    public int deleteSysDeviceByDeviceId(Long id);

}
