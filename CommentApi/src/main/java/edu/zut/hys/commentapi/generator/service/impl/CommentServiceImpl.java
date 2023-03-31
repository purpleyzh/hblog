package edu.zut.hys.commentapi.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.commentapi.generator.service.CommentService;
import edu.zut.hys.commentapi.generator.mapper.CommentMapper;
import edu.zut.hys.domain.Comment;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
implements CommentService{

}




