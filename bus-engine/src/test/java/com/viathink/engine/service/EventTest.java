package com.viathink.engine.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.viathink.core.batch.dto.BusinessDetailResult;
import com.viathink.core.batch.service.*;
import com.viathink.core.business.gene.bean.ErrorLogEntity;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.dto.DetailCountForm;
import com.viathink.core.business.gene.mapper.ErrorLogMapper;
import com.viathink.core.business.gene.mapper.GeneDailyDetailMapper;
import com.viathink.core.business.gene.service.*;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import com.viathink.core.util.ExportExcelUtil;
import com.viathink.engine.EngineApplication;
import com.viathink.engine.batch.SchedulerTask;
import com.viathink.core.util.DayListUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EngineApplication.class, webEnvironment = NONE)
public class EventTest {
    @Autowired
    private OrderEventService orderEventService;
    @Autowired
    private LocalStaffEventService localStaffEventService;
    @Autowired
    private CountyEventService countyEventService;
    @Autowired
    private DoctorEventService doctorEventService;
    @Autowired
    private LogisticsPayoutEventService logisticsPayoutEventService;
    @Autowired
    private ProvinceEventService provinceEventService;
    @Autowired
    private RegionEventService regionEventService;
    @Autowired
    private CityEventService cityEventService;
    @Autowired
    private BusinessDetailService businessDetailService;
    @Autowired
    private ProfitService profitService;
    @Autowired
    @Qualifier("regionContrastServiceImpl")
    private ContrastService regionContrastService;
    @Autowired
    @Qualifier("provinceContrastServiceImpl")
    private ContrastService provinceContrastService;
    @Autowired
    @Qualifier("testingItemContrastServiceImpl")
    private ContrastService testingItemContrastService;
    @Autowired
    private IncomeService incomeServiceImpl;
    @Autowired
    private SnapshootService snapshootService;
    @Autowired
    private SchedulerTask schedulerTask;
    @Autowired
    private MonthAvgRrendService monthAvgRrendService;
    @Autowired
    private HolidayDayService holidayDayService;
    @Autowired
    private ErrorLogMapper errorLogMapper;

    @Test
    public void readExcel() {
        String path = "excel/income.xlsx";
        // excel/aa.xlsx
        InputStream fileInputStream =
                EventTest.class.getClassLoader().getResourceAsStream(path);
        System.out.println("fileInputStream: " + fileInputStream);
    }

    private JSONObject parseStrToJSONObject(String jsonFileName) {
        // excel/aa.xlsx
        InputStream fileInputStream =
                EventTest.class.getClassLoader().getResourceAsStream(jsonFileName);
        byte[] bytes = new byte[0];
        try {
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = new String(bytes);
        return JSON.parseObject(str);
    }
    public Map<String, Object> getParamMap(String jsonFileName) {
        JSONObject jsonObject = parseStrToJSONObject(jsonFileName);
        EventBaseEntity event = new EventBaseEntity();
        event.setTag(jsonObject.getString("tag"));
        event.setEvent(jsonObject.getString("event"));
        event.setEventTime(Long.valueOf(jsonObject.getString("eventTime")));
        event.setMessageId("message id");
        event.setRecordId(Long.valueOf(jsonObject.getString("id")));
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(Long.valueOf(jsonObject.getString("eventTime")));
        event.setDayStr(businessDate.getDayStr());
        event.setMonthStr(businessDate.getMonthStr());
        event.setYearStr(businessDate.getYearStr());
        event.setQuarterStr(businessDate.getQuarterStr());
        event.setHalfYearStr(businessDate.getHalfYearStr());
        JSONObject data = jsonObject.getJSONObject("data");
        Map<String, Object> map = new HashMap<>();
        map.put("event", event);
        map.put("data", data);
        return map;
    }
    @Test
    public void orderCreateEvent() {
        Map<String, Object> map = this.getParamMap("orderCreate.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        orderEventService.orderCreated(event, data);
    }
    @Test
    public void orderCancelEvent() {
        Map<String, Object> map = this.getParamMap("orderCancel.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        orderEventService.orderCancel(event, data);
    }
    @Test
    public void orderPayedEvent() {
        Map<String, Object> map = this.getParamMap("orderPayed.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        orderEventService.orderPay(event, data);
    }

    @Test
    public void sampleConfirmedEvent() {
        Map<String, Object> map = this.getParamMap("sampleConfirmed.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        orderEventService.sampleConfirmed(event, data);
    }
    @Test
    public void testingReportUploadedEvent() {
        Map<String, Object> map = this.getParamMap("testingReportUploaded.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        orderEventService.testingReportUploaded(event, data);
    }
    @Test
    public void orderInvoicedEvent() {
        Map<String, Object> map = this.getParamMap("orderInvoiced.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        orderEventService.orderInvoiced(event, data);
    }

    @Test
    public void orderRefundedEvent() {
        Map<String, Object> map = this.getParamMap("orderRefunded.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        orderEventService.orderRefunded(event, data);
    }
    @Test
    public void orderUpdatedEvent() {
        Map<String, Object> map = this.getParamMap("orderUpdated.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        orderEventService.orderUpdated(event, data);
    }
    @Test
    public void localStaffReimburseApprovedEvent() {
        Map<String, Object> map = this.getParamMap("localStaffReimburseApproved.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        logisticsPayoutEventService.eventHandle(event, data);
    }
    @Test
    public void localStaffUpdateEvent() {
        Map<String, Object> map = this.getParamMap("localStaffUpdate.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        localStaffEventService.eventHandle(event, data);
    }
    @Test
    public void doctorUpdateEvent() {
        Map<String, Object> map = this.getParamMap("doctorUpdate.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        doctorEventService.eventHandle(event, data);
    }
    @Test
    public void regionProvinceUpdateEvent() {
        Map<String, Object> map = this.getParamMap("regionProvinceUpdate.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        provinceEventService.updateProvinceRegionRelation(event, data);
    }
    @Test
    public void provinceUpdateEvent() {
        Map<String, Object> map = this.getParamMap("provinceUpdate.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        provinceEventService.updateProvinceInfo(event, data);
    }
    @Test
    public void cityUpdateEvent() {
        Map<String, Object> map = this.getParamMap("cityUpdate.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        cityEventService.updateCityInfo(event, data);
    }
    @Test
    public void countyUpdateEvent() {
        Map<String, Object> map = this.getParamMap("countyUpdate.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        countyEventService.updateCountyInfo(event, data);
    }
    @Test
    public void regionUpdateEvent() {
        Map<String, Object> map = this.getParamMap("regionUpdate.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        JSONObject data = (JSONObject) map.get("data");
        regionEventService.updateRegionInfo(event, data);
    }

    @Test
    public void getDailyDetailReport() {
        businessDetailService.getDailyDetailReport(new Date(1528795458000L));
    }

    @Test
    public void getMonthDetailReport() {

        businessDetailService.getMonthDetailReport(new Date(1528795458000L));
    }

    @Test
    public void getHalfYearDetailReport() {

        businessDetailService.getHalfYearDetailReport(new Date(1528795458000L));
    }

    @Test
    public void getYearDetailReport() {

        businessDetailService.getYearDetailReport(new Date(1528795458000L));
    }

    @Test
    public void getHalfYearDetailProfitReport() {
        profitService.getHalfYearDetailProfitReport(new Date(1528795458000L));
    }

    @Test
    public void getMonthDetailProfitReport() {

        profitService.getMonthDetailProfitReport(new Date(1528795458000L));
    }

    @Test
    public void getYearDetailProfitReport() {

        profitService.getYearDetailProfitReport(new Date(1528795458000L));
    }

    @Test
    public void regionContrastServiceTest(){
        Date date = new Date();
        date.setTime(1528795458000L);
        regionContrastService.dailySnapshoot(date);
        regionContrastService.monthlySnapshoot(date);
        regionContrastService.halfYearlySnapshoot(date);
        regionContrastService.yearlySnapshoot(date);
    }

    @Test
    public void provinceContrastServiceTest(){
        Date date = new Date();
        date.setTime(1528795458000L);
        provinceContrastService.dailySnapshoot(date);
        provinceContrastService.monthlySnapshoot(date);
        provinceContrastService.halfYearlySnapshoot(date);
        provinceContrastService.yearlySnapshoot(date);
    }

    @Test
    public void testingItemContrastServiceTest(){
        Date date = new Date();
        date.setTime(1528795458000L);
        testingItemContrastService.dailySnapshoot(date);
        testingItemContrastService.monthlySnapshoot(date);
        testingItemContrastService.halfYearlySnapshoot(date);
        testingItemContrastService.yearlySnapshoot(date);
    }

    @Test
    public void dateCalTest(){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        System.out.println(calendar.get(Calendar.MONTH));
    }

    private Date dateCal(Date date, int dateDimension, int amount){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(dateDimension,amount); //整数往后推,负数往前移动
        return calendar.getTime();
    }

    private Date getNowYearMonthDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(new Date());
        try {
            return sdf.parse(dateNowStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    @Test
    public void testtest(){
        String sstr = "2016-h1";
        String [] splits =null;
        String dateStr;

        if((splits = sstr.split("-h")).length == 2){
                dateStr = splits[0] + "-" + (Integer.valueOf(splits[1]) * 6) + "-" + "1";
            try {
                List<Date> dateList = DayListUtil.monthList(dateStr);
                for(Date date:dateList){
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    if(((calendar.get(Calendar.MONTH) + 1) % 6) == 0)
                        System.out.println(date);
                }
            } catch (ParseException e) {

            }
        }
    }

    @Test
    public void exportExcel() throws IOException,ParseException{
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
        ExportExcelUtil.readExcel(jsonObject,"excel/ceshi.xlsx");
    }

    @Test
    public void getIncomeDetailReport() {
        incomeServiceImpl.getMonthDetailReport(new Date());
    }
    @Test
    public void incomeExcel() throws IOException,ParseException{
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        snapshootEntity.setType(BatchUtil.TYPE_GENE_INCOME_DETAIL);
        snapshootEntity.setMonthStr("2018-05");
        SnapshootEntity snapshootByClass = snapshootService.findSnapshootByClass(snapshootEntity);
        JSONObject jsonObject = JSONObject.parseObject(snapshootByClass.getRecord());
        ExportExcelUtil.incomeExcel(jsonObject,"income.xlsx");
    }

    private static final Long MILLI_SEC_FOR_24HOURS = 24 * 3600 * 1000L;
    @Autowired
    private GeneDailyDetailMapper geneDailyDetailMapper;
    @Test
    public void compareTest(){
        Long milliSecNow = new Date().getTime();
        Long endMilliSec = (milliSecNow / MILLI_SEC_FOR_24HOURS + 1) * MILLI_SEC_FOR_24HOURS;
        Long startMilliSec = endMilliSec - MILLI_SEC_FOR_24HOURS * 14;
        DetailCountForm form = new DetailCountForm();
        form.setStartDate(startMilliSec);
        form.setEndDate(endMilliSec);
        List<BusinessDetailResult> resultList = geneDailyDetailMapper.queryDailyDetail(form);
        System.out.println(resultList);
    }

    @Test
    public void testCalendar(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(1517414400000L);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        System.out.println(dayOfYear);
    }

    @Test
    public void businessTest(){
        businessDetailService.getDailyDetailReport(new Date(1528214400000L));
    }

    @Test
    public void snapshootBatchTest() {
        schedulerTask.task();
        regionContrastService.yearlySnapshoot(new Date());
        businessDetailService.getYearDetailReport(new Date());
        profitService.getYearDetailProfitReport(new Date());
        provinceContrastService.yearlySnapshoot(new Date());
        testingItemContrastService.yearlySnapshoot(new Date());
    }
    @Test
    public void getOrderTrendReport() {
        monthAvgRrendService.getMonthAvgReport(new Date(1526054400000l));
    }

    @Test
    public void hsDataCheck2(){
        List<String> errors = new ArrayList<>();
        List<ErrorLogEntity> errorLogList = errorLogMapper.findAllFieldCheckError();

        for(ErrorLogEntity entity : errorLogList){
            String detail = entity.getDetail();
            String event = JSONObject.parseObject(detail).getString("event");
            String recordId = JSONObject.parseObject(detail).getString("recordId");
            JSONObject errorDetail = JSONObject.parseObject(detail);
            JSONArray errorList = errorDetail.getJSONArray("errorList");
            for(int i=0; i<errorList.size(); i++){
                String s = errorList.getString(i);
                if(s.startsWith("testings[")){
                    String[] split = s.split("\\.");
                    s = s.substring(split[0].length() + 1);
                }
                if(s.startsWith("检测套餐testingItemId重复"))
                    s = "检测套餐testingItemId重复";
                if(s.startsWith("订单不存在"))
                    s = "订单不存在";
                s = event + " : " + s;
                boolean flag = true;
                for(int j=0; j<errors.size() ; j++){
                    String error = errors.get(j);
                    if(error.matches("^[+] "+s+"[\\s\\S]*")){
                        Pattern pattern = Pattern.compile("(?<=(\n  > |,))\\w*(?=(,|$))");
                        Matcher matcher = pattern.matcher(error);
                        boolean innerFlag = true;
                        while (matcher.find()){
                            String group = matcher.group();
                            if(group.equals(recordId)){
                                innerFlag = false;
                            }
                        }
                        if(innerFlag){
                            errors.set(j,error + "," +recordId);
                        }
                        flag = false;
                    }
                }
                if(flag){
                    errors.add("+ " + s + "\n  > " + recordId);
                }
            }
        }
        for(String s : errors){
            System.out.println(s + "\n");
        }
    }

    @Test
    public void hsDataCheck3(){
        List<String> strList = new ArrayList<>();
        List<ErrorLogEntity> errorLogList = errorLogMapper.findAllFieldCheckError();
        for(ErrorLogEntity entity : errorLogList){
            String event = JSONObject.parseObject(entity.getDetail()).getString("event");
            if(event.equals("integralGranted")){
                strList.add(entity.getDetail());
            }
        }
        for(String str : strList){
            System.out.println(str + ",");
        }
    }

    @Test
    public void hsDataCheck4(){
        List<String> errors = new ArrayList<>();
        List<ErrorLogEntity> errorLogList = errorLogMapper.findAllFieldCheckError();
        for(ErrorLogEntity entity : errorLogList) {
            String event = JSONObject.parseObject(entity.getDetail()).getString("event");
            if (event.equals("integralGranted")) {
                String detail = entity.getDetail();
                JSONObject errorDetail = JSONObject.parseObject(detail);
                JSONArray errorList = errorDetail.getJSONArray("errorList");
                for (int i = 0; i < errorList.size(); i++) {
                    String s = errorList.getString(i);
                    if (s.startsWith("testings[")) {
                        String[] split = s.split("\\.");
                        s = s.substring(split[0].length() + 1);
                    }
                    if (s.startsWith("检测套餐testingItemId重复"))
                        s = "检测套餐testingItemId重复";
                    if (s.startsWith("订单不存在"))
                        s = "订单不存在";
                    if (!errors.contains(s))
                        errors.add(s);
                }
            }
        }
        for(String s : errors){
            System.out.println(s);
        }
    }
}
