package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.util.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysDevice;
import com.ruoyi.system.domain.SysWater;
import com.ruoyi.system.domain.vo.LocationVo;
import com.ruoyi.system.domain.vo.OtherVo;
import com.ruoyi.system.domain.vo.SysDeviceAddVo;
import com.ruoyi.system.domain.vo.SysDeviceUpdateVo;
import com.ruoyi.system.mapper.SysWaterMapper;
import com.ruoyi.system.service.ISysDeviceService;
import com.ruoyi.system.service.ISysWaterService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备信息管理Service业务层处理
 *
 * @author JavaSkx
 * @date 2023-04-20
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
                result = get("http://api.heclouds.com/devices?"+query,"GET");
            }else{
                result = get("http://api.heclouds.com/devices","GET");
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
                if(deviceObject.has("desc")){
                    device.setDesc(deviceObject.getString("desc"));
                }
                if(deviceObject.has("auth_info")){
                    device.setAuthInfo(deviceObject.getString("auth_info"));
                }
                if(deviceObject.has("other")){
                    JSONObject other = deviceObject.getJSONObject("other");
                    OtherVo otherVo = new OtherVo();
                    if(other.has("version")){
                        otherVo.setVersion(other.getString("version"));
                    }
                    if(other.has("manufacturer")){
                        otherVo.setManufacturer(other.getString("manufacturer"));
                    }
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



    /**
     * 新增设备
     * @param sysDevice
     * @return 结果
     */
    @Override
    public int insertSysDevice(SysDeviceAddVo sysDevice) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String postJson = objectMapper.writeValueAsString(sysDevice);
            JSONObject jsonObject = margePostJon(postJson);
            if (!jsonObject.has("tags")){
                jsonObject.remove("tags");
            }
            String response = post("http://api.heclouds.com/devices", jsonObject.toString(),"POST");
            JSONObject result = new JSONObject(response);
            if(result.getInt("errno")==0){
                return 1;
            }else{
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    private JSONObject margePostJon(String postJson){
        JSONObject jsonObject = new JSONObject(postJson);
        JSONObject other = jsonObject.getJSONObject("other");
        if(other.isNull("version")&&other.isNull("manufacturer")){
            jsonObject.remove("other");
        }
        if (!jsonObject.has("tags")
                ||jsonObject.isNull("tags")
                ||jsonObject.getJSONArray("tags").length()==0){
            jsonObject.remove("tags");
        }
        if(jsonObject.isNull("auth_info")){
            jsonObject.remove("auth_info");
        }
        if(jsonObject.isNull("title")){
            jsonObject.remove("title");
        }
        if(jsonObject.isNull("desc")){
            jsonObject.remove("desc");
        }
        return jsonObject;
    }
    /**
     * 更新设备信息
     * @param sysDeviceUpdateVo
     * @return
     */
    @Override
    public int updateSysDevice(SysDeviceUpdateVo sysDeviceUpdateVo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String postJson = objectMapper.writeValueAsString(sysDeviceUpdateVo);
            JSONObject jsonObject = margePostJon(postJson);
            Long deviceId = null;
            if(jsonObject.has("deviceId")){
                deviceId = jsonObject.getLong("deviceId");
                jsonObject.remove("deviceId");
            }
            if(jsonObject.has("auth_info")){
                SysDevice sysDevice = new SysDevice();
                sysDevice.setAuthInfo(jsonObject.getString("auth_info"));
                sysDevice.setPage(1);
                sysDevice.setPer_page(10);
                List<SysDevice> sysDevices = this.selectDeviceInfo(sysDevice);
                if(sysDevices.size()>0&&sysDevices.get(0).getAuthInfo().equals(jsonObject.getString("auth_info"))){
                    jsonObject.remove("auth_info");
                }
            }
            String response = post("http://api.heclouds.com/devices/"+deviceId, jsonObject.toString(),"PUT");
            JSONObject result = new JSONObject(response);
            if(result.getInt("errno")==0){
                return 1;
            }else{
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除设备
     * @param
     * @return
     */
    @Override
    public int deleteSysDeviceByDeviceId(Long id) {
        try {
            String response = get("http://api.heclouds.com/devices/"+id,"DELETE");
            JSONObject result = new JSONObject(response);
            if(result.getInt("errno")==0){
                return 1;
            }else{
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    public String get(String url,String methodName) throws Exception {
        String content = null;
        URLConnection urlConnection = new URL(url).openConnection();
        HttpURLConnection connection = (HttpURLConnection) urlConnection;
        connection.setRequestProperty("api-Key","stzJxV=iGEoWNgepYJv5sW1PTGc=");
        connection.setRequestMethod(methodName);
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



    public String post(String url,String requestBody,String methodName) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod(methodName);
        con.setRequestProperty("api-Key","stzJxV=iGEoWNgepYJv5sW1PTGc=");
        con.setRequestProperty("Content-Type", "application/json");

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(requestBody.getBytes("UTF-8"));
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
