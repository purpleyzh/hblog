package edu.zut.hys.heartenapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zut.hys.constant.Constant;
import edu.zut.hys.domain.Article;
import edu.zut.hys.domain.Hearten;
import edu.zut.hys.domain.ResponseData;
import edu.zut.hys.domain.User;
import edu.zut.hys.feignapi.clients.ArticleClient;
import edu.zut.hys.feignapi.clients.CommentClient;
import edu.zut.hys.feignapi.clients.NoticeClient;
import edu.zut.hys.feignapi.clients.UserClient;
import edu.zut.hys.heartenapi.generator.mapper.HeartenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Author Hys
 * Date 2022/2/4 23:23
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/hearten")
public class HeartenController {

    @Autowired
    HeartenMapper heartenMapper;

    @Autowired
    NoticeClient noticeClient;

    @Autowired
    UserClient userClient;

    @Autowired
    ArticleClient articleClient;

    @Autowired
    CommentClient commentClient;

    @RequestMapping("/inserthearten")
    public ResponseData insertHearten(@RequestParam("userid")Long userid, @RequestParam("relationtype")String relationtype,
                                      @RequestParam("relationid")Long relationid){
        ResponseData responseData = new ResponseData();

        List<Long> userids = new LinkedList<>();
        userids.add(userid);
        HashMap<Long, User> userHashMap = userClient.getUsersByIds(userids);

        //查找点赞对象的用户
        User writer;
        //可能是文章
        if(relationtype.equals("article")){
            writer = articleClient.getUserByArticleId(relationid);
        }
        //可能是评论"comment"
        else{
            writer = commentClient.getUserByCommentId(relationid);
        }
        String messageType = "article".equals(relationtype)?"文章":"评论";

        QueryWrapper<Hearten> heartenQueryWrapper = new QueryWrapper<>();
        heartenQueryWrapper.eq("userid", userid)
                .eq("relationtype", relationtype)
                .eq("relationid", relationid);
        Hearten hearten = heartenMapper.selectOne(heartenQueryWrapper);
        if(hearten == null){
            hearten = new Hearten(userid, relationtype, relationid);
            heartenMapper.insert(hearten);
            noticeClient.SendNotice(userid, "user", writer.getUserid(), "hearten", userHashMap.get(userid).getUsername()+"点赞了你的"+messageType);
        }else{
            if("1".equals(hearten.getStatus())){
                hearten.setStatus("2");
            }else{
                hearten.setStatus("1");
                noticeClient.SendNotice(userid, "user", writer.getUserid(), "hearten", userHashMap.get(userid).getUsername()+"点赞了你的"+messageType);
            }
            heartenMapper.updateById(hearten);
        }
        responseData.getData().put("hearten", hearten);
        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }

    @RequestMapping("/getheartensbyuserid")
    public ResponseData getHeartensByUserid(@RequestParam("userid")Long userid){
        ResponseData responseData = new ResponseData();

        QueryWrapper<Hearten> heartenQueryWrapper = new QueryWrapper<>();
        heartenQueryWrapper.eq("userid", userid);
        List<Hearten> heartens = heartenMapper.selectList(heartenQueryWrapper);
        responseData.getData().put("heartens", heartens);

        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }

    @RequestMapping("/getheartensbyarticleid")
    public ResponseData getHeartensByArticleId(@RequestParam("relationid")Long relationid,
                                               @RequestParam("relationtype")String relationtype,
                                               @RequestParam(value = "userid",required = false)Long userid){
        ResponseData responseData = new ResponseData();

        QueryWrapper<Hearten> heartenQueryWrapper = new QueryWrapper<>();
        heartenQueryWrapper.eq("relationid", relationid)
                .eq("relationtype",relationtype)
                .eq("status", "1");
        List<Hearten> heartens = heartenMapper.selectList(heartenQueryWrapper);
        responseData.getData().put("heartens", heartens);
        responseData.getData().put("heartenSum", heartens.size());

        Hearten ownHearten;
        if(userid != null){
            heartenQueryWrapper.clear();
            heartenQueryWrapper.eq("userid",userid)
                    .eq("relationtype",relationtype)
                    .eq("relationid", relationid);
            ownHearten = heartenMapper.selectOne(heartenQueryWrapper);
            if(ownHearten!=null){
                responseData.getData().put("heartenStatus", ownHearten.getStatus());
            }else{
                responseData.getData().put("heartenStatus", "0");
            }
        }else{
            responseData.getData().put("heartenStatus", "0");
        }

        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }
}
