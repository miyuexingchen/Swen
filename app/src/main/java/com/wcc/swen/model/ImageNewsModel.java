package com.wcc.swen.model;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public class ImageNewsModel {
    public String imgextra;

    public ImageNewsModel(String imgextra) {
        this.imgextra = imgextra;
    }

    @Override
    public String toString() {
        return "ImageNewsModel{" +
                "imgextra='" + imgextra + '\'' +
                '}';
    }
}
