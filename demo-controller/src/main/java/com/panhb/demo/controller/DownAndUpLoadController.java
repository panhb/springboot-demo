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
                   @RequestHeader Integer chunk_index,@RequestHeader Integer chunk_num,MultipartFile apk_file) throws Exception{
        if(StringUtils.isEmpty(file_id))
            file_id = UUID.randomUUID().toString();
        response.setHeader("File-Id",file_id);
        String dirPath = "D://file_tmp//"+file_id;
        String destPath = dirPath + File.separator + chunk_index;

        File file = uploadFile(apk_file,destPath);
        if(!chunk_md5.equals(FileUtils.getFileMd5(file))){
            printJson(Result.error("分片md5不匹配",chunk_index));
            return;
        }
        upLoadOut(dirPath, destPath, file_name, chunk_num, chunk_index, file_md5);
    }

    @RequestMapping("/up2")
    public void up(@RequestHeader(name="file_id",required=false) String file_id,
                   @RequestHeader String file_md5,@RequestHeader String file_name,@RequestHeader(name="file_size") Long fileSize,@RequestHeader(name="file_step") Long fileStep,
                   @RequestHeader String chunk_md5,@RequestHeader(name="Range",required=false) String range) throws Exception{
        if(StringUtils.isEmpty(file_id))
            file_id = UUID.randomUUID().toString();
        response.setHeader("File-Id",file_id);
        int chunkNum = getNum(fileSize,fileStep);
        String rangeBytes = range.replaceAll("bytes=" , "" );
        rangeBytes = rangeBytes.split("-")[1].trim();
        int chunkIndex = getNum(Long.parseLong(rangeBytes),fileStep);
        String dirPath = "D://file_tmp//"+file_id;
        String destPath = dirPath + File.separator + chunkIndex;
        FileUtils.inputStream2File(request.getInputStream(),destPath);
        String md5 = FileUtils.getFileMd5(new File(destPath));
        if(!chunk_md5.equals(md5)){
            printJson(Result.error("分片md5不匹配",chunkIndex));
            return;
        }
        upLoadOut(dirPath, destPath, file_name, chunkNum, chunkIndex, file_md5);

    }

    private void upLoadOut(String dirPath,String destPath,String fileName,int chunkNum,int chunkIndex,String fileMd5) throws Exception{
        File dirFile = new File(dirPath);
        File file = new File(destPath);
        File[] files = dirFile.listFiles();
        String apkPath = dirPath+ File.separator + fileName;
        if(chunkNum > 1){
            if(files.length == chunkNum){//如果文件数等于分片数开始合并
                String[] fapths = new String[chunkNum];
                for(int i = 1 ; i <= chunkNum;i++)
                    fapths[i-1] = dirPath + File.separator + i;
                FileUtils.mergeFiles(fapths,apkPath);
            }else{
                printJson(Result.success("分片上传成功",chunkIndex));
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
        if(fileMd5.equals(apkMd5)){
            response.setHeader("File-Md5",apkMd5);
            printJson(Result.success("文件上传成功"));
        }else{
            printJson(Result.error("文件校验失败!"));
        }
    }

    private int getNum(long total,long step){
        int num = (int)(total/step);
        num = total%step==0?num:num+1;
        return num;
    }




}
