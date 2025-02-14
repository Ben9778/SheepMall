package com.blackfiresoft.sheepmall.admin.statistic;

import com.blackfiresoft.sheepmall.dto.*;

import java.util.List;
import java.util.Map;

/**
 * 统计数据统一接口
 */
public interface StatisticHandle {

    UserStatisticDto memberStatistic();

    RefundStatisticDto refundStatistic();

    OrderStatisticDto OrderStatistic();

    List<Map<String, Object>> sevenDaySaleTrend();

    List<Map<String, Object>> thirtyDaySaleTrend();

    List<Map<String, Object>> sevenDayIncomeTrend();

    List<Map<String, Object>> thirtyDayIncomeTrend();

}
