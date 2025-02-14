package com.blackfiresoft.sheepmall.admin.statistic;

import com.blackfiresoft.sheepmall.dto.OrderStatisticDto;
import com.blackfiresoft.sheepmall.dto.RefundStatisticDto;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 后台统计数据接口
 */
@RestController
@RequestMapping("/57602")
public class StatisticApi {
    @Resource
    private StatisticHandle statisticHandle;

    @GetMapping("/member/statistic")
    public ResultEntity getMemberStatistic() {
        return ResultEntity.success(statisticHandle.memberStatistic());
    }


    @GetMapping("/refund/statistic")
    public ResultEntity getRefundStatistic() {
        RefundStatisticDto refundStatisticDto = statisticHandle.refundStatistic();
        if (refundStatisticDto == null) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(refundStatisticDto);
    }

    @GetMapping("/order/statistic")
    public ResultEntity getOrderStatistic() {
        OrderStatisticDto orderStatisticDto = statisticHandle.OrderStatistic();
        if (orderStatisticDto == null) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(orderStatisticDto);
    }

    @GetMapping("/saleBySeven")
    public ResultEntity getSevenDaySale() {
        List<Map<String, Object>> saleList = statisticHandle.sevenDaySaleTrend();
        if (saleList == null) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(saleList);
    }

    @GetMapping("/saleByThirty")
    public ResultEntity getThirtyDaySale() {
        List<Map<String, Object>> saleList = statisticHandle.thirtyDaySaleTrend();
        if (saleList == null) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(saleList);
    }

    @GetMapping("/incomeBySeven")
    public ResultEntity getSevenDayIncome() {
        List<Map<String, Object>> incomeList = statisticHandle.sevenDayIncomeTrend();
        if (incomeList == null) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(incomeList);
    }

    @GetMapping("/incomeByThirty")
    public ResultEntity getThirtyDayIncome() {
        List<Map<String, Object>> incomeList = statisticHandle.thirtyDayIncomeTrend();
        if (incomeList == null) {
            return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
        }
        return ResultEntity.success(incomeList);
    }
}
