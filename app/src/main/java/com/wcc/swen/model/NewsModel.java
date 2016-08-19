package com.wcc.swen.model;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public class NewsModel {

    // 共同
    public String title;
    public String source;
    public String imgsrc;
    public String ptime;
    public int replyCount;

    // 顶部
    public List<NewsModel> ads;
    public String photosetID;
    public String skipID;

    // 多图
    public List<ImageNewsModel> imgextra;

    // 普通
    public String digest;
    public String url;

    public NewsModel(String title, String source, String imgsrc, String ptime, int replyCount, List<NewsModel> ads, String photosetID, String skipID, List<ImageNewsModel> imgextra, String digest, String url) {
        this.title = title;
        this.source = source;
        this.imgsrc = imgsrc;
        this.ptime = ptime;
        this.replyCount = replyCount;
        this.ads = ads;
        this.photosetID = photosetID;
        this.skipID = skipID;
        this.imgextra = imgextra;
        this.digest = digest;
        this.url = url;
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", ptime='" + ptime + '\'' +
                ", replyCount=" + replyCount +
                ", ads=" + ads +
                ", photosetID='" + photosetID + '\'' +
                ", skipID='" + skipID + '\'' +
                ", imgextra=" + imgextra +
                ", digest='" + digest + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
