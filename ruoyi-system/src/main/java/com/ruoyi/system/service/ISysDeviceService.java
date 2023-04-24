package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysDevice;
import com.ruoyi.system.domain.vo.SysDeviceAddVo;
import com.ruoyi.system.domain.vo.SysDeviceUpdateVo;

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
    public int insertSysDevice(SysDeviceAddVo sysDevice);

    /**
     * 更新设备信息
     *
     */
    public int updateSysDevice(SysDeviceUpdateVo sysDeviceUpdateVo);

    /**
     * 删除设备
     */
    public int deleteSysDeviceByDeviceId(Long id);

}
