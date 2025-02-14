package com.blackfiresoft.sheepmall.user;

import com.blackfiresoft.sheepmall.message.MailService;
import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.blackfiresoft.sheepmall.util.Md5Encode;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;


@Service
public class UserServiceImp implements UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private MailService mailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultEntity register(Users user, String code) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResultEntity.fail(ResultEnum.USERNAME_EXIST);
        } else if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResultEntity.fail(ResultEnum.EMAIL_EXIST);
        } else if (!Objects.equals(redisTemplate.opsForValue().get(user.getEmail()), code)) {
            return ResultEntity.fail(ResultEnum.VALIDATION_CODE_ERROR);
        } else {
            String encodePsw = Md5Encode.passwordEncode(user.getPassword());
            user.setPassword(encodePsw);
            userRepository.saveAndFlush(user);
        }
        return ResultEntity.success();
    }

    @Override
    public Boolean loginByEmail(String email, String password) {
        String encodePsw = Md5Encode.passwordEncode(password);
        Users user = userRepository.findByEmailAndPassword(email, encodePsw);
        return user != null;
    }

    @Override
    public Boolean loginByUsername(String username, String password) {
        String encodePsw = Md5Encode.passwordEncode(password);
        Users user = userRepository.findByUsernameAndPassword(username, encodePsw);
        return user != null;
    }

    @Override
    public Optional<Users> getUserInfo(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(Users user) {
        userRepository.findById(user.getId()).ifPresent(u -> {
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());
            u.setProfile_pic(user.getProfile_pic());
            userRepository.saveAndFlush(u);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultEntity deleteUser(Long id, String email, String code) {
        if (!Objects.equals(redisTemplate.opsForValue().get(email), code)) {
            return ResultEntity.fail(ResultEnum.VALIDATION_CODE_ERROR);
        }
        userRepository.deleteById(id);
        return ResultEntity.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultEntity resetPassword(String email, String code, String password) {
        if (Objects.equals(redisTemplate.opsForValue().get(email), code)) {
            Users userInfo = userRepository.findByEmail(email);
            String newPwd = Md5Encode.passwordEncode(password);
            userInfo.setPassword(newPwd);
            userRepository.saveAndFlush(userInfo);
            return ResultEntity.success();
        }
        return ResultEntity.fail(ResultEnum.VALIDATION_CODE_ERROR);
    }

    @Override
    public Boolean getValidationCode(String email) {
        String validationCode = mailService.sendEmail(email);
        if (validationCode != null) {
            redisTemplate.opsForValue().set(email, validationCode, Duration.ofSeconds(60));
            return true;
        }
        return false;
    }
}
