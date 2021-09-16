/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: imgService.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/9 下午9:17
 */

package com.hdu.honor.image;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class ImgService {
    private final Logger logger = LoggerFactory.getLogger(ImgService.class);
    @Autowired
    private ImgConfig imgConfig;
    public final Set<String> acceptContentTypes = new HashSet<>();

    /**
     * 添加支持的图片类型
     */
    public ImgService(){
        acceptContentTypes.add("image/jpeg");
        acceptContentTypes.add("image/png");
        acceptContentTypes.add("image/webp");
        acceptContentTypes.add("image/gif");
    }

    /**
     * 检查文件的大小是否超过限制
     * @author Eric
     * @since 1.0.0
     * @see ImgConfig
     */
    public boolean checkSize(MultipartFile file){
        return imgConfig.getMaxSize().toBytes()>=file.getSize();
    }

    /**
     * 对上传的文件进行预处理判断是否为支持的类型，大小有没有超过限制，文件名是否合法。
     * @author Eric
     * @since 1.0.0
     * @see ImgConfig
     */
    private String preprocess(MultipartFile file)throws ImageUploadException{
        String filename=file.getOriginalFilename();
        if (filename==null||filename.lastIndexOf(".")==-1||!acceptContentTypes.contains(file.getContentType())){
            throw new ImageUploadException("不支持的类型");
        }
        if (!checkSize(file)){
            throw new ImageUploadException("文件太大");
        }
        return filename;
    }

    public String save(MultipartFile file) throws ImageUploadException{
        String filename=preprocess(file);
        String suffixName=filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID().toString().replace("-","")+suffixName;
        try {
            file.transferTo(new File(imgConfig.getPath(),filename));
        }catch (Exception e){
            e.printStackTrace();
        }
        return filename;
    }


    public String getUrl(String filename){
        return "/upload/img/"+filename;
    }

    /**
     * 上传图片，并对图片进行压缩，压缩的设置参考{@link ImgConfig}设置是全局的<br>
     * 图片压缩的库为{@link Thumbnails}<br>
     * 支持上传的图片类型为jpg,png,webp,gif
     * @param file {@link MultipartFile}上传的文件流
     * @return 图片对应的在upload文件夹中的名称，名称是由{@link UUID}生成，保证唯一性
     * @author Eric
     * @see MultipartFile
     * @see Thumbnails
     * @see ImgConfig
     * @see UUID
     * @since 1.0.0
     */
    public String saveCover(MultipartFile file) throws ImageUploadException{
        String filename=preprocess(file);
        String suffixName=filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID().toString().replace("-","")+suffixName;
        try {
            Thumbnails.of(file.getInputStream())
                    .size(imgConfig.getMaxCoverWidth(),imgConfig.getMaxCoverHeight())
                    .outputQuality(imgConfig.getCoverQuality())
                    .toFile(new File(imgConfig.getPath(),filename));
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new ImageUploadException("转换失败");
        }
        return filename;
    }
}
