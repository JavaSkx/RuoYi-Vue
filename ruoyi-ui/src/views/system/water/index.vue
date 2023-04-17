<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="河流名称" prop="waterName">
        <el-input
          v-model="queryParams.waterName"
          placeholder="请输入河流名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-date-picker clearable
                        v-model="queryParams.updateTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择更新时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="水质等级" prop="rk">
        <el-input
          v-model="queryParams.rk"
          placeholder="请输入水质等级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:water:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:water:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:water:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:water:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="waterList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="水域ID" align="center" prop="waterId" />
      <el-table-column label="河流名称" align="center" prop="waterName" />
      <el-table-column label="PH值" align="center" prop="ph" />
      <el-table-column label="溶解氧" align="center" prop="ddo" />
      <el-table-column label="高猛酸盐指数" align="center" prop="pv" />
      <el-table-column label="化学需氧量" align="center" prop="cod" />
      <el-table-column label="五日生化需氧量" align="center" prop="bod" />
      <el-table-column label="氨氮" align="center" prop="nhn" />
      <el-table-column label="总磷" align="center" prop="p" />
      <el-table-column label="总氮" align="center" prop="n" />
      <el-table-column label="铜" align="center" prop="cu" />
      <el-table-column label="锌" align="center" prop="zn" />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="水质等级" align="center" prop="rk" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:water:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:water:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改水质信息管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="河流名称" prop="waterName">
          <el-input v-model="form.waterName" placeholder="请输入河流名称" />
        </el-form-item>
        <el-form-item label="PH值" prop="ph">
          <el-input v-model="form.ph" placeholder="请输入PH值" />
        </el-form-item>
        <el-form-item label="溶解氧" prop="ddo">
          <el-input v-model="form.ddo" placeholder="请输入溶解氧" />
        </el-form-item>
        <el-form-item label="高猛酸盐指数" prop="pv">
          <el-input v-model="form.pv" placeholder="请输入高猛酸盐指数" />
        </el-form-item>
        <el-form-item label="化学需氧量" prop="cod">
          <el-input v-model="form.cod" placeholder="请输入化学需氧量" />
        </el-form-item>
        <el-form-item label="五日生化需氧量" prop="bod">
          <el-input v-model="form.bod" placeholder="请输入五日生化需氧量" />
        </el-form-item>
        <el-form-item label="氨氮" prop="nhn">
          <el-input v-model="form.nhn" placeholder="请输入氨氮" />
        </el-form-item>
        <el-form-item label="总磷" prop="p">
          <el-input v-model="form.p" placeholder="请输入总磷" />
        </el-form-item>
        <el-form-item label="总氮" prop="n">
          <el-input v-model="form.n" placeholder="请输入总氮" />
        </el-form-item>
        <el-form-item label="铜" prop="cu">
          <el-input v-model="form.cu" placeholder="请输入铜" />
        </el-form-item>
        <el-form-item label="锌" prop="zn">
          <el-input v-model="form.zn" placeholder="请输入锌" />
        </el-form-item>
        <el-form-item label="水质等级" prop="rk">
          <el-input v-model="form.rk" placeholder="请输入水质等级" />
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
import { listWater, getWater, delWater, addWater, updateWater } from "@/api/system/water";

export default {
  name: "Water",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 水质信息管理表格数据
      waterList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        waterName: null,
        updateTime: null,
        rk: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        waterName: [
          { required: true, message: "河流名称不能为空", trigger: "blur" }
        ],
        ph: [
          { required: true, message: "PH值不能为空", trigger: "blur" }
        ],
        ddo: [
          { required: true, message: "溶解氧不能为空", trigger: "blur" }
        ],
        pv: [
          { required: true, message: "高猛酸盐指数不能为空", trigger: "blur" }
        ],
        cod: [
          { required: true, message: "化学需氧量不能为空", trigger: "blur" }
        ],
        bod: [
          { required: true, message: "五日生化需氧量不能为空", trigger: "blur" }
        ],
        nhn: [
          { required: true, message: "氨氮不能为空", trigger: "blur" }
        ],
        p: [
          { required: true, message: "总磷不能为空", trigger: "blur" }
        ],
        n: [
          { required: true, message: "总氮不能为空", trigger: "blur" }
        ],
        cu: [
          { required: true, message: "铜不能为空", trigger: "blur" }
        ],
        zn: [
          { required: true, message: "锌不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
        rk: [
          { required: true, message: "水质等级不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询水质信息管理列表 */
    getList() {
      this.loading = true;
      listWater(this.queryParams).then(response => {
        this.waterList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        waterId: null,
        waterName: null,
        ph: null,
        ddo: null,
        pv: null,
        cod: null,
        bod: null,
        nhn: null,
        p: null,
        n: null,
        cu: null,
        zn: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        rk: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.waterId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加水质信息管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const waterId = row.waterId || this.ids
      getWater(waterId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改水质信息管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.waterId != null) {
            updateWater(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWater(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const waterIds = row.waterId || this.ids;
      this.$modal.confirm('是否确认删除水质信息管理编号为"' + waterIds + '"的数据项？').then(function() {
        return delWater(waterIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/water/export', {
        ...this.queryParams
      }, `water_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
