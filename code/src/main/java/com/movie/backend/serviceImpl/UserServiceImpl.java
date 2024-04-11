package com.movie.backend.serviceImpl;

import com.movie.backend.constant.Constant;
import com.movie.backend.dao.UserDao;
import com.movie.backend.dto.UserStatus;
import com.movie.backend.entity.User;
import com.movie.backend.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public Boolean register(String userName, String password, Integer gender) {
        User user = new User();
        user.setRole(0);
        user.setName(userName);
        user.setUsername(userName);
        user.setPassword(password);
        user.setGender(gender);
        userDao.save(user);
        return null;
    }

    @Override
    public Boolean register(Map<String, String> params) {
        User user = new User();
        String userName = params.get(Constant.USER_NAME);
        user.setUsername(userName);
        user.setName(userName);
        String password = params.get(Constant.PASSWORD);
        user.setPassword(password);
        Integer gender = Integer.parseInt(params.get(Constant.GENDER));
        user.setGender(gender);
        String area = params.get(Constant.AREA);
        user.setArea(area);
        String intro = params.get(Constant.INTRO);
        user.setIntro(intro);
        String email = params.get(Constant.EMAIL);
        user.setEmail(email);
        String phone = params.get(Constant.PHONE);
        user.setPhone(phone);
        user.setRole(0);
        String date = params.get(Constant.BIRTHDAY);
        //YYYY-MM-dd
        String yy = date.substring(0, 4);
        Integer year = Integer.parseInt(yy) - 1900;
        String mm = date.substring(5, 7);
        Integer month = Integer.parseInt(mm) - 1;
        String dd = date.substring(8, 10);
        Integer day = Integer.parseInt(dd);
        Date birthday = new Date(year, month, day);
        user.setBirthday(birthday);
        userDao.save(user);
        return true;
    }

    @Override
    public UserStatus login(String userName, String password) {
        User user = userDao.findUserByUsername(userName);
        UserStatus userStatus = new UserStatus();
        userStatus.setUserId(-1);
        if (user == null) {
            return userStatus;
        }
        if (!password.equals(user.getPassword())) {
            return userStatus;
        }
        userStatus.setUserId(user.getId());
        userStatus.setRole(user.getRole());
        return userStatus;
    }

    @Override
    public User getUserById(Integer uid) {
        return userDao.getUserById(uid);
    }
}
