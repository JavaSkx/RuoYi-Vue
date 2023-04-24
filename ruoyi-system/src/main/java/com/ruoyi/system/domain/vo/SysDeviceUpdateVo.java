package com.ruoyi.system.domain.vo;

import java.util.List;

/**
 * 设备信息管理对象 sys_water
 *
 * @author JavaSkx
 * @date 2023-04-17
 */
public class SysDeviceUpdateVo
{
    private Long deviceId;

    private String title;

    private OtherVo other;

    private LocationVo location;

    private String auth_info;

    private String desc;

    private List<String> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OtherVo getOther() {
        return other;
    }

    public void setOther(OtherVo other) {
        this.other = other;
    }

    public LocationVo getLocation() {
        return location;
    }

    public void setLocation(LocationVo location) {
        this.location = location;
    }

    public String getAuth_info() {
        return auth_info;
    }

    public void setAuth_info(String auth_info) {
        this.auth_info = auth_info;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
