package com.movie.backend.daoImpl;

import com.movie.backend.dao.UserDao;
import com.movie.backend.entity.User;
import com.movie.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    UserRepository userRepository;
    @Override
    public Boolean existsUserByIdIs(Integer userId) {
        return userRepository.existsUserByIdIs(userId);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String userName) {
        return userRepository.findUserByUsernameIs(userName);
    }

    @Override
    public User getUserById(Integer uid) {
        return userRepository.findUserByIdIs(uid);
    }
}
