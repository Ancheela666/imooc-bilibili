package com.imooc.bilibili.service;

import com.imooc.bilibili.domain.File;
import com.imooc.bilibili.dao.FileDao;
import com.imooc.bilibili.service.util.FastDFSUtil;
import com.imooc.bilibili.service.util.MD5Util;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
public class FileService {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    public String uploadFileBySlices(MultipartFile slice, String fileMd5, Integer sliceNo, Integer totalNo) throws IOException {
        //查询该文件是否已经被上传过
        File dbFileMD5 = fileDao.getFileByMD5(fileMd5);
        if(dbFileMD5 != null){ //该文件已经被上传过
            return dbFileMD5.getUrl();
        }
        //上传文件
        String url = fastDFSUtil.uploadFileBySlices(slice, fileMd5, sliceNo, totalNo);
        //往数据库中添加文件上传记录
        if(!StringUtils.isNullOrEmpty(url)){ //上传成功
            dbFileMD5 = new File();
            dbFileMD5.setCreateTime(new Date());
            dbFileMD5.setUrl(url);
            dbFileMD5.setType(fastDFSUtil.getFileType(slice));
            dbFileMD5.setMd5(fileMd5);
            fileDao.addFile(dbFileMD5); //将文件信息存入数据库中
        }
        return url;
    }

    public String getFileMD5(MultipartFile file) throws Exception {
        return MD5Util.getFileMD5(file);
    }

    public File getFileByMd5(String fileMd5) {
        return fileDao.getFileByMD5(fileMd5);
    }
}
