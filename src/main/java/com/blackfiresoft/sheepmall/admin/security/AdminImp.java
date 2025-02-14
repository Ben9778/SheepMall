package com.blackfiresoft.sheepmall.admin.security;

import com.blackfiresoft.sheepmall.message.MailService;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.blackfiresoft.sheepmall.util.Md5Encode;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminImp implements AdminHandle {

    @Resource
    private AdminRepository adminRepository;
    @Resource
    private MailService mailService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultEntity initUpdatePwd(Long adminId, String oldPwd, String newPwd) {
        try {
            Optional<Administrator> adminOptional = adminRepository.findById(adminId);
            if (adminOptional.isPresent()) {
                Administrator admin = adminOptional.get();
                String encodeOldPwd = Md5Encode.passwordEncode(oldPwd);
                String encodeNewPwd = Md5Encode.passwordEncode(newPwd);
                if (admin.getPassword().equals(encodeOldPwd)) {
                    admin.setPassword(encodeNewPwd);
                    admin.setIsInit(1);
                    adminRepository.saveAndFlush(admin);
                    return ResultEntity.success();
                } else {
                    return ResultEntity.fail(ResultEnum.PASSWORD_ERROR);
                }
            } else {
                return ResultEntity.fail(ResultEnum.ADMIN_NOT_FOUND);
            }
        } catch (Exception e) {
            //日志记录
            System.err.println("Error updating admin password: " + e.getMessage());
            return ResultEntity.fail(ResultEnum.UPDATE_PASSWORD_ERROR);
        }
    }


    @Override
    public Administrator loginByUsername(String username, String password) {
        Optional<Administrator> adminInfo= adminRepository.findByUsernameAndPassword(username, Md5Encode.passwordEncode(password));
        return adminInfo.orElse(null);
    }

    @Override
    public Administrator loginByEmail(String email, String password) {
        Optional<Administrator> adminInfo = adminRepository.findByEmailAndPassword(email, Md5Encode.passwordEncode(password));
        return adminInfo.orElse(null);
    }

    @Override
    public Administrator getAdminInfo(Long adminId) {
        Optional<Administrator> adminInfo = adminRepository.findById(adminId);
        return adminInfo.orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultEntity resetPwd(String email, String code, String password) {
        String redisCode = (String) redisTemplate.opsForValue().get(email);
        if (redisCode != null && redisCode.equals(code)) {
            Optional<Administrator> adminInfo = adminRepository.findByEmail(email);
            if (adminInfo.isPresent()) {
                int row = adminRepository.setAdminPasswordFor(Md5Encode.passwordEncode(password),email);
                return row>0?ResultEntity.success(row):ResultEntity.fail(ResultEnum.RESET_PASSWORD_ERROR);
            } else {
                return ResultEntity.fail(ResultEnum.DATA_NOT_FOUND);
            }
        }
        return ResultEntity.fail(ResultEnum.VALIDATION_CODE_ERROR);
    }

    @Override
    public Boolean getValidationCode(String email) {
        String code = mailService.sendEmail(email);
        if (code != null) {
            redisTemplate.opsForValue().set(email, code, 60 * 1000);
            return true;
        }
        return false;
    }
}

