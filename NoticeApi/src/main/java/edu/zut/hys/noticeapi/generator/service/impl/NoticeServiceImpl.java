package edu.zut.hys.noticeapi.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zut.hys.domain.Notice;
import edu.zut.hys.noticeapi.generator.service.NoticeService;
import edu.zut.hys.noticeapi.generator.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
implements NoticeService{

}




