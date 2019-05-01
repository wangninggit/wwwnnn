package com.viathink.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.service.SnapshootService;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.ExportExcelUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusApiApplicationTests {
    @Autowired
    private SnapshootService snapshootService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void exportExcel() throws IOException,ParseException {
        Map<String, Object> item = new HashMap<>();
        item.put("number", 1);
        item.put("name", "订单1");
        item.put("orderPrice", 100.00);
        item.put("date", "2018-06-01");
        Map<String, Object> item1 = new HashMap<>();
        item1.put("number", 2);
        item1.put("name", "订单2");
        item1.put("orderPrice", 1000);
        item1.put("date", "2018-05-06");
        List<Object> list1 = new ArrayList<>();

        list1.add(item);
        list1.add(item1);

        Map<String, Object> sum = new HashMap<>();
        sum.put("orderPrice", 1100);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list1);
        map.put("sum", sum);
        String str = JSON.toJSONString(map);
        JSONObject jsonObject = JSONObject.parseObject(str);
        ExportExcelUtil.readExcel(jsonObject,"ceshi.xlsx");
    }

    @Test
    public void incomeExcel() throws IOException,ParseException{
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        snapshootEntity.setType(BatchUtil.TYPE_GENE_INCOME_DETAIL);
        snapshootEntity.setMonthStr("2018-05");
        SnapshootEntity snapshootByClass = snapshootService.findSnapshootByClass(snapshootEntity);
        JSONObject jsonObject = JSONObject.parseObject(snapshootByClass.getRecord());
        ExportExcelUtil.incomeExcel(jsonObject,"incomeDetail.xlsx");
    }

}
