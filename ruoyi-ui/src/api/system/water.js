import request from '@/utils/request'

// 查询水质信息查询列表
export function listWater(query) {
  return request({
    url: '/system/water/list',
    method: 'get',
    params: query
  })
}

// 查询水质信息查询详细
export function getWater(waterId) {
  return request({
    url: '/system/water/' + waterId,
    method: 'get'
  })
}

// 新增水质信息查询
export function addWater(data) {
  return request({
    url: '/system/water',
    method: 'post',
    data: data
  })
}

// 修改水质信息查询
export function updateWater(data) {
  return request({
    url: '/system/water',
    method: 'put',
    data: data
  })
}

// 删除水质信息查询
export function delWater(waterId) {
  return request({
    url: '/system/water/' + waterId,
    method: 'delete'
  })
}
