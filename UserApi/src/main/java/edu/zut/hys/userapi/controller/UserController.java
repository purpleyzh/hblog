package edu.zut.hys.userapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zut.hys.constant.Constant;
import edu.zut.hys.domain.*;
import edu.zut.hys.feignapi.clients.FileClient;
import edu.zut.hys.userapi.generator.mapper.UserMapper;
import edu.zut.hys.userapi.generator.mapper.UserRoleMapper;
import edu.zut.hys.userapi.generator.service.UserService;
import edu.zut.hys.userapi.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Author Hys
 * Date 2022/1/28 19:48
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    FileClient fileClient;

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Transactional
    @RequestMapping("/register")
    public ResponseData register(@RequestParam("username")String username,@RequestParam("password")String password,
                           @RequestParam("email")String email,@RequestParam("phonenumber")String phonenumber){
        ResponseData responseData = new ResponseData();
        responseData.setCode(Constant.SUCCESS);
        try{

            User user = new User(username,bCryptPasswordEncoder.encode(password),email,phonenumber);
            userMapper.insert(user);
            Long userid =user.getUserid();
            UserRole userRole = new UserRole();
            userRole.setUserid(userid);
            userRole.setRoleid(2L);
            userRoleMapper.insert(userRole);

            responseData.getData().put("msg", "success");
        }catch (Exception e){
            responseData.setCode(Constant.FAIL);
        }

        return responseData;
    }

    @Transactional
    @RequestMapping("/update")
    public ResponseData update(@RequestParam(value = "username", required = false)String username, @RequestParam(value = "password", required = false)String password,
                                 @RequestParam(value = "email", required = false)String email, @RequestParam(value = "phonenumber", required = false)String phonenumber,
                               @RequestParam(value = "userid")Long userid, @RequestParam(value = "description", required = false)String description){
        ResponseData responseData = new ResponseData();
        responseData.setCode(Constant.SUCCESS);
        try{

            User user = new User(username,bCryptPasswordEncoder.encode(password),email,phonenumber);
            user.setUserid(userid);
            user.setDescription(description);

            userMapper.updateById(user);

            userService.clearCache();

            responseData.getData().put("msg", "success");
        }catch (Exception e){
            responseData.setCode(Constant.FAIL);
        }

        return responseData;
    }



    @RequestMapping("/getusersbyids")
    public String getUserbyIds(@RequestParam("ids")List<Long> ids){
        ResponseData responseData = new ResponseData();
        responseData.setCode(Constant.SUCCESS);

        List<User> users = userMapper.selectBatchIds(ids);
        responseData.getData().put("users", users);

        return JSON.toJSONString(responseData, SerializerFeature.DisableCircularReferenceDetect);
    }

    @RequestMapping("/searchusersbyusername")
    public ResponseData searchUsersByUsername(@RequestParam("keyword")String keyword){
        ResponseData responseData = new ResponseData();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", keyword);
        List<User> users = userMapper.selectList(queryWrapper);
        responseData.getData().put("users", users);

        List<Long> fileids = new LinkedList<>();
        for (User user : users) {
            fileids.add(user.getHeadshotid());
        }
        HashMap<Long, Appfile> headshots =  fileClient.getFilesByIds(fileids);
        responseData.getData().put("headshots", headshots);

        responseData.setCode(20000);
        return responseData;
    }

}
