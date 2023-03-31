package edu.zut.hys.fileapi.controller;

import edu.zut.hys.constant.FileType;
import edu.zut.hys.domain.Appfile;
import edu.zut.hys.fileapi.generator.service.AppfileService;
import edu.zut.hys.fileapi.utils.NonStaticResourceHttpRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author Hys
 * Date 2022/2/2 19:20
 * Project AwakeningEra2
 */
@Controller
@RequestMapping("/file")
public class UpAndDown {
    Logger logger = Logger.getLogger(FileController.class.getName());
    @Autowired
    AppfileService appfileService;
    @Autowired
    NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    @ResponseBody
    @PostMapping("/upload")
    public String singleFileUpLoad(@RequestParam("file")MultipartFile file, @RequestParam("userid")Long userid,
                                   @RequestParam("relationid")Long relationtid, @RequestParam("relationtype")String relationtype) {
        logger.log(Level.INFO,file.getOriginalFilename());
        //win
        String response;
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
        try {
            System.out.println(targetPath+fileName);
            file.transferTo(new File(targetPath+fileName));
            Appfile appfile = new Appfile(userid, relationtid, fileName, FileType.HEAD_SHOT);
            appfileService.save(appfile);
            response = fileName;
        } catch (Exception e) {
            e.printStackTrace();
            response = "Exception";
        }
        return response;
    }

    @ResponseBody
    @RequestMapping("/download/{name}")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("name")String name) {
        // 文件名
        String fileName = name;
        if (fileName != null) {
            //win
//            String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//            String projectPath = sourcePath.replace("/target/classes/","/").substring(1);
            //linux
            String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            String projectPath = sourcePath.replace("/FileApi-1.jar!/BOOT-INF/classes!/","");
            projectPath = projectPath.replace("file:", "");
            String targetPath = projectPath +"/file/";

            //设置文件路径
//            File file = new File(projectPath + "file/" + fileName);
            File file = new File(targetPath + fileName);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }

    @RequestMapping("/preview/{name}")
    public void videoPreview(HttpServletRequest request, HttpServletResponse response,@PathVariable("name")String name) throws Exception {
        //win
//        String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        String projectPath = sourcePath.replace("/target/classes/","/").substring(1);
        //linux
        String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String projectPath = sourcePath.replace("/FileApi-1.jar!/BOOT-INF/classes!/","");
        projectPath = projectPath.replace("file:", "");

        String realPath = projectPath +"/file/"+name;
        logger.log(Level.INFO,"访问文件路径: "+realPath);
        Path filePath = Paths.get(realPath);
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }
}
