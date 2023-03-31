package edu.zut.hys.followapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zut.hys.constant.Constant;
import edu.zut.hys.domain.Follow;
import edu.zut.hys.domain.ResponseData;
import edu.zut.hys.domain.User;
import edu.zut.hys.feignapi.clients.NoticeClient;
import edu.zut.hys.feignapi.clients.UserClient;
import edu.zut.hys.followapi.generator.mapper.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Author Hys
 * Date 2022/2/4 20:27
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    FollowMapper followMapper;

    @Autowired
    UserClient userClient;

    @Autowired
    NoticeClient noticeClient;

    @RequestMapping("/insertfollow")
    public ResponseData insertFollow(@RequestParam("userid")Long userid, @RequestParam("touserid")Long to){
        ResponseData responseData = new ResponseData();
        Follow follow;
        List<Long> userids = new LinkedList<>();
        userids.add(userid);
        HashMap<Long, User> userHashMap = userClient.getUsersByIds(userids);
        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();
        followQueryWrapper.eq("userid",userid)
                        .eq("touserid", to);

        follow = followMapper.selectOne(followQueryWrapper);
        //未关注
        if(follow == null){
            follow = new Follow(userid, to);
            followMapper.insert(follow);
            noticeClient.SendNotice(userid, "user", to, "follow", userHashMap.get(userid).getUsername()+"关注了你");
        }else{
            //取消关注
            if("1".equals(follow.getStatus())){
                follow.setStatus("2");
            }
            //恢复关注
            else{
                noticeClient.SendNotice(userid, "user", to, "follow", userHashMap.get(userid).getUsername()+"关注了你");
                follow.setStatus("1");
            }
            followMapper.updateById(follow);
        }
        responseData.getData().put("follow",follow);
        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }

    @RequestMapping("/getfollowbyuseridandtouserid")
    public ResponseData getFollowByUserIdAndToUserId(@RequestParam("userid")Long userid, @RequestParam("touserid")Long to){
        ResponseData responseData = new ResponseData();

        Follow follow;
        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();
        followQueryWrapper.eq("userid",userid)
                .eq("touserid", to);

        follow = followMapper.selectOne(followQueryWrapper);

        if(follow == null){
            responseData.getData().put("followStatus", "0");
        }else{
            responseData.getData().put("followStatus", follow.getStatus());
        }
        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }

//    @RequestMapping("/deletefollow")
//    public ResponseData deteleFollow(@RequestParam("followid")Long followid){
//        ResponseData responseData = new ResponseData();
//        followMapper.deleteById(followid);
//        responseData.setCode(Constant.SUCCESS);
//        return responseData;
//    }

    @RequestMapping("/getfollowsbyuserid")
    public ResponseData getFollowsByUserid(@RequestParam("userid")Long userid){
        ResponseData responseData = new ResponseData();
        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();
        followQueryWrapper.eq("userid", userid);
        List<Follow> follows = followMapper.selectList(followQueryWrapper);
        if(follows.size() == 0){
            List<User> tousers = new LinkedList<>();
            responseData.getData().put("follows", tousers);
            responseData.setCode(Constant.SUCCESS);
            return responseData;
        }
        List<Long> touserids = new LinkedList<>();
        follows.forEach(follow -> touserids.add(follow.getTouserid()));

        HashMap<Long, User> tousersmap = userClient.getUsersByIds(touserids);
        List<User> tousers = new LinkedList<>();
        tousersmap.forEach((id,user)->tousers.add(user));

        responseData.getData().put("follows", tousers);
        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }
}
