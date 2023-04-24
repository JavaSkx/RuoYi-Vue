package com.ruoyi.system.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysDevice;
import com.ruoyi.system.domain.vo.SysDeviceAddVo;
import com.ruoyi.system.domain.vo.SysDeviceUpdateVo;
import com.ruoyi.system.service.ISysDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 设备信息管理Controller
 * @Author: JavaSkx
 * @Date: 2023/4/20 13:50
 * @Version: 1.0
 */
@RestController
@RequestMapping("/system/device")
public class SysDeviceController extends BaseController {

    @Autowired
    private ISysDeviceService deviceService;

    /**
     * 查询设备信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:device:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDevice sysDevice)
    {
        startPage();
        return getDataTable(deviceService.selectDeviceInfo(sysDevice));
    }

    /**
     * 新增设备
     */
    @PreAuthorize("@ss.hasPermi('system:device:add')")
    @Log(title = "设备管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SysDeviceAddVo sysDevice) {
        return toAjax(deviceService.insertSysDevice(sysDevice));
    }

    /**
     * 修改设备信息
     */
    @PreAuthorize("@ss.hasPermi('system:device:edit')")
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody SysDeviceUpdateVo sysDeviceUpdateVo) {
         return toAjax(deviceService.updateSysDevice(sysDeviceUpdateVo));
    }

    /**
     * 删除设备
     */
    @PreAuthorize("@ss.hasPermi('system:device:remove')")
    @DeleteMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(deviceService.deleteSysDeviceByDeviceId(id));
    }








}
