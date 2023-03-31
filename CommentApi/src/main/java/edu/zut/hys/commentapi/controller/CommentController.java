package edu.zut.hys.commentapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zut.hys.commentapi.generator.mapper.CommentMapper;
import edu.zut.hys.constant.Constant;
import edu.zut.hys.domain.Comment;
import edu.zut.hys.domain.ResponseData;
import edu.zut.hys.domain.User;
import edu.zut.hys.feignapi.clients.ArticleClient;
import edu.zut.hys.feignapi.clients.CommentClient;
import edu.zut.hys.feignapi.clients.NoticeClient;
import edu.zut.hys.feignapi.clients.UserClient;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.soap.Addressing;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Author Hys
 * Date 2022/2/4 20:45
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserClient userClient;
    
    @Autowired
    NoticeClient noticeClient;

    @Autowired
    CommentClient commentClient;

    @Autowired
    ArticleClient articleClient;

    @RequestMapping("/insertcomment")
    public ResponseData insertComment(@RequestParam("userid") Long userid, @RequestParam("type")String type,
                                      @RequestParam("articleid")Long articleid, @RequestParam(value = "commentid",required = false)Long commentid,
                                      @RequestParam("body")String body){
        ResponseData responseData = new ResponseData();

        List<Long> userids = new LinkedList<>();
        userids.add(userid);
        HashMap<Long, User> userHashMap = userClient.getUsersByIds(userids);

        User writer;
        //评论的文章
        if(commentid == null){
            writer = articleClient.getUserByArticleId(articleid);
        }
        //评论的评论
        else{
            writer = commentClient.getUserByCommentId(commentid);
        }

        Comment comment = new Comment(userid, type, articleid, commentid, body);
        commentMapper.insert(comment);
        noticeClient.SendNotice(userid, "user", writer.getUserid(), "comment", userHashMap.get(userid).getUsername()+"评论了你");
        responseData.getData().put("comment",comment);
        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }

    @RequestMapping("/selectcomments")
    public ResponseData selectComments(@RequestParam("articleid")String articleid){
        ResponseData responseData = new ResponseData();
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("articleid", articleid);
        List<Comment> comments = commentMapper.selectList(commentQueryWrapper);
        //补充用户信息
        HashMap<Long, User> users;
        if(comments.size() == 0){
            users = new HashMap<>();
        }else{
            HashSet<Long> useridset = new HashSet<>();
            comments.forEach(comment -> useridset.add(comment.getUserid()));
            List<Long> userid = new LinkedList<>();
            useridset.forEach(userid::add);
            users = userClient.getUsersByIds(userid);
        }

        responseData.getData().put("comments",comments);
        responseData.getData().put("users", users);
        responseData.setCode(Constant.SUCCESS);
        return responseData;
    }

}
