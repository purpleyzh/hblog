package edu.zut.hys.commentapi.controller;

import edu.zut.hys.commentapi.generator.mapper.CommentMapper;
import edu.zut.hys.domain.Comment;
import edu.zut.hys.domain.User;
import edu.zut.hys.feignapi.clients.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Author Hys
 * Date 2022/2/4 20:45
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/comment/feign")
public class CommentFeignController {

    @Autowired
    UserClient userClient;

    @Autowired
    CommentMapper commentMapper;

    @RequestMapping("/getuserbycommentid")
    User getUserByCommentId(@RequestParam("commentid")Long commentid){

        Comment comment = commentMapper.selectById(commentid);
        List<Long> userids = new LinkedList<>();
        userids.add(comment.getUserid());
        HashMap<Long, User> userHashMap = userClient.getUsersByIds(userids);
        return userHashMap.get(comment.getUserid());
    }

}
