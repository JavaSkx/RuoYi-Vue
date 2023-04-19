import request from '@/utils/request'

// 查询设备信息管理列表
export function listDevice(query) {
    return request({
      url: 'http://api.heclouds.com/devices',
      method: 'get',
      params: query
    })
}

// 查询水质信息管理详细
export function getDevice(deviceId) {
    return request({
      url: '/system/device/' + deviceId,
      method: 'get'
    })
}


// 新增设备信息管理
export function addDevice(data) {
    return request({
      url: '/system/device',
      method: 'post',
      data: data
    })
}

// 修改设备信息管理
export function updateDevice(data) {
    return request({
      url: '/system/device',
      method: 'put',
      data: data
    })

}


// 删除水质信息管理
export function delDevice(deviceId) {
    return request({
      url: '/system/device/' + deviceId,
      method: 'delete'
    })
}




  