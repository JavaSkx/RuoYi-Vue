package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysWater;
import com.ruoyi.system.service.ISysWaterService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 水质信息管理Controller
 *
 * @author JavaSkx
 * @date 2023-04-17
 */
@RestController
@RequestMapping("/system/water")
public class SysWaterController extends BaseController
{
    @Autowired
    private ISysWaterService sysWaterService;

    /**
     * 查询水质信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:water:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWater sysWater)
    {
        startPage();
        List<SysWater> list = sysWaterService.selectSysWaterList(sysWater);
        return getDataTable(list);
    }

    /**
     * 导出水质信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:water:export')")
    @Log(title = "水质信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysWater sysWater)
    {
        List<SysWater> list = sysWaterService.selectSysWaterList(sysWater);
        ExcelUtil<SysWater> util = new ExcelUtil<SysWater>(SysWater.class);
        util.exportExcel(response, list, "水质信息管理数据");
    }

    /**
     * 获取水质信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:water:query')")
    @GetMapping(value = "/{waterId}")
    public AjaxResult getInfo(@PathVariable("waterId") Long waterId)
    {
        return success(sysWaterService.selectSysWaterByWaterId(waterId));
    }

    /**
     * 新增水质信息管理
     */
    @PreAuthorize("@ss.hasPermi('system:water:add')")
    @Log(title = "水质信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWater sysWater)
    {
        return toAjax(sysWaterService.insertSysWater(sysWater));
    }

    /**
     * 修改水质信息管理
     */
    @PreAuthorize("@ss.hasPermi('system:water:edit')")
    @Log(title = "水质信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWater sysWater)
    {
        return toAjax(sysWaterService.updateSysWater(sysWater));
    }

    /**
     * 删除水质信息管理
     */
    @PreAuthorize("@ss.hasPermi('system:water:remove')")
    @Log(title = "水质信息管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{waterIds}")
    public AjaxResult remove(@PathVariable Long[] waterIds)
    {
        return toAjax(sysWaterService.deleteSysWaterByWaterIds(waterIds));
    }
}
