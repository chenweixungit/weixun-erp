package com.example.emos.api.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.json.JSONUtil;
import com.example.emos.api.common.util.PageUtils;
import com.example.emos.api.common.util.R;
import com.example.emos.api.controller.form.*;
import com.example.emos.api.db.pojo.TbDept;
import com.example.emos.api.db.pojo.TbRole;
import com.example.emos.api.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/dept")
@Tag(name = "DeptController", description = "部门Web接口")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/searchAllDept")
    @Operation(summary = "查询所有部门")
    public R searchAllDept() {
        ArrayList<HashMap> list = deptService.searchAllDept();
        return R.ok().put("list", list);
    }

    @PostMapping("/searchById")
    @Operation(summary = "根据ID查询部门")
    @SaCheckPermission(value = {"ROOT", "DEPT:SELECT"}, mode = SaMode.OR)
    public R searchById(@Valid @RequestBody SearchDeptByIdForm form) {
        HashMap map = deptService.searchById(form.getId());
        return R.ok(map);
    }

    @PostMapping("/searchDeptByPage")
    @Operation(summary = "查询部门分页数据")
    @SaCheckPermission(value = {"ROOT", "DEPT:SELECT"}, mode = SaMode.OR)
    public R searchDeptByPage(@Valid @RequestBody SearchRoleByPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1 ) * length;
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.put("start", start);
        PageUtils pageUtils = deptService.searchDeptByPage(param);
        return R.ok().put("page",pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加部门")
    @SaCheckPermission(value = {"ROOT", "DEPT:INSERT"}, mode = SaMode.OR)
    public R insert(@Valid @RequestBody InsertDeptForm form){
        TbDept tbDept = JSONUtil.parse(form).toBean(TbDept.class);
        int rows = deptService.insert(tbDept);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/update")
    @Operation(summary = "修改部门")
    @SaCheckPermission(value = {"ROOT", "DEPT:UPDATE"}, mode = SaMode.OR)
    public R update(@Valid @RequestBody UpdateDeptForm form){
        TbDept tbDept = new TbDept();
        tbDept.setId(form.getId());
        tbDept.setDeptName(form.getDeptName());
        tbDept.setEmail(form.getEmail());
        tbDept.setTel(form.getTel());
        tbDept.setDesc(form.getDesc());
        int rows = deptService.update(tbDept);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/deleteDeptByIds")
    @Operation(summary = "删除部门记录")
    @SaCheckPermission(value = {"ROOT", "DEPT:DELETE"}, mode = SaMode.OR)
    public R deleteDeptByIds(@Valid @RequestBody DeleteDeptByIdsForm form) {
        int rows = deptService.deleteDeptByIds(form.getIds());
        return R.ok().put("rows", rows);
    }
}