package com.blackfiresoft.sheepmall.admin.statistic;

import com.blackfiresoft.sheepmall.dto.*;
import com.blackfiresoft.sheepmall.order.OrderRepository;
import com.blackfiresoft.sheepmall.order.Orders;
import com.blackfiresoft.sheepmall.refund.Refund;
import com.blackfiresoft.sheepmall.refund.RefundRepository;
import com.blackfiresoft.sheepmall.user.UserRepository;
import com.blackfiresoft.sheepmall.user.Users;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 统计数据实现类
 */
@Service
public class StatisticImp implements StatisticHandle {
    @Resource
    private UserRepository userRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private RefundRepository refundRepository;

    /**
     * 当天和昨天会员增加数据统计
     *
     * @return UserStatisticDto
     */
    @Override
    public UserStatisticDto memberStatistic() {
        List<Users> tData = userRepository.findByToday();
        List<Users> yData = userRepository.findByYesterday();
        UserStatisticDto userStatisticDto = new UserStatisticDto();
        userStatisticDto.setTodayUser(tData.size());
        userStatisticDto.setYesterdayUser(yData.size());
        return userStatisticDto;
    }

    /**
     * 当天和昨天退款金额统计
     *
     * @return RefundStatisticDto
     */
    @Override
    public RefundStatisticDto refundStatistic() {
        List<Refund> tData = refundRepository.findByToday();
        List<Refund> yData = refundRepository.findByYesterday();
        RefundStatisticDto refundStatistic = new RefundStatisticDto();
        BigDecimal tRefund = new BigDecimal(0);
        BigDecimal yRefund = new BigDecimal(0);
        for (Refund tDatum : tData) {
            tRefund = tRefund.add(tDatum.getRefundAmount());
        }
        refundStatistic.setTodayRefund(tRefund);
        for (Refund yDatum : yData) {
            yRefund = yRefund.add(yDatum.getRefundAmount());
        }
        refundStatistic.setYesterdayRefund(yRefund);
        return refundStatistic;
    }

    /**
     * 当天和昨天销售额以及销售量统计
     *
     * @return OrderOdStatDto
     */
    @Override
    public OrderStatisticDto OrderStatistic() {
        List<Orders> byTdSale = orderRepository.findByTdSale();
        List<Orders> byYdSale = orderRepository.findByYdSale();
        List<Orders> byTdIncome = orderRepository.findByTdIncome();
        List<Orders> byYdIncome = orderRepository.findByYdIncome();
        OrderStatisticDto orderOdStat = new OrderStatisticDto();
        int tdSaleCount = 0, yeSaleCount = 0;
        BigDecimal tdIncome = new BigDecimal(0);
        BigDecimal ydIncome = new BigDecimal(0);
        //当天销售量
        for (Orders tdSale : byTdSale) {
            tdSaleCount += tdSale.getQuantity();
        }
        orderOdStat.setTodaySale(tdSaleCount);
        //昨天销售量
        for (Orders ydSale : byYdSale) {
            yeSaleCount += ydSale.getQuantity();
        }
        orderOdStat.setYesterdaySale(yeSaleCount);
        //今天销售额
        for (Orders tdAmount : byTdIncome) {
            tdIncome = tdIncome.add(tdAmount.getTotalPrice());
        }
        orderOdStat.setTodayIncome(tdIncome);
        //昨天销售额
        for (Orders ydAmount : byYdIncome) {
            ydIncome = ydIncome.add(ydAmount.getTotalPrice());
        }
        orderOdStat.setYesterdayIncome(ydIncome);
        return orderOdStat;
    }

    /**
     * 统计近7天销售量数据
     *
     * @return SaleTrendDto
     */
    @Override
    public List<Map<String, Object>> sevenDaySaleTrend() {
        return orderRepository.findBySevenDaySale();
    }

    /**
     * 统计近30天销售量数据
     *
     * @return List<SaleTrendDto>
     */
    @Override
    public List<Map<String, Object>> thirtyDaySaleTrend() {
        return orderRepository.findByThirtyDaySale();
    }

    /**
     * 统计近7天销售额数据
     *
     * @return List<IncomeTrendDto>
     */
    @Override
    public List<Map<String, Object>> sevenDayIncomeTrend() {
        return orderRepository.findBySevenDayIncome();
    }

    /**
     * 统计近30天销售额数据
     *
     * @return List<IncomeTrendDto>
     */
    @Override
    public List<Map<String, Object>> thirtyDayIncomeTrend() {
        return orderRepository.findByThirtyDayIncome();
    }

}
