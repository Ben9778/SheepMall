package com.blackfiresoft.sheepmall.admin.memberHandle;
import com.blackfiresoft.sheepmall.user.UserRepository;
import com.blackfiresoft.sheepmall.user.Users;
import com.blackfiresoft.sheepmall.util.Md5Encode;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MemberImp implements Member {

    @Resource
    private UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(Users user) {
        user.setPassword(Md5Encode.passwordEncode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<Users> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(Users user) {
        userRepository.findById(user.getId()).ifPresent(u -> {
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            u.setProfile_pic(user.getProfile_pic());
            userRepository.saveAndFlush(u);
        });
    }
}
