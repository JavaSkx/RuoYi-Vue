package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysDevice;
import com.ruoyi.system.service.ISysDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: Josen
 * @Date: 2023/4/20 13:50
 * @Version: 1.0
 */
@RestController
@RequestMapping("/system/device")
public class SysDeviceController extends BaseController {

    @Autowired
    private ISysDeviceService deviceService;

    /**
     * 查询水质信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:device:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDevice sysDevice)
    {
        startPage();
        return getDataTable(deviceService.selectDeviceInfo(sysDevice));
    }







}
