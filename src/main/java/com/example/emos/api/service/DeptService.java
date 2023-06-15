package com.example.emos.api.service;

import com.example.emos.api.common.util.PageUtils;

import java.util.ArrayList;
import java.util.HashMap;

public interface DeptService {
    public ArrayList<HashMap> searchAllDept();
    public HashMap searchById(int id);

    public PageUtils searchDeptByPage(HashMap params);
}
