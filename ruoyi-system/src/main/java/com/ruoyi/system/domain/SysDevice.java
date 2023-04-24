package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.system.domain.vo.LocationVo;
import com.ruoyi.system.domain.vo.OtherVo;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * 设备信息管理对象 sys_water
 *
 * @author JavaSkx
 * @date 2023-04-17
 */
public class SysDevice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long id;

    private Long device_id;

    private String title;

    private String protocol;

    private OtherVo other;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Boolean online;

    private LocationVo location;

    private String authInfo;

    private String desc;

    private List<String> tags;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;

    private int page;

    private int per_page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public OtherVo getOther() {
        return other;
    }

    public void setOther(OtherVo other) {
        this.other = other;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public LocationVo getLocation() {
        return location;
    }

    public void setLocation(LocationVo location) {
        this.location = location;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
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

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public String toQueryString() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(this);
            if (value != null) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(field.getName(), "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value.toString(), "UTF-8"));
            }
        }
        return sb.toString();
    }

}
