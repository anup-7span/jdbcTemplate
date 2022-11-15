package com.jdbc.demo.service;
import com.jdbc.demo.entity.User;
import org.springframework.stereotype.Service;


import java.util.List;

public interface UserService {
    User saveUser(User user);
    User updateUser(User user);
    User getUser(Integer id);
    String deleteById(Integer id);
    List<User> allUser();
    User getUsers(User user);
    String delete(User user);
    List<User> allRecord();

    int batchUpdate(List<User> userList);
    int multiInsert(List<User> userList);

    User addMultipleUser(User user);

    int addMultiRecords(List<User> userList);
}
