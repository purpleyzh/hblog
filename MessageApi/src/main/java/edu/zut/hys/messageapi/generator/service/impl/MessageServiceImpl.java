package edu.zut.hys.messageapi.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.domain.Message;
import edu.zut.hys.messageapi.generator.service.MessageService;
import edu.zut.hys.messageapi.generator.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
implements MessageService{

}




