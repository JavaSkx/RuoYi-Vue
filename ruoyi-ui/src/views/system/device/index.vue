<template>
     <div class="app-container all-height">
        <div>
            <div>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-card shadow="never" class="totalViewCard">
                             <el-row class="totalViewRow">
                                <el-col :span="4">
                                    <show-div :title="'设备数量(个)'" :text="deviceTotal" />
                                 </el-col>
                                <el-divider direction="vertical" class="total-middle-divider"></el-divider>
                                <el-col :span="4">
                                    <show-div :title="'在线设备数(个)'" :text="onlineTotal" text-color="#C80000" />
                                </el-col>
                                <el-divider direction="vertical" class="total-middle-divider"></el-divider>
                        
                    
                                <div style="float: right;margin-left: 400px;margin-top: 26px;">
                                    <el-button size="mini"><i class="el-icon-plus el-icon--left"></i>批量添加</el-button>
                                    <el-button size="mini" type="primary" @click="handleAdd"><i class="el-icon-plus el-icon--left"></i>添加设备</el-button>
                                </div>
                            </el-row>
                         </el-card>
                     </el-col>
                 </el-row>

            </div>
            <div class="search">
                <el-select v-model="queryParams.online" placeholder="在线状态(全部)" size="small" class="select-low">
                        <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                    </el-select>

                    
                    <el-input placeholder="请输入搜索内容" v-model="queryParams.title" class="input-with-select" size="small">
                        <el-select v-model="select" slot="prepend" placeholder="设备名称" class="select-low">
                            <el-option label="设备名称" value="1"></el-option>
                        </el-select>
                    </el-input>
                    <el-button size="small" icon="el-icon-search" class="search-btn"  @click="handleQuery">搜索</el-button>
            </div>
            <div class="table-data">
                <el-table
                :data="deviceList"
                style="width: 100%">
                    <el-table-column
                        prop="id"
                        label="设备ID"
                        width="200">
                    </el-table-column>
                    <el-table-column
                        prop="title"
                        label="设备名称"
                        width="200">
                    </el-table-column>
                    <el-table-column
                        label="设备状态">
                        <template slot-scope="scope">
                            <div slot="reference" class="name-wrapper">
                                <div v-if="scope.row.online=='1'">
                                    <el-tag size="medium">
                                        在线
                                    </el-tag>
                                </div>
                                <div v-else>
                                    <el-tag size="danger">
                                        离线
                                    </el-tag>
                                </div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="lastLogin"
                        label="最后在线时间">
                    </el-table-column>  
                    <el-table-column
                        fixed="right"
                        label="操作"
                        width="100">
                        <template slot-scope="scope">
                            <el-button type="text" @click="handleUpdate(scope.row)" size="small">编辑</el-button>
                            <el-button type="text" @click="handleDelete(scope.row)" size="small">删除</el-button>
                        </template>
                        </el-table-column>
                </el-table>
                <div class="block">
                    <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page.sync="currentPage3"
                    :page-size=pageSize
                    layout="prev, pager, next, jumper"
                    :total=total>
                    </el-pagination>
                </div>
            </div>
        </div>
             <!-- 添加或修改设备信息管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备名称" prop="deviceTitle">
          <el-input v-model="form.deviceTitle" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备描述" prop="desc">
          <el-input v-model="form.desc" placeholder="请输入设备描述" />
        </el-form-item>
        <el-form-item label="设备标签" prop="tags">
          <el-input v-model="form.tags" placeholder="请输入设备标签" />
        </el-form-item>
        <el-form-item label="坐标信息" prop="location">
          <el-input v-model="form.location" placeholder="请输入设备坐标信息" />
        </el-form-item>
        <el-form-item label="私密性" prop="private">
          <el-input v-model="form.private" placeholder="设备私密性" />
        </el-form-item>
        <el-form-item label="鉴权信息" prop="auth_info">
          <el-input v-model="form.auth_info" placeholder="请输入设备鉴权信息" />
        </el-form-item>
        <el-form-item label="自定义" prop="other">
          <el-input v-model="form.other" placeholder="请输入其他设备自定义信息" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    </div>


  
</template>


<script>
import ShowDiv from '@/views/dashboard/showDiv'
import  { listDevice, getDevice, delDevice, addDevice, updateDevice } from '@/api/system/device'

export default {

    name: 'Dashboard',
    components: {ShowDiv},
    created() {
        this.getList();
    },
    methods: {
         // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        deviceTitle: null,
        desc:null,
        tags:null,
        location:null,
        private:null,
        auth_info:null,
        other:null
      };
      this.resetForm("form");
    },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      },
      /**数据表格*/
      getList() {
        this.loading = true;
        listDevice(this.queryParams).then(response => {
            this.deviceList = response.rows;
            this.total = response.total;
            this.loading = false;
            this.deviceTotal = response.total;

            for(let i=0;i<deviceList.length;i++){
                if([deviceList].online==true){
                   this.onlineTotal++;
                }
            }
        });
       },
       /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
       /** 修改按钮操作 */
     handleUpdate(row) {
        this.reset();
        //业务逻辑
     },
        /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备信息管理";
    },

      /** 删除按钮操作 */
    handleDelete(row) {
      const deviceIds = row.deviceId || this.ids;
      this.$modal.confirm('是否确认删除设备信息管理编号为"' + deviceId + '"的数据项？').then(function() {
        return delWater(deviceIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
     
      
    },
    data() {
      return {
            options: [{
                value: '',
                label: '在线状态(全部)'
            }, {
                value: 'true',
                label: '在线'
            }, {
                value: 'false',
                label: '离线'
            }],
            deviceList:[],
            value: '',
            input3: '',
            select: '',
            title: '',
            open: false,
            currentPage3: 2,
            pageSize:100,
            total:150,
            deviceTotal:0,
            onlineTotal:0,
             // 表单参数
            form: {},
            rules: {
                deviceTitle: [
                { required: true, message: "设备名称不能为空", trigger: "blur" }
                ],
                desc: [
                { required: true, message: "设备描述不能为空", trigger: "blur" }
                ]
            },
                 // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                online: null,
                title: null
            },
      }
    }
}
</script>
<style scoped>


.app-container {
  padding: 10px 20px 20px 20px;
}

.all-height{
  min-height: calc(100vh) !important;
  background-color: #f7f7f7b3 !important;
}

.totalViewCard {
  border: 1px solid #E3E4E6;
}


.totalViewRow {
  display: flex;
  flex-direction: row;
  height: 100%;
}

.device-size{
    font-size: 13px !important;
}

.total-middle-divider {
  top:5px;
  height: 70px;
  background-color: #F1F1F2;
}


.select-low{
    width:140px;
}

.search{
    margin-top: 20px;
}

.input-with-select .el-input-group__prepend {
    background-color: #fff;
}

.el-input-group {
    padding-left: 10px;
    line-height: normal;
    display: inline-table;
    width: 39%;
    border-collapse: separate;
    border-spacing: 0;
}

.search-btn{
    color:#1890ff;
    margin-left:10px
}

.table-data{

    margin-top:20px;
    border-radius: 4px;
    border: 1px solid #e6ebf5;
    background-color: #FFFFFF;
    overflow: hidden;
    color: #303133;
    -webkit-transition: 0.3s;
    transition: 0.3s;
}

.block{
    text-align: center;
    margin-top: 10px;
    margin-bottom: 10px;
    display: block;
}
</style>