package com.blackfiresoft.sheepmall.admin.marketHandle;

import com.blackfiresoft.sheepmall.user.Users;
import jakarta.annotation.Resource;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecordImp implements RecordService{
    @Resource
    private RecordRepository recordRepository;

    @Override
    public List<Records> getByActivityId(Long activityId) {
        return recordRepository.findByActivityId(activityId);
    }

    @Override
    public List<Records> getByProductNo(String productNo) {
        return recordRepository.findByProductNo(productNo);
    }

    @Override
    public List<Records> getByUsers(Users user) {
        return recordRepository.findByUsers(user);
    }

    @Override
    public void create(@NonNull Records record) {
        recordRepository.saveAndFlush(record);
    }
}
