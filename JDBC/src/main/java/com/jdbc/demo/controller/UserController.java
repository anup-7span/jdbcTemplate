package com.jdbc.demo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbc.demo.entity.User;
import com.jdbc.demo.service.UserService;
import com.jdbc.demo.utills.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userServices;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(method = RequestMethod.POST)
    public Object addUser(@RequestBody HashMap<String,Object> requestBean){
        String request=null;
        request = String.valueOf(requestBean.get(Constants.REQUEST));
        if(request.equalsIgnoreCase(Constants.ADD_USER)){
            User o=objectMapper.convertValue(requestBean, new TypeReference<User>() {
            });
            return userServices.saveUser(o);
        }
        if (request.equalsIgnoreCase(Constants.UPDATE_USER)){
            User o=objectMapper.convertValue(requestBean, new TypeReference<User>() {
            });
            return userServices.updateUser(o);
        }
        if (request.equalsIgnoreCase(Constants.GET_USER)){
            User o=objectMapper.convertValue(requestBean, new TypeReference<User>() {
            });
            return userServices.getUsers(o);
        }
        if (request.equalsIgnoreCase(Constants.DELETE_USER)){
            User o=objectMapper.convertValue(requestBean, new TypeReference<User>() {
            });
            return userServices.delete(o);
        }
        if (request.equalsIgnoreCase(Constants.GET_JOIN_QUERY_DATA)){
            return userServices.allRecord();
        }
        return null;
    }
/*    @GetMapping()
    public List<User> getAll(){
        return userServices.allRecord();
    }*/
    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        return userServices.saveUser(user);
    }
    @PutMapping("/user")
    public User update(@RequestBody User user){
        return userServices.updateUser(user);
    }
    @GetMapping("/{id}")
    public User get(@PathVariable Integer id){
        return userServices.getUser(id);
    }
   @GetMapping()
   public List<User> getAll(){
        return userServices.allUser();
   }
   @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
       userServices.deleteById(id);
       return "User deleted" + id;
   }
   @PutMapping("/alluser")
    public int updateAll(@RequestBody List<User>userList){
        return userServices.multiInsert(userList);
   }
    @PostMapping("/users")
    public User updateAll(@RequestBody User user){
        return userServices.addMultipleUser(user);
    }

    @PostMapping("/addRecords")
    public int addUsers(@RequestBody List<User> userList ){
        return userServices.addMultiRecords(userList);
    }
}
