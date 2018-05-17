package com.turtlebot.operation.dataobject.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: davidddl
 * @Date: 2018/5/17 21:23
 * @Description:
 */
public class ImageFileModel {
    private String wantedFilename;
    private MultipartFile image;

    public ImageFileModel() {
    }

    public String getWantedFilename() {
        return wantedFilename;
    }

    public void setWantedFilename(String wantedFilename) {
        this.wantedFilename = wantedFilename;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
