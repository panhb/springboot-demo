package com.panhb.demo.controller;

import com.google.common.io.Files;
import com.panhb.demo.controller.base.BaseController;
import com.panhb.demo.model.result.Result;
import com.panhb.demo.utils.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * Created by admin on 2017/6/30.
 */

@RestController
@RequestMapping("/load")
public class DownAndUpLoadController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(DownAndUpLoadController.class);

    @RequestMapping("/down")
    public void down(){
        File file = new File("D://panhb.log");
        if(file.exists()){
            breakPointDownload(file);
        }else{
            log.error("文件不存在");
            printJson(Result.error(getMessage("result.opt.error")));
        }
    }

    @RequestMapping("/up")
    public void up(@RequestHeader(name="file_id",required=false) String file_id,
                   @RequestHeader String file_md5,@RequestHeader String file_name,@RequestHeader String chunk_md5,
                   @RequestHeader String chunk_index,@RequestHeader Integer chunk_num,MultipartFile apk_file) throws Exception{
        if(StringUtils.isEmpty(file_id))
            file_id = UUID.randomUUID().toString();
        response.setHeader("File-Id",file_id);
        String dirPath = "D://file_tmp//"+file_id;
        String destPath = dirPath + File.separator + chunk_index;

        File file = uploadFile(apk_file,destPath);
        if(!chunk_md5.equals(FileUtils.getFileMd5(file)))
            printJson(Result.error("分片md5不匹配",chunk_index));
        File dirFile = new File(dirPath);
        File[] files = dirFile.listFiles();
        String apkPath = dirPath+ File.separator + file_name;
        if(chunk_num > 1){
            if(files.length == chunk_num){//如果文件数等于分片数开始合并
                String[] fapths = new String[chunk_num];
                for(int i = 1 ; i <= chunk_num;i++)
                    fapths[i-1] = dirPath + File.separator + i;
                FileUtils.mergeFiles(fapths,apkPath);
            }else{
                printJson(Result.success("分片上传成功",chunk_index));
                return;
            }
        }else{
            File apkFile = new File(apkPath);
            if(!apkFile.exists()){
                apkFile.createNewFile();
            }
            Files.copy(file,apkFile);
            file.delete();
        }
        String apkMd5 = FileUtils.getFileMd5(new File(apkPath));
        if(file_md5.equals(apkMd5)){
            response.setHeader("File-Md5",apkMd5);
            printJson(Result.success("文件上传成功"));
        }else{
            printJson(Result.error("文件校验失败!"));
        }
    }




}
