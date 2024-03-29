package com.example.emos.api.db.dao;

import com.example.emos.api.db.pojo.TbDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface TbDeptDao {
    public ArrayList<HashMap> searchAllDept();
    public HashMap searchById(int id);

    public ArrayList<HashMap> searchDeptByPage(HashMap params);

    public long searchDeptCount(HashMap params);

    public int update(TbDept tbDept);

    public int insert(TbDept tbDept);

    public boolean searchCanDelete(Integer[] ids);

    public int deleteDeptByIds(Integer[] ids);
}