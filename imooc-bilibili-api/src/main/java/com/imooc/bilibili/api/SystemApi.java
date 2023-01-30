package com.imooc.bilibili.api;

import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.domain.Video;
import com.imooc.bilibili.service.ElasticSearchService;

import com.imooc.bilibili.service.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class SystemApi {

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Autowired
    private ElasticSearchService elasticSearchService;

    //文件上传并分片
    @GetMapping("/slices")
    public void slices(MultipartFile multipartFile) throws Exception {
        fastDFSUtil.convertFileToSlices(multipartFile);
    }

    //针对视频数据的es搜索
    @GetMapping("/es-videos")
    public JsonResponse<Video> getEsVideos(@RequestParam String keyword) throws Exception {
        Video videos = elasticSearchService.getVideos(keyword);
        return new JsonResponse<>(videos);
    }

    //es全文搜索
    @GetMapping("/contents")
    public JsonResponse<List<Map<String, Object>>> getContents(@RequestParam String keyword,
                                                               @RequestParam Integer pageNo,
                                                               @RequestParam Integer pageSize) throws IOException {
        List<Map<String, Object>> list = elasticSearchService.getContents(keyword,pageNo,pageSize);
        return new JsonResponse<>(list);
    }
}
