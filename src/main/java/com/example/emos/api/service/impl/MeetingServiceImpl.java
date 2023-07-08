package com.example.emos.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.example.emos.api.common.util.PageUtils;
import com.example.emos.api.db.dao.TbMeetingDao;
import com.example.emos.api.db.pojo.TbMeeting;
import com.example.emos.api.exception.EmosException;
import com.example.emos.api.service.MeetingService;
import com.example.emos.api.task.MeetingWorkflowTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
@Service
@Slf4j
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private TbMeetingDao meetingDao;

    @Autowired
    private MeetingWorkflowTask meetingWorkflowTask;
    @Override
    public PageUtils searchOfflineMeetingByPage(HashMap param) {
        ArrayList<HashMap> list = meetingDao.searchOfflineMeetingByPage(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        long count = meetingDao.searchOfflineMeetingCount(param);
        for (HashMap map : list){
            String meeting = (String) map.get("meeting");
            if(meeting!=null && meeting.length() > 0){
                map.replace("meeting", JSONUtil.parseArray(meeting));
            }
        }
        PageUtils pageUtils = new PageUtils(list,count,start,length);
        return pageUtils;
    }

    @Override
    public int insert(TbMeeting meeting) {
        int rows = meetingDao.insert(meeting);
        if(rows != 1){
            throw  new EmosException("会议添加失败");
        }
        meetingWorkflowTask.startMeetingWorkFlow(meeting.getUuid(),
                meeting.getCreatorId(), meeting.getTitle(),
                meeting.getDate(), meeting.getStart() + ":00",
                "线下会议");
        return rows;
    }
}
