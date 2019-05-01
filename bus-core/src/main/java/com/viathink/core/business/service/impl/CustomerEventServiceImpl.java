package com.viathink.core.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.service.*;
import com.viathink.core.business.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerEventServiceImpl implements CustomerEventService {
    private final OrderEventService orderEventServiceImpl;
    private final LogisticsPayoutEventService logisticsPayoutEventServiceImpl;
    private final LocalStaffEventService localStaffEventServiceImpl;
    private final DoctorEventService doctorEventServiceImpl;
    private final ProvinceEventService provinceEventServiceImpl;
    private final CityEventService cityEventServiceImpl;
    private final CountyEventService countyEventServiceImpl;
    private final RegionEventService regionEventServiceImpl;

    @Autowired
    public CustomerEventServiceImpl(OrderEventService orderEventServiceImpl, LogisticsPayoutEventService logisticsPayoutEventServiceImpl, LocalStaffEventService localStaffEventServiceImpl, DoctorEventService doctorEventServiceImpl, ProvinceEventService provinceEventServiceImpl, CityEventService cityEventServiceImpl, CountyEventService countyEventServiceImpl, RegionEventService regionEventServiceImpl) {
        this.orderEventServiceImpl = orderEventServiceImpl;
        this.logisticsPayoutEventServiceImpl = logisticsPayoutEventServiceImpl;
        this.localStaffEventServiceImpl = localStaffEventServiceImpl;
        this.doctorEventServiceImpl = doctorEventServiceImpl;
        this.provinceEventServiceImpl = provinceEventServiceImpl;
        this.cityEventServiceImpl = cityEventServiceImpl;
        this.countyEventServiceImpl = countyEventServiceImpl;
        this.regionEventServiceImpl = regionEventServiceImpl;
    }

    @Override
    public void eventHandler(EventBaseEntity event, JSONObject jsonObject) {
        JSONObject data = jsonObject.getJSONObject("data");
        String eventStr = jsonObject.getString("event");

        switch (jsonObject.getString("tag")){
            case "order":
                switch (eventStr) {
                    case "orderCreated":
                        orderEventServiceImpl.orderCreated(event, data);
                        break;
                    case "orderPayed":
                        orderEventServiceImpl.orderPay(event, data);
                        break;
                    case "sampleConfirmed":
                        orderEventServiceImpl.sampleConfirmed(event, data);
                        break;
                    case "testingReportUploaded":
                        orderEventServiceImpl.testingReportUploaded(event, data);
                        break;
                    case "orderInvoiced":
                        orderEventServiceImpl.orderInvoiced(event, data);
                        break;
                    case "integralGranted":
                        orderEventServiceImpl.integralGranted(event, data);
                        break;
                    case "orderCancel":
                        orderEventServiceImpl.orderCancel(event, data);
                        break;
                    case "orderRefunded":
                        orderEventServiceImpl.orderRefunded(event, data);
                        break;
                    case "orderUpdated":
                        orderEventServiceImpl.orderUpdated(event, data);
                        break;
                    case "orderLogistics":
                        orderEventServiceImpl.orderLogistics(event, data);
                        break;
                    default:
                        break;
                }
                break;
            case "logistics":
                switch (eventStr) {
                    case "localStaffReimburseApproved":
                        logisticsPayoutEventServiceImpl.eventHandle(event, data);
                        break;
                    default:
                        break;
                }
                break;
            case "system":
                switch (eventStr) {
                    case "localStaffUpdate":
                        localStaffEventServiceImpl.eventHandle(event, data);
                        break;
                    case "doctorUpdate":
                        doctorEventServiceImpl.eventHandle(event, data);
                        break;
                    case "regionProvinceUpdate":
                        provinceEventServiceImpl.updateProvinceRegionRelation(event, data);
                        break;
                    case "provinceUpdate":
                        provinceEventServiceImpl.updateProvinceInfo(event, data);
                        break;
                    case "cityUpdate":
                        cityEventServiceImpl.updateCityInfo(event, data);
                        break;
                    case "countyUpdate":
                        countyEventServiceImpl.updateCountyInfo(event, data);
                        break;
                    case "regionUpdate":
                        regionEventServiceImpl.updateRegionInfo(event, data);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
