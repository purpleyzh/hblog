package edu.zut.hys.fileapi.controller;

import edu.zut.hys.domain.Appfile;
import edu.zut.hys.fileapi.generator.mapper.AppfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Author Hys
 * Date 2022/2/4 19:21
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/file/feign")
public class FileFeignController {

    @Autowired
    AppfileMapper appfileMapper;

    @RequestMapping("/getfilesbyids")
    public HashMap<Long,Appfile> getFilesByIds(@RequestParam("ids")List<Long> ids){
        List<Appfile> appfiles = appfileMapper.selectBatchIds(ids);
        HashMap<Long,Appfile> rsp = new HashMap<>();
        appfiles.forEach(item->{
            rsp.put(item.getFileid(), item);
        });
        return rsp;
    }
}
