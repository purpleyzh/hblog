package edu.zut.hys.userapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import edu.zut.hys.constant.Constant;
import edu.zut.hys.domain.ResponseData;
import edu.zut.hys.domain.User;
import edu.zut.hys.feignapi.clients.FileClient;
import edu.zut.hys.userapi.generator.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ldap.HasControls;
import java.util.HashMap;
import java.util.List;

/**
 * Author Hys
 * Date 2022/2/4 19:16
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/user/feign")
public class UserFeignController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    FileClient fileClient;

    @RequestMapping("/getusersbyids")
    public HashMap<Long, User> getUserbyIds(@RequestParam("ids") List<Long> ids){
        List<User> users = userMapper.selectBatchIds(ids);
        HashMap<Long, User> rsp = new HashMap<>();
        users.forEach(item->{
            rsp.put(item.getUserid(),item);
        });
        return rsp;
    }

    @RequestMapping("/updateheadshot")
    public void updateHeadshot(@RequestParam("userid")Long userid,
                               @RequestParam("fileid")Long fileid){
        User user = new User();
        user.setUserid(userid);
        user.setHeadshotid(fileid);
        userMapper.updateById(user);
    }


}
