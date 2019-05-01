package com.viathink.core.util;

import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.dto.StatisticalDataBaseDto;
import lombok.Data;

import java.util.List;

@Data
public class BatchUtil {
    public static final String TYPE_GENE_BUSINESS_DETAIL = "gene-businessDetail";
    public static final String TYPE_GENE_REGION_CONTRAST = "gene-regionContrast";
    public static final String TYPE_GENE_PROVINCE_CONTRAST = "gene-provinceContrast";
    public static final String TYPE_GENE_TESTING_ITEM_CONTRAST = "gene-testingItemContrast";
    public static final String TYPE_GENE_PROFIT = "gene-profit";
    public static final String TYPE_GENE_INCOME_DETAIL = "gene-income";
    public static final String TYPE_GENE_ORDER_TREND_DETAIL = "gene-orderTrend";

    public static final String TIME_RANGE_DAY_STR = "day";
    public static final String TIME_RANGE_MONTH_STR = "month";
    public static final String TIME_RANGE_YEAR_STR = "year";
    public static final String TIME_RANGE_HALF_YEAR_STR = "halfYear";
    public static final String TIME_RANGE_QUARTER_STR = "quarter";
    public static final String ERROR_TYPE_STREAM = "stream";
    public static final String ERROR_TYPE_BATCH = "batch";
    public static final String ERROR_TYPE_BUSINESS = "business";

    public static SnapshootEntity setDatelyStr(SnapshootEntity entity, BusinessDate sourceDate){
        entity.setDayStr(sourceDate.getDayStr());
        entity.setMonthStr(sourceDate.getMonthStr());
        entity.setQuarterStr(sourceDate.getQuarterStr());
        entity.setHalfYearStr(sourceDate.getHalfYearStr());
        entity.setYearStr(sourceDate.getYearStr());
        return entity;
    }

    public static StatisticalDataBaseDto sumPackRecordData(List<? extends StatisticalDataBaseDto> list, StatisticalDataBaseDto sum){
            for (StatisticalDataBaseDto item : list) {
                sum.setOrderIncome(sum.getOrderIncome() + item.getOrderIncome());
                sum.setCashIncome(sum.getCashIncome() + item.getCashIncome());
                sum.setFinanceConfirmIncome(sum.getFinanceConfirmIncome() + item.getFinanceConfirmIncome());
                sum.setTestingItemCost(sum.getTestingItemCost() + item.getTestingItemCost());
                sum.setTestingItemConfirmCost(sum.getTestingItemConfirmCost() + item.getTestingItemConfirmCost());
                sum.setIntegralCost(sum.getIntegralCost() + item.getIntegralCost());
                sum.setOrderPlaceAvgCost(sum.getOrderPlaceAvgCost() + item.getOrderPlaceAvgCost());
                sum.setOrderCancelCount(sum.getOrderCancelCount() + item.getOrderCancelCount());
                sum.setOrderPlaceCount(sum.getOrderPlaceCount() + item.getOrderPlaceCount());
                sum.setOrderCancelAvgCost(sum.getOrderCancelAvgCost() + item.getOrderCancelAvgCost());
            }
            sum.setOrderPlaceAvgCost(sum.getOrderPlaceAvgCost() / list.size());
            sum.setOrderCancelAvgCost(sum.getOrderCancelAvgCost() / list.size());
        return sum;
    }
}
