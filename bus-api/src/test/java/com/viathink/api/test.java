package com.viathink.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.viathink.api.report.dto.ReportDetailParamDto;
import com.viathink.api.report.util.ReportControllerUtil;
import com.viathink.core.business.gene.mapper.HolidayDayMapper;
import com.viathink.core.business.gene.service.HolidayDayService;
import com.viathink.core.business.gene.service.SnapshootService;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.SortServiceUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApiApplication.class, webEnvironment = NONE)
public class test {
    @Autowired
    SnapshootService snapshootService;
    @Autowired
    HolidayDayService holidayDayService;
    @Autowired
    private HolidayDayMapper holidayDayMapper;
    @Test
    public void compareTest(){
        ReportDetailParamDto reportDetailParamDto = new ReportDetailParamDto();
        reportDetailParamDto.setTimeDimension("day");
        reportDetailParamDto.setDateStr("2018-06-06");
        reportDetailParamDto.setSortBy("placeCount");
        reportDetailParamDto.setSortMode("desc");
        JSONObject snapshoot = ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST);
        JSONArray jsonArray = snapshoot.getJSONArray("list");
        String jsonStr = JSONObject.toJSONString(jsonArray,SerializerFeature.WriteClassName);
        System.out.println(jsonStr);
        List<JSONObject> list = JSONObject.parseArray(jsonStr, JSONObject.class);
        list.sort((o1, o2) -> {
            Long c1,c2;
            Integer mode;
            if(reportDetailParamDto.getSortBy().equals(SortServiceUtil.SORT_BY[0])){
                c1 = o1.getLong("testingItemPlaceCount");
                c2 = o2.getLong("testingItemPlaceCount");
            }else {
                c1 = o1.getLong("testingItemOrderIncome");
                c2 = o2.getLong("testingItemOrderIncome");
            }
            if(reportDetailParamDto.getSortMode().equals(SortServiceUtil.SORT_MODE[0])){
                mode = 1;
            }else{
                mode = -1;
            }
            if(c1 > c2){
                return mode;
            }else if(c1.equals(c2)){
                return 0;
            }else{
                return -1 * mode;
            }
        });
        JSONObject ret = new JSONObject();
        ret.put("list",list);
        ret.put("sum",snapshoot.get("sum"));
        System.out.println(ret);
    }

    @Test
    public void objectToMap() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("1","2");
        Object obj = map;
        String s = JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue);
        map = JSONObject.parseObject(s).getInnerMap();
        System.out.println(map);
    }
    @Test
    public void addHolidays(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        holidayDayMapper.deleteHolidaysByYearStr(String.valueOf(year));
        holidayDayService.addHolidays(year);
    }

}
