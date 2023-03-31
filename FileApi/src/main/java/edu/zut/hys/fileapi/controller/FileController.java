package edu.zut.hys.fileapi.controller;

import edu.zut.hys.constant.Constant;
import edu.zut.hys.constant.FileType;
import edu.zut.hys.domain.Appfile;
import edu.zut.hys.feignapi.clients.UserClient;
import edu.zut.hys.fileapi.generator.mapper.AppfileMapper;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author Hys
 * Date 2022/1/28 22:51
 * Project AwakeningEra2
 */
@RestController
@RequestMapping("/file")
public class FileController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    AppfileMapper appfileMapper;

    @Autowired
    UserClient userClient;

    @RequestMapping("/getheadshot")
    public String getHeadshot(@RequestParam("fileid")Long fileid){
        Appfile appfile = appfileMapper.selectById(fileid);
        return appfile.getFilename();
    }

    @PostMapping("/updateheadshot")
    public String updateHeadShot(@RequestParam("file") MultipartFile file, @RequestParam("userid")Long userid) {
        logger.log(Level.INFO,file.getOriginalFilename());
        String response;
        //win
//        String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        String projectPath = sourcePath.replace("/target/classes/","").substring(1);
//        String targetPath = projectPath +"/file/";
        //linux
        String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        String projectPath = sourcePath.replace("/FileApi-1.jar!/BOOT-INF/classes!/","");
        projectPath = projectPath.replace("file:", "");
        String targetPath = projectPath +"/file/";


        logger.log(Level.INFO,"文件: "+targetPath+file.getOriginalFilename());
        File target = new File(targetPath);
        if(!target.exists()){
            logger.log(Level.INFO,"创建目标目录");
            target.mkdirs();
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf(".")+1);
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        //指定本地文件夹存储
        Appfile appfile = null;
        try {
           logger.log(Level.INFO,targetPath+fileName);
            file.transferTo(new File(targetPath+fileName));
            appfile = new Appfile(userid, userid, fileName, FileType.HEAD_SHOT);
            appfileMapper.insert(appfile);
            response = fileName;
        } catch (Exception e) {
            e.printStackTrace();
            response = "Exception";
        }

        if(appfile!=null && appfile.getFileid()!=null){
            userClient.updateHeadshot(userid, appfile.getFileid());
        }

        return response;
    }

    @RequestMapping("/getpath")
    public String getPath() {
        String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String projectPath = sourcePath.replace("/target/classes/","").substring(1);
        String targetPath = projectPath +"/file/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date(System.currentTimeMillis())));
        System.out.println(sourcePath);
        System.out.println(projectPath);
        System.out.println(targetPath);
        return sourcePath;
    }

    @RequestMapping("/getlinuxpath")
    public String getLinuxPath() {
        String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        String projectPath = sourcePath.replace("/FileApi-1.jar!/BOOT-INF/classes!/","");
        projectPath = projectPath.replace("file:", "");
        String targetPath = projectPath +"/file/";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date(System.currentTimeMillis())));
        System.out.println(sourcePath);
        System.out.println(projectPath);
        System.out.println(targetPath);
        return sourcePath;
    }

}
