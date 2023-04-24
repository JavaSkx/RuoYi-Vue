import request from '@/utils/request'

// 查询设备信息管理列表
export function listDevice(query) {
    return request({
      url: '/system/device/list',
      method: 'get',
      params: query
    })
}

// 查询设备信息管理详细
export function getDevice(deviceId) {
    return request({
      url: '/system/device/list',
      method: 'get',
      params: {
        device_id: deviceId,
        page:1,
        per_page:10
      }
    })
}


// 新增设备信息管理
export function addDevice(data) {
    return request({
      url: '/system/device/add',
      method: 'post',
      data: data
    })
}

// 修改设备信息管理
export function updateDevice(data) {
    return request({
      url: '/system/device/update',
      method: 'post',
      data: data
    })

}


// 删除设备信息管理
export function delDevice(deviceId) {
    return request({
      url: '/system/device/delete/' + deviceId,
      method: 'delete'
    })
}




