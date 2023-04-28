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
                                <el-divider direction="vertical" class="total-middle-divider"></el-divider>


                                <div style="float: right;margin-left: 400px;margin-top: 26px;">
<!--                                    <el-button size="mini"><i class="el-icon-plus el-icon&#45;&#45;left"></i>批量添加</el-button>-->
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
                      <pagination
                        v-show="total>0"
                        :total="total"
                        :page.sync="queryParams.page"
                        :limit.sync="queryParams.per_page"
                        @pagination="getList"
                      />
                </div>
            </div>
        </div>
             <!-- 添加或修改设备信息管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="设备名称：" prop="title" >
          <el-input v-model="form.title" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="鉴权信息：" prop="auth_info">
          <el-input v-model="form.auth_info" placeholder="请输入设备鉴权信息" />
        </el-form-item>
        <el-form-item label="设备描述：" prop="desc">
          <el-input v-model="form.desc" placeholder="请输入设备描述" />
        </el-form-item>
        <el-form-item label="设备标签：" prop="tags">
          <!-- <el-input v-model="form.tags" placeholder="请输入设备标签" /> -->
          <el-select v-model="form.tags" multiple placeholder="请选择">
            <el-option
              v-for="item in tagOpertions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="坐标信息：" prop="location">
          <el-input-number v-model="form.location.lon" :step="0.1" :max="1000" :min="-1000"></el-input-number>
           <span>&nbsp;&nbsp;&nbsp;,&nbsp;&nbsp;&nbsp;</span>
           <el-input-number v-model="form.location.lat" :step="0.1" :max="1000" :min="-1000"></el-input-number>
        </el-form-item>
        <el-form-item label="自定义：" prop="other">
          <label>版本号：</label>
          <el-input style="width:40%" v-model="form.other.version" placeholder="请输入设备版本号信息" />
          <br>
          <label>制造商：</label>
          <el-input style="width:40%;padding-top: 10px;" v-model="form.other.manufacturer" placeholder="请输入设备制造商信息" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
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
        this.getAllList();
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
      this.deviceid=null,
      this.form = {
        title:null,
        desc:null,
        tags:null,
        location:{
          lon:0,
          lat:0
        },
        auth_info:null,
        other:{
          version:null,
          manufacturer:null,
        }
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
        let param={
            page:1,
            per_page:2147483647
          }
        listDevice(param).then(response => {
            this.total = response.total;
            this.loading=false;
        });
        listDevice(this.queryParams).then(response => {
            this.deviceList=response.rows;
            this.loading = false;
        });
       },

       getAllList() {
          this.loading=true;
          let param={
            page:1,
            per_page:2147483647
          }
          listDevice(param).then(response => {
             this.deviceTotal = response.total;
             this.loading=false;
          })
       },
       /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.page = 1;
        this.getList();
      },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const deviceId = row.id || this.ids
      getDevice(deviceId).then(response => {
        this.deviceid=deviceId;
        if(response.rows[0].other!=null){
          this.form.other=response.rows[0].other;
        }
        if(response.rows[0].location!=null){
          this.form.location=response.rows[0].location;
        }
        this.form.auth_info = response.rows[0].authInfo;
        this.form.desc=response.rows[0].desc;
        this.form.title=response.rows[0].title;
        this.form.tags=response.rows[0].tags;
        this.open = true;
        this.title = "修改设备信息管理";
      });
    },

  /** 提交按钮 */
  submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.deviceid != null) {
            this.form.deviceId=this.deviceid
            updateDevice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDevice(this.form).then(response => {
              if(response.code==200){
                  this.$modal.msgSuccess("新增成功");
                  this.open = false;
                  this.getAllList();
                  this.getList();
              }else{
                this.$modal.msg("新增失败");
              }
            });
          }
        }
      });
    },

        /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备信息管理";
    },

      /** 删除按钮操作 */
    handleDelete(row) {
      const deviceIds = row.id || this.ids;
      this.$modal.confirm('是否确认删除设备信息管理编号为"' + deviceIds + '"的数据项？').then(function() {
        return delDevice(deviceIds);
      }).then(() => {
        this.getAllList();
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
            tagOpertions: [{
                value: 'china',
                label: 'china'
            }, {
                value: 'mobile',
                label: 'mobile'
            }],
            deviceList:[],
            value: '',
            deviceid:'',
            input3: '',
            select: '',
            title: '',
            open: false,
            currentPage3: 1,
            per_page:10,
            total:0,
            deviceTotal:0,
            onlineTotal:0,
             // 表单参数
            form: {
              location:{
                lon:0,
                lat:0
              },
              other:{
                version:null,
                manufacturer:null,
              }
            },
            rules: {
                title: [
                { required: true, message: "设备名称不能为空", trigger: "blur" }
                ],
                auth_info: [
                { required: true, message: "鉴权信息不能为空", trigger: "blur" }
                ],
                private: [
                { required: true, message: "设备私密性不能为空", trigger: "blur" }
              ]
            },
                 // 查询参数
            queryParams: {
                page: 1,
                per_page: 10,
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

.el-input-number--medium{
  width: 130px;
  line-height: 34px
}



</style>
