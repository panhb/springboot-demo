package com.panhb.demo.utils;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * Created by admin on 2017/6/30.
 */
public class FileUtils {

    public static String getFileMd5(File file){
        try {
            String md5 = DigestUtils.md5Hex(Files.toByteArray(file));
            return md5;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean mergeFiles(String[] fpaths, String resultPath) {
        if (fpaths == null || fpaths.length < 1 || Strings.isNullOrEmpty(resultPath)) {
            return false;
        }
        if (fpaths.length == 1) {
            return new File(fpaths[0]).renameTo(new File(resultPath));
        }
        File[] files = new File[fpaths.length];
        for (int i = 0; i < fpaths.length; i ++) {
            files[i] = new File(fpaths[i]);
            if (Strings.isNullOrEmpty(fpaths[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }
        File resultFile = new File(resultPath);
        try {
            int bufSize = 1024;
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(resultFile));
            byte[] buffer = new byte[bufSize];

            for (int i = 0; i < fpaths.length; i ++) {
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(files[i]));
                int readcount;
                while ((readcount = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readcount);
                }
                inputStream.close();
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        for (int i = 0; i < fpaths.length; i ++) {
            files[i].delete();
        }
        return true;
    }

    public static boolean mergeFilesByFileChannel(String[] fpaths, String resultPath) {
        if (fpaths == null || fpaths.length < 1 || Strings.isNullOrEmpty(resultPath)) {
            return false;
        }
        if (fpaths.length == 1) {
            return new File(fpaths[0]).renameTo(new File(resultPath));
        }

        File[] files = new File[fpaths.length];
        for (int i = 0; i < fpaths.length; i ++) {
            files[i] = new File(fpaths[i]);
            if (Strings.isNullOrEmpty(fpaths[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();
            for (int i = 0; i < fpaths.length; i ++) {
                FileChannel blk = new FileInputStream(files[i]).getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
            }
            resultFileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        for (int i = 0; i < fpaths.length; i ++) {
            files[i].delete();
        }
        return true;
    }


    /**
     * 文件分割
     * @param src 源文件路径
     * @param fileSize 分割后每个文件的大小，单位是KB
     * @param dest 目标文件路径
     * @return int 切割的文件数
     */
    public static int split(String src,int fileSize,String dest){
        if(Strings.isNullOrEmpty(src) || fileSize==0 || Strings.isNullOrEmpty(dest)){
            System.out.println("分割失败");
        }
        File destFile = new File(dest);
        if(!destFile.exists())
            destFile.mkdirs();
        File srcFile = new File(src);//源文件
        long srcSize = srcFile.length();//源文件的大小
//        long destSize = 1024*1024*fileSize;//目标文件的大小（分割后每个文件的大小）
        long destSize = 1024*fileSize;//目标文件的大小（分割后每个文件的大小）
        int number = (int)(srcSize/destSize);
        number = srcSize%destSize==0?number:number+1;//分割后文件的数目
//        String fileName = src.substring(src.lastIndexOf("\\"));//源文件名
        InputStream in = null;//输入字节流
        BufferedInputStream bis = null;//输入缓冲流
//        byte[] bytes = new byte[1024*1024];//每次读取文件的大小为1MB
        byte[] bytes = new byte[1024];//每次读取文件的大小为1KB
        int len = -1;//每次读取的长度值
        try {
            in = new FileInputStream(srcFile);
            bis = new BufferedInputStream(in);
            for(int i=0;i<number;i++){
//                String destName = dest+File.separator+fileName+"-"+i+".dat";
                String destName = dest+File.separator+(i+1);
                OutputStream out = new FileOutputStream(destName);
                BufferedOutputStream bos = new BufferedOutputStream(out);
                int count = 0;
                while((len = bis.read(bytes))!=-1){
                    bos.write(bytes, 0, len);//把字节数据写入目标文件中
                    count+=len;
                    if(count>=destSize){
                        break;
                    }
                }
                bos.flush();//刷新
                bos.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //关闭流
            try {
                if(bis!=null)bis.close();
                if(in!=null)in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return number;
    }

}
