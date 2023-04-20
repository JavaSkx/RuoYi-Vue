package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.util.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysDevice;
import com.ruoyi.system.domain.SysWater;
import com.ruoyi.system.domain.vo.LocationVo;
import com.ruoyi.system.domain.vo.OtherVo;
import com.ruoyi.system.mapper.SysWaterMapper;
import com.ruoyi.system.service.ISysDeviceService;
import com.ruoyi.system.service.ISysWaterService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 水质信息管理Service业务层处理
 *
 * @author JavaSkx
 * @date 2023-04-17
 */
@Service
public class SysDeviceServiceImpl implements ISysDeviceService
{

    @Override
    public List<SysDevice> selectDeviceInfo(SysDevice sysDevice) {
        try {
            String query = sysDevice.toQueryString();
            String result = "";
            if(StringUtils.isNotEmpty(query)){
                result = get("http://api.heclouds.com/devices?"+query);
            }else{
                result = get("http://api.heclouds.com/devices");
            }
            JSONObject jsonObject = new JSONObject(result);
            JSONArray devicesArray = jsonObject.getJSONObject("data").getJSONArray("devices");
            List<SysDevice> devices = new ArrayList<>();
            for (int i = 0; i < devicesArray.length(); i++) {
                JSONObject deviceObject = devicesArray.getJSONObject(i);
                SysDevice device = new SysDevice();
                device.setProtocol(deviceObject.getString("protocol"));
                if(deviceObject.has("last_login")){
                    device.setLastLogin(DateUtils.parseDate(deviceObject.getString("last_login"),"yyyy-MM-dd HH:mm:ss"));
                }
                if(deviceObject.has("online")){
                    device.setOnline(deviceObject.getBoolean("online"));
                }
                device.setId(deviceObject.getLong("id"));
                device.setTitle(deviceObject.getString("title"));
                device.setDesc(deviceObject.getString("desc"));
                if(deviceObject.has("auth_info")){
                    device.setAuthInfo(deviceObject.getString("auth_info"));
                }
                if(deviceObject.has("other")){
                    JSONObject other = deviceObject.getJSONObject("other");
                    OtherVo otherVo = new OtherVo();
                    otherVo.setVersion(other.getString("version"));
                    otherVo.setManufacturer(other.getString("manufacturer"));
                    device.setOther(otherVo);
                }
                if(deviceObject.has("location")){
                    JSONObject location = deviceObject.getJSONObject("location");
                    LocationVo locationVo = new LocationVo();
                    locationVo.setLat(location.getDouble("lat"));
                    locationVo.setLon(location.getDouble("lon"));
                    device.setLocation(locationVo);
                }
                if(deviceObject.has("tags")){
                    JSONArray tags = deviceObject.getJSONArray("tags");
                    List<String> tagList = new ArrayList<>();
                    for(int j=0;j<tags.length();j++){
                        tagList.add((String) tags.get(j));
                    }
                    device.setTags(tagList);
                }
                devices.add(device);
            }
            return devices;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public String get(String url) throws Exception {
        String content = null;
        URLConnection urlConnection = new URL(url).openConnection();
        HttpURLConnection connection = (HttpURLConnection) urlConnection;
        connection.setRequestProperty("api-Key","stzJxV=iGEoWNgepYJv5sW1PTGc=");
        connection.setRequestMethod("GET");
        //连接
        connection.connect();
        //得到响应码
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                    (connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder bs = new StringBuilder();
            String l;
            while ((l = bufferedReader.readLine()) != null) {
                bs.append(l).append("\n");
            }
            content = bs.toString();
        }
        return content;
    }
}
